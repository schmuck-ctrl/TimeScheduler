/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import CalendarUtilities.PnlDayPanel;
import CalendarUtilities.PnlWeekdays;

/**
 *
 * @author Benny
 */
public class FrmCalendar extends javax.swing.JPanel {

    private java.awt.Color gridColor;
    private CalendarUtilities.PnlDayPanel[][] panelArray;
    private java.time.LocalDate localDate;
    private PnlWeekdays pnlWeekdays;
    private int pnlWeekdaysheight;
    private int padding;
    private final int weekdaysheight = 30;

    /**
     * Creates new form FrmCalendar
     */
    public FrmCalendar() {
        initComponents();

        this.setLayout(null);

        this.setBackground(java.awt.Color.DARK_GRAY);
        this.pnlWeekdaysheight = 30;
        this.padding = 1;
        this.localDate = java.time.LocalDate.now();

        this.createView();
        this.setWeekdaysPanel();
        this.getPnlDayPanelByDay(this.localDate).setSelected();
    }

    private void createView() {

        this.removeAll();
        this.revalidate();
        this.repaint();

        this.panelArray = null;

        int numberOfColumns = 7;
        int numberOfRows = (this.calculateOverflowDaysOfLastMonth()
                + calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear())
                + this.calculateOverflowDaysOfNextMonth()) / 7;

        int lastMonthOverflow = this.calculateOverflowDaysOfLastMonth(), lastMonthStartDate = this.calculateDaysOfMonth(this.localDate.getMonthValue() - 1, this.localDate.getYear()) - this.calculateOverflowDaysOfLastMonth() + 1; // + 1 weil das datum sonst um 1 zu niedrig ist
        int nextMonthOverflow = this.calculateOverflowDaysOfNextMonth(), nextMonthStartDate = 1;
        //counter for currentMonth so we get 1. 2. 3. and so on
        int currentMonthCounter = 1;

        this.panelArray = new CalendarUtilities.PnlDayPanel[numberOfRows][numberOfColumns];

        //check if year is last or next year if month = 0 or month = 11
        int lastMonthYear = this.localDate.getYear(), currentMonthYear = this.localDate.getYear(), nextMonthYear = this.localDate.getYear();
        int lastMonth = this.localDate.getMonthValue() - 1 < 1 ? 12 : this.localDate.getMonthValue() - 1;
        int nextMonth = this.localDate.getMonthValue() + 1 > 12 ? 1 : this.localDate.getMonthValue() + 1;
        if (this.localDate.getMonthValue() >= 12) {
            nextMonthYear = nextMonthYear + 1;
        } else if (this.localDate.getMonthValue() <= 1) {
            lastMonthYear = lastMonthYear - 1;
        }

        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfColumns; j++) {

                //go in this if, if we need to add days from the last month
                if (lastMonthOverflow > 0) {
                    this.panelArray[i][j] = new PnlDayPanel();
                    // -1 because we want the last month not current
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(lastMonthYear, lastMonth, lastMonthStartDate));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.LAST_MONTH);

                    lastMonthOverflow--;
                    lastMonthStartDate++;
                    //go in this if, if we have days from next month
                } else if (nextMonthOverflow > 0 && currentMonthCounter > this.calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear())) {
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(nextMonthYear, nextMonth, nextMonthStartDate));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.NEXT_MONTH);

                    nextMonthOverflow--;
                    nextMonthStartDate++;
                    //go in if, if we are in current month
                } else {
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(currentMonthYear, this.localDate.getMonthValue(), currentMonthCounter));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.CURRENT_MONTH);
                    currentMonthCounter++;
                }
                this.createDayPanelListener(this.panelArray[i][j]);
                this.add(this.panelArray[i][j]);
            }
        }
        this.scaleView();
    }

    private void scaleView() {

        int numberOfRows = (this.calculateOverflowDaysOfLastMonth()
                + calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear())
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
        int x = this.localDate.getYear(), y = this.localDate.getMonthValue() - 1;
        cal.set(this.localDate.getYear(), this.localDate.getMonthValue() - 1, 1);

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

        int _month = this.localDate.getMonthValue();
        int _year = this.localDate.getYear();

        if (_month >= 12) {
            _month = 0;
            _year = _year + 1;
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(_year, _month, 1);

        int firstDayOfNextMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);

        switch (firstDayOfNextMonth) {
            case 1:
                return 1;
            case 2:
                return 0;
            case 3:
                return 6;
            case 4:
                return 5;
            case 5:
                return 4;
            case 6:
                return 3;
            case 7:
                return 2;
            default:
                return - 1;
        }
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

    private void clearDaySelection() {

        int rowCount = (this.calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear()) + this.calculateOverflowDaysOfLastMonth() + this.calculateOverflowDaysOfNextMonth()) / 7;
        int columnCount = 7;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                this.panelArray[i][j].setUnselected();
            }
        }
    }

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        scaleView();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_formComponentResized

    private void createDayPanelListener(PnlDayPanel dayPanel) {
        dayPanel.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                    clearDaySelection();
                    dayPanel.setSelected();
                    FrmMain.getInstance().displayAllEventsOfDay(dayPanel.getDay());
                }
                
                if (e.getClickCount() == 2 && e.getButton() == java.awt.event.MouseEvent.BUTTON1){
                    FrmMain.getInstance().createNewEvent(dayPanel.getDay());
                }
                
                if (e.getButton() == java.awt.event.MouseEvent.BUTTON3){
                    clearDaySelection();
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
            }
        });
    }

    private PnlDayPanel getPnlDayPanelByDay(java.time.LocalDate ld){
        
        int rowCount = (this.calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getDayOfMonth()) + this.calculateOverflowDaysOfLastMonth() + this.calculateOverflowDaysOfNextMonth()) / 7;
        int columnCount = 7;
        
        for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < columnCount; j++){
                if (this.panelArray[i][j].getDay().isEqual(ld))
                    return panelArray[i][j];
            }
        }
        return null;
    }
    
    public void setWeekdaysPanel(){
        this.pnlWeekdays = new PnlWeekdays(this.weekdaysheight);
        this.add(this.pnlWeekdays);
        this.pnlWeekdays.resize();
    }
    
    public void addEvents(java.util.ArrayList<classes.Event> eventList) {
        
        for (java.awt.Component c : this.getComponents()){
            if (c instanceof PnlDayPanel){
                ((PnlDayPanel) c).removeAllAppointments();
            }
        }
        
        for (classes.Event event : eventList){
            PnlDayPanel dayPanel = this.getPnlDayPanelByDay(java.time.LocalDate.of(event.getDate().getYear(), event.getDate().getMonthValue(), event.getDate().getDayOfMonth()));
            
            if (dayPanel != null){
                dayPanel.addAppointment(event);
            }
        }  
    }

    public void setLocalDate(java.time.LocalDate dt) {
        this.localDate = dt;

        this.createView();
        this.scaleView();
        this.setWeekdaysPanel();
        
        PnlDayPanel pnl = this.getPnlDayPanelByDay(dt);
        pnl.setSelected();
        

        this.revalidate();
        this.repaint();
    }
    
    public java.time.LocalDate getLocalDate() {
        return localDate;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
