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
        
        this.pnlWeekdaysheight = 20;
        this.padding = 1;
        this.currentDate = java.time.LocalDate.now();
        this.pnlWeekdays = new PnlWeekdays(pnlWeekdaysheight);

        this.createView();
    }

    private void createView() {

        int numberOfColumns = 7;
        int numberOfRows = this.calculateOverflowDaysOfLastMonth() +
                calculateDaysOfMonth(this.currentDate.getMonthValue(), this.currentDate.getYear()) + 
                this.calculateOverflowDaysOfNextMonth() / 7;
        
        int lastMonthOverflow = this.calculateOverflowDaysOfLastMonth(), lastMonthStartDate = this.calculateDaysOfMonth(this.currentDate.getMonthValue() - 1, this.currentDate.getYear()) - this.calculateOverflowDaysOfLastMonth();
        int nextMonthOverflow = this.calculateOverflowDaysOfNextMonth(), nextMonthStartDate = 1;
        
        this.panelArray = new CalendarUtilities.PnlDayPanel [numberOfRows][numberOfColumns];
        
        //check if year is last or next year if month = 0 or month = 11
        int lastMonthYear = this.currentDate.getYear(), currentMonthYear = this.currentDate.getYear(), nextMonthYear = this.currentDate.getYear();
        if (this.currentDate.getMonthValue() >= 11){
            nextMonthYear = nextMonthYear + 1;
        } else if (this.currentDate.getMonthValue() <= 0){
            lastMonthYear = lastMonthYear - 1;
        }
        
        for (int i = 0; i < numberOfRows; i++){
            
            for (int j = 0; j < numberOfColumns; j++){
                
                //go in this if, if we need to add days from the last month
                if (lastMonthOverflow > 0){
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDateTime.of(lastMonthYear, this.currentDate.getMonthValue(), lastMonthStartDate, 0, 0));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.LAST_MONTH);
                    
                    lastMonthOverflow--;
                    lastMonthStartDate++;
                //go in this if, if we have days from next month
                } else if (nextMonthOverflow > 0){
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDateTime.of(nextMonthYear, this.currentDate.getMonthValue(), nextMonthStartDate, 0, 0));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.NEXT_MONTH);
                    
                    nextMonthOverflow--;
                    nextMonthStartDate++;
                //go in if, if we are in current month
                } else{
                    this.panelArray[i][j] = new PnlDayPanel();
                    this.panelArray[i][j].setDay(java.time.LocalDateTime.of(currentMonthYear, this.currentDate.getMonthValue(), nextMonthStartDate, 0, 0));
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.CURRENT_MONTH);
                }
                this.panelArray[i][j].setVisible(true);
            }   
        }
        this.scaleView();
    }

    private void scaleView() {
        
        int numberOfRows = this.calculateOverflowDaysOfLastMonth() +
                calculateDaysOfMonth(this.currentDate.getMonthValue(), this.currentDate.getYear()) + 
                this.calculateOverflowDaysOfNextMonth() / 7;
        int numberOfColumns = 7;
        
        for (int i = 0; i < numberOfRows; i++){
            
            for (int j = 0; j < numberOfColumns; j++){
                
                int xLocation = j * (int)(Math.ceil(this.getWidth() / numberOfColumns));
                int yLocation = i * (int)(Math.ceil(this.getHeight() / numberOfRows)) + this.pnlWeekdaysheight;
                int width = (int)Math.ceil(this.getWidth() / 7) - this.padding;
                int height = (int)Math.ceil((this.getHeight() - this.pnlWeekdaysheight) / 7) - this.padding;
                
                this.panelArray[i][j].setBounds(xLocation, yLocation, width, height);                
            } 
        }
    }

    private int calculateDaysOfMonth(int month, int year) {

        if (month < 0) {
            month = 11;
            year = year - 1;
        } else if (month > 11) {
            month = 0;
            year = year + 1;
        }

        java.time.YearMonth ym = java.time.YearMonth.of(year, month);

        return ym.lengthOfMonth();
    }

    private int calculateOverflowDaysOfLastMonth() {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(this.currentDate.getYear(), this.currentDate.getMonthValue(), 1);

        int daysInLastMonth = 0;
        int firstDayInMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);

        if (firstDayInMonth != 2) {
            daysInLastMonth = firstDayInMonth - 2;
            if (daysInLastMonth < 0) {
                daysInLastMonth = 6;
            }
        }
        return daysInLastMonth;
    }

    private int calculateOverflowDaysOfNextMonth() {
        int _month, _year;

		// check if next month is also in next year
		if ((this.currentDate.getMonthValue() + 1) > 11) {
			_month = 0;
			_year = this.currentDate.getYear() + 1;
		} else {
			_month = this.currentDate.getMonthValue() + 1;
			_year = this.currentDate.getYear();
		}

		// get first day in next month
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.set(_year, _month, 1);
		int day = cal.get(java.util.Calendar.DAY_OF_MONTH);

		// if day is 2 its a monday, so there is no overflow. Else calculate the offset
		if (day != 2) {
			day = day - 2;
			if (day < 0) {
				return 1;
			}
		} else {
			return 0;
		}
		// return days miinus offset as amount of days
		return 7 - day;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
