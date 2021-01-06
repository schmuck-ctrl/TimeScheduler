/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import CalendarUtilities.PnlDayPanel;
import CalendarUtilities.PnlWeekdays;
import java.awt.event.MouseEvent;
import java.time.Month;

/**
 *
 * @author Benny
 */
public class FrmCalendar extends javax.swing.JPanel {

    private java.awt.Color gridColor;
    private CalendarUtilities.PnlDayPanel[][] panelArray;
    private java.time.LocalDate currentDate;
    private PnlWeekdays pnlWeekdays;
    private int pnlWeekdaysheight;
    private int padding;

    /**
     * Creates new form FrmCalendar
     */
    public FrmCalendar() {
        initComponents();

        this.setLayout(null);

        this.setBackground(java.awt.Color.DARK_GRAY);
        this.pnlWeekdaysheight = 30;
        this.padding = 1;
        this.currentDate = java.time.LocalDate.now();
        this.pnlWeekdays = new PnlWeekdays(pnlWeekdaysheight);

        this.createView();
    }

    private void createView() {

        this.removeAll();
        this.revalidate();
        this.repaint();
        
        this.panelArray = null;

        int numberOfColumns = 7;
        int numberOfRows = (this.calculateOverflowDaysOfLastMonth()
                + calculateDaysOfMonth(this.currentDate.getMonthValue(), this.currentDate.getYear())
                + this.calculateOverflowDaysOfNextMonth()) / 7;

        int lastMonthOverflow = this.calculateOverflowDaysOfLastMonth(), lastMonthStartDate = this.calculateDaysOfMonth(this.currentDate.getMonthValue() - 1, this.currentDate.getYear()) - this.calculateOverflowDaysOfLastMonth() + 1; // + 1 weil das datum sonst um 1 zu niedrig ist
        int nextMonthOverflow = this.calculateOverflowDaysOfNextMonth(), nextMonthStartDate = 1;
        //counter for currentMonth so we get 1. 2. 3. and so on
        int currentMonthCounter = 1;

        this.panelArray = new CalendarUtilities.PnlDayPanel[numberOfRows][numberOfColumns];

        //check if year is last or next year if month = 0 or month = 11
        int lastMonthYear = this.currentDate.getYear(), currentMonthYear = this.currentDate.getYear(), nextMonthYear = this.currentDate.getYear();
        if (this.currentDate.getMonthValue() >= 12) {
            nextMonthYear = nextMonthYear + 1;
        } else if (this.currentDate.getMonthValue() <= 1) {
            lastMonthYear = lastMonthYear - 1;
        }

        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfColumns; j++) {

                //go in this if, if we need to add days from the last month
                if (lastMonthOverflow > 0) {
                    this.panelArray[i][j] = new PnlDayPanel();
                    // + 1 because of day offset somehow
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(lastMonthYear, this.currentDate.getMonthValue(), lastMonthStartDate));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.LAST_MONTH);

                    lastMonthOverflow--;
                    lastMonthStartDate++;
                    //go in this if, if we have days from next month
                } else if (nextMonthOverflow > 0 && currentMonthCounter > this.calculateDaysOfMonth(this.currentDate.getMonthValue(), this.currentDate.getYear())) {
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(nextMonthYear, this.currentDate.getMonthValue(), nextMonthStartDate));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.NEXT_MONTH);

                    nextMonthOverflow--;
                    nextMonthStartDate++;
                    //go in if, if we are in current month
                } else {
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(currentMonthYear, this.currentDate.getMonthValue(), currentMonthCounter));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.CURRENT_MONTH);
                    currentMonthCounter++;
                }
                this.add(this.panelArray[i][j]);
            }
        }
        this.scaleView();
    }

    private void scaleView() {

        int numberOfRows = (this.calculateOverflowDaysOfLastMonth()
                + calculateDaysOfMonth(this.currentDate.getMonthValue(), this.currentDate.getYear())
                + this.calculateOverflowDaysOfNextMonth()) / 7;
        int numberOfColumns = 7;

        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfColumns; j++) {

                int xLocation = j * (int) (Math.ceil((float) this.getWidth() / numberOfColumns));
                int yLocation = ((int) (i * Math.ceil((float) (this.getHeight() - this.pnlWeekdaysheight) / numberOfRows))) + this.pnlWeekdaysheight;
                //yLocation = ((int) (rowCount * Math.ceil((float) (this.getHeight() - this.yOffsetDaysPanel) / rowCountMax))) + this.yOffsetDaysPanel;
                int width = (int) Math.ceil((float) this.getWidth() / 7) - this.padding;
                int height = (int) Math.ceil((float) (this.getHeight() - this.pnlWeekdaysheight) / numberOfRows) - this.padding;

                //System.out.println("xLocation:\t" + xLocation + "\tyLocation:\t" + yLocation + "\tRow:\t" + i + "\tColumn:\t" + j + "\tWidth:\t" + width + "\tHeight\t" + height + "\tParentWidth:\t" + this.getWidth() + "\tParentHeight:\t" + this.getHeight());
                this.panelArray[i][j].setBounds(xLocation, yLocation, width, height);
                this.panelArray[i][j].revalidate();
                this.panelArray[i][j].repaint();
            }
        }
    }

    private int calculateDaysOfMonth(int month, int year) {

        if (month < 1) {
            month = 12;
            year = year - 1;
        } else if (month > 12) {
            month = 1;
            year = year + 1;
        }

        java.time.YearMonth ym = java.time.YearMonth.of(year, month);

        return ym.lengthOfMonth();
    }

    private int calculateOverflowDaysOfLastMonth() {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        // this.currentDate.getMonthValue() - 1 because Calendar starts with January = 0 and LocalDateTime starts with January = 1
        int x = this.currentDate.getYear(), y = this.currentDate.getMonthValue() - 1;
        cal.set(this.currentDate.getYear(), this.currentDate.getMonthValue() - 1, 1);

        int daysInLastMonth = 0;
        int firstDayInMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);

        if (firstDayInMonth != 2) {
            daysInLastMonth = firstDayInMonth - 2;
            if (daysInLastMonth < 0) {
                daysInLastMonth = 6;
            }
        }
        return daysInLastMonth;
    }

    private int calculateOverflowDaysOfNextMonth() {

        int _month = this.currentDate.getMonthValue();
        int _year = this.currentDate.getYear();

        if (_month >= 12) {
            _month = 0;
            _year = _year + 1;
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(_year, _month, 1);

        int firstDayOfNextMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);

        if (firstDayOfNextMonth != 2) {
            firstDayOfNextMonth = firstDayOfNextMonth - 2;
        } else {
            return 0;
        }
        return 7 - firstDayOfNextMonth;
    }

    public void setLocalDate(java.time.LocalDate dt) {
        this.currentDate = dt;

        this.createView();
        this.scaleView();
        
        this.revalidate();
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        scaleView();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
