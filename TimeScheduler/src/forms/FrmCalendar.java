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
     * Creates new form FrmCalendar, sets standard properties and draw the
     * calendar panels.
     */
    public FrmCalendar() {
        initComponents();

        //Layout is null, so we have full control
        this.setLayout(null);
        //set grid color to Dark grey
        this.gridColor = java.awt.Color.DARK_GRAY;

        //set gridcolor
        this.setBackground(this.gridColor);
        //set the height of the blue weekdays panel
        this.pnlWeekdaysheight = 30;
        //padding is used to reduce height and width of panels, so we can see 1 pixel of this calendar grid
        this.padding = 1;
        //initialize calendar with 
        this.localDate = java.time.LocalDate.now();

        this.createView();
        this.setWeekdaysPanel();
        this.getPnlDayPanelByDay(this.localDate).setSelected();
    }

    /**
     * CreateView() creates panels, to represent the days. These panels are from
     * Class CalendarUtilities.PnlDayPanel. Therefore, the size of month have to
     * be calculated and the check for January or December has to be done
     *
     */
    private void createView() {

        //remove everything except PnlWeekdays
        for (java.awt.Component c : this.getComponents()) {
            //remove every CalendarUtilities.PnlDayPanel but not the CalendarUtilites.PnlWeekdays
            if (c instanceof PnlWeekdays) {
                continue;
            }
            this.remove(c);
        }

        //use revalidate and repaint to redraw the view if something is deleted. Otherwise there will be glitches
        this.revalidate();
        this.repaint();

        //clear the panelArray
        this.panelArray = null;

        //number of columns always 7, 1 week = 7 days
        int numberOfColumns = 7;
        //calculate the number of rows based on which days from last month, current month and next month have to be in view and divide it by 7.
        int numberOfRows = (this.calculateOverflowDaysOfLastMonth()
                + calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear())
                + this.calculateOverflowDaysOfNextMonth()) / 7;

        //sets number of days from last month 
        int lastMonthOverflow = this.calculateOverflowDaysOfLastMonth();
        //gets the start date of last month, so we can count from lastMonthStartDate to the last day of the last month
        int lastMonthStartDate = this.calculateDaysOfMonth(this.localDate.getMonthValue() - 1, this.localDate.getYear()) - this.calculateOverflowDaysOfLastMonth() + 1; // + 1 weil das datum sonst um 1 zu niedrig ist
        //gets the days from next month which have to be in this month
        int nextMonthOverflow = this.calculateOverflowDaysOfNextMonth(), nextMonthStartDate = 1;
        //counter for currentMonth so we get 1. 2. 3. and so on
        int currentMonthCounter = 1;

        //initialize new Array with new rows and columns
        this.panelArray = new CalendarUtilities.PnlDayPanel[numberOfRows][numberOfColumns];

        //check if year is last or next year if month = 0 or month = 11, so we set variables with current year
        int lastMonthYear = this.localDate.getYear(), currentMonthYear = this.localDate.getYear(), nextMonthYear = this.localDate.getYear();
        //check if last month is in last year, so current month is january
        int lastMonth = this.localDate.getMonthValue() - 1 < 1 ? 12 : this.localDate.getMonthValue() - 1;
        //check if next month is in this year, else current month is december 
        int nextMonth = this.localDate.getMonthValue() + 1 > 12 ? 1 : this.localDate.getMonthValue() + 1;
        //set lastyear and nextyear if month is at january or december
        if (this.localDate.getMonthValue() >= 12) {
            nextMonthYear = nextMonthYear + 1;
        } else if (this.localDate.getMonthValue() <= 1) {
            lastMonthYear = lastMonthYear - 1;
        }

        for (int i = 0; i < numberOfRows; i++) {

            for (int j = 0; j < numberOfColumns; j++) {

                //go in this if, if we need to add days from the last month
                if (lastMonthOverflow > 0) {
                    //initialize new panel
                    this.panelArray[i][j] = new PnlDayPanel();
                    //call the setDay() function from PnlDayPanel and set properties to a day from last month or year
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(lastMonthYear, lastMonth, lastMonthStartDate));
                    //set the panel type to last_month for easier maintenance
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.LAST_MONTH);

                    //reduce lastmonthoverflow by 1 so we won't enter this if clause when the last month days are all already in the calendar
                    lastMonthOverflow--;
                    //add startDate + 1 so we count 1 up in days, otherwise there will be always the same datum
                    lastMonthStartDate++;
                    //go in this if, if we have days from next month
                } else if (nextMonthOverflow > 0 && currentMonthCounter > this.calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear())) {
                    //initialize new panel
                    this.panelArray[i][j] = new PnlDayPanel();
                    //call the setDay() function from PnlDayPanel and set properties to a day from next month or year
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(nextMonthYear, nextMonth, nextMonthStartDate));
                    //set the panel type to next_month for easier maintenance
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.NEXT_MONTH);

                    //reduce nextmonthoverflow by 1 so we won't enter this if clause when the next month days are all already in the calendar
                    nextMonthOverflow--;
                    //add nextmonthstartDate + 1 so we count 1 up in days, otherwise there will be always the same datum
                    nextMonthStartDate++;
                    //go in if, if we are in current month
                } else {
                    //new panel
                    this.panelArray[i][j] = new PnlDayPanel();
                    //set day to currentMonthCounter and current month and year
                    this.panelArray[i][j].setDay(java.time.LocalDate.of(currentMonthYear, this.localDate.getMonthValue(), currentMonthCounter));
                    //setType for easier Maintenance to current_month
                    this.panelArray[i][j].setType(PnlDayPanel.TYPE.CURRENT_MONTH);
                    currentMonthCounter++;
                }
                //call createListener for functionalities
                this.createDayPanelListener(this.panelArray[i][j]);
                //add the panel to the panel
                this.add(this.panelArray[i][j]);
            }
        }
        //at the end call scaleview
        this.scaleView();
    }

    /**
     * Scales the view to given dimensions of this class. So if the window is
     * resized, all panels are resized too.
     */
    private void scaleView() {

        //get number of rows
        int numberOfRows = (this.calculateOverflowDaysOfLastMonth()
                + calculateDaysOfMonth(this.localDate.getMonthValue(), this.localDate.getYear())
                + this.calculateOverflowDaysOfNextMonth()) / 7;
        //set numberOfColumn to 7
        int numberOfColumns = 7;

        //i for rows
        for (int i = 0; i < numberOfRows; i++) {

            //in this loop, we are going to calculate all the locations for each panel
            //j for columns
            for (int j = 0; j < numberOfColumns; j++) {

                //Calculate xLocation based on the this.width() divided by 7 (the days of week) * j, the counter. Math.ceil is necessary, otherwise we get inconsistency
                int xLocation = j * (int) (Math.ceil((float) this.getWidth() / numberOfColumns));
                //calculate yLocation based on this.getHeight() with the consideration of the WeekDaysPanelHeight. so i * (this.height - weekDaysHeight) / rows + height
                int yLocation = ((int) (i * Math.ceil((float) (this.getHeight() - this.pnlWeekdaysheight) / numberOfRows))) + this.pnlWeekdaysheight;
                //Calculate width with width / 7 - padding
                int width = (int) Math.ceil((float) this.getWidth() / 7) - this.padding;
                //calculate height with height - weekDaysHeight / numberOfRows - padding
                int height = (int) Math.ceil((float) (this.getHeight() - this.pnlWeekdaysheight) / numberOfRows) - this.padding;

                //set the location and dimensions of eacht panel new and redraw it.
                this.panelArray[i][j].setBounds(xLocation, yLocation, width, height);
                this.panelArray[i][j].revalidate();
                this.panelArray[i][j].repaint();
            }
        }
    }

    /**
     * Calculates the days in a specific month using java.time.YearMonth.
     *
     * @param month Integer value of month, possible values are 1 - 12.
     * @param year Integer value of year.
     * @return Returns the lenth of a month as integer.
     */
    private int calculateDaysOfMonth(int month, int year) {

        //when month < 1, month will be set to december and the year will be reduced
        if (month < 1) {
            month = 12;
            year = year - 1;
            //when month > 12, month will be set to January and the year will be raised
        } else if (month > 12) {
            month = 1;
            year = year + 1;
        }

        //gets the year month 
        java.time.YearMonth ym = java.time.YearMonth.of(year, month);

        //returns the length of the month
        return ym.lengthOfMonth();
    }

    /**
     * Calculates the overflow Days which have to be also in the current month
     * view from the last Month based on this.localdate
     *
     * @return
     */
    private int calculateOverflowDaysOfLastMonth() {

        //initialize a java.time.Calendar
        java.util.Calendar cal = java.util.Calendar.getInstance();
        // this.currentDate.getMonthValue() - 1 because Calendar starts with January = 0 and LocalDateTime starts with January = 1
        cal.set(this.localDate.getYear(), this.localDate.getMonthValue() - 1, 1);

        int daysInLastMonth = 0;
        //gets first day
        int firstDayInMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);

        //if firstDayInMonth = 2 we return 0, because Sunday = 1, Monday = 2, ..... , Saturday = 7
        if (firstDayInMonth != 2) {
            //calculate the overflow days
            daysInLastMonth = firstDayInMonth - 2;
            if (daysInLastMonth < 0) {
                daysInLastMonth = 6;
            }
        }
        return daysInLastMonth;
    }

    /**
     * Calculates the overflow Days which have to be also in the current month
     * view from the next Month based on this.localdate
     *
     * @return
     */
    private int calculateOverflowDaysOfNextMonth() {

        int _month = this.localDate.getMonthValue();
        int _year = this.localDate.getYear();

        //if month == 12 we know, next month is January and also in next year
        if (_month >= 12) {
            _month = 0;
            _year = _year + 1;
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(_year, _month, 1);

        //get first day of next month
        int firstDayOfNextMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);

        //using switch case to determine how many days based on the days
        return switch (firstDayOfNextMonth) {
            case 1 ->
                1;
            case 2 ->
                0;
            case 3 ->
                6;
            case 4 ->
                5;
            case 5 ->
                4;
            case 6 ->
                3;
            case 7 ->
                2;
            default ->
                - 1;
        };
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

    /**
     * Clears the Selection of every day in the view.
     */
    public void clearDaySelection() {

        //get rowCount of view
        int rowCount = this.panelArray == null ? -1 : this.panelArray.length;
        //set weekdays to 7
        int columnCount = 7;

        //for each panel, we unselect every panel
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                this.panelArray[i][j].setUnselected();
            }
        }
    }

    /**
     * Is called, if the Component is resized.
     *
     * @param evt Evt has more informations about the event.
     */
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        //scale view, because the size has changed.
        scaleView();
        //revalidate and repaint the view 
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_formComponentResized

    /**
     * Creates MouseListeners for each PnlDayPanel
     *
     * @param dayPanel Is from CalendarUtilities.PnlDayPanel
     */
    private void createDayPanelListener(PnlDayPanel dayPanel) {
        dayPanel.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                pnlDayPanelMousePressed(e, dayPanel);
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

    /**
     * Returns the Panel based on the day.
     *
     * @param ld Receives a java.time.LocalDate.
     * @return Returns null if there is no such panel, or the panel.
     */
    private PnlDayPanel getPnlDayPanelByDay(java.time.LocalDate ld) {

        int rowCount = this.panelArray == null ? -1 : this.panelArray.length;
        int columnCount = 7;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                //check if localDates are equal
                if (this.panelArray[i][j].getDay().isEqual(ld)) {
                    return panelArray[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Creates a new PnlWeekDays and add it to the Calendar.
     */
    public void setWeekdaysPanel() {
        //Creates a pnlWeekdays with height 
        this.pnlWeekdays = new PnlWeekdays(this.weekdaysheight);
        //Add pnlWeekdays to this Calendar
        this.add(this.pnlWeekdays);
        //resize the Panel
        this.pnlWeekdays.resize();
    }

    /**
     * Add classes.Event List to the Calendar. If this function is called, the
     * Calender Removes all Appointment Buttons to add them new
     *
     * @param eventList Receives a classes.Event List with events.
     */
    public void addEvents(java.util.ArrayList<classes.Event> eventList) {

        //clear all appointments from current Calendar
        for (java.awt.Component c : this.getComponents()) {
            if (c instanceof PnlDayPanel) {
                ((PnlDayPanel) c).removeAllAppointments();
                ((PnlDayPanel) c).revalidate();
                ((PnlDayPanel) c).repaint();
            }
        }

        //Add appointments to panel
        for (classes.Event event : eventList) {
            //get panel based on event's localdatetime
            PnlDayPanel dayPanel = this.getPnlDayPanelByDay(java.time.LocalDate.of(event.getDate().getYear(), event.getDate().getMonthValue(), event.getDate().getDayOfMonth()));

            if (dayPanel != null) {
                dayPanel.addAppointment(event);
            }
        }
    }

    /**
     * Sets the LocalDate of the Calendar, which results in redrawing the
     * Calendar based on the date.
     *
     * @param dt java.time.LocalDate.
     */
    public void setLocalDate(java.time.LocalDate dt) {
        this.localDate = dt;

        //create new View
        this.createView();
        this.scaleView();
        this.setWeekdaysPanel();

        //sets the dayPanel to selected
        PnlDayPanel pnl = this.getPnlDayPanelByDay(dt);
        if (pnl != null) {
            pnl.setSelected();
        }

        //repaint again
        this.revalidate();
        this.repaint();
        
        this.focusDayPanel(this.getPnlDayPanelByDay(dt));
    }

    /**
     * Returns the Date of the Calendar.
     *
     * @return Returns a java.time.LocalDate
     */
    public java.time.LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Returns the first java.time.LocalDate of the View so this.panelArray
     * [0][0]
     *
     * @return if succesfull returns java.time.LocalDate, if not, null.
     */
    public java.time.LocalDate getFirstDayOfView() {
        if (this.panelArray != null) {
            if (this.panelArray[0][0] != null) {
                return this.panelArray[0][0].getDay();
            }
        }
        return null;
    }

    /**
     * Returns the last java.time.LocalDate of the View so this.panelArray [0]
     * [0]
     *
     * @return if succesfull returns java.time.LocalDate, if not, null.
     */
    public java.time.LocalDate getLastDayOfView() {
        if (this.panelArray != null) {
            if (this.panelArray[this.panelArray.length - 1][6] != null) {
                return this.panelArray[this.panelArray.length - 1][6].getDay();
            }
        }
        return null;
    }

    /**
     * Returns the selected java.time.LocalDate based on the selected day.
     *
     * @return Returns a java.time.LocalDate from selectedDay, otherwise null
     */
    public java.time.LocalDate getSelectedLocalDate() {
        int columnLength = 7;
        for (int i = 0; i < this.panelArray.length; i++) {
            for (int j = 0; j < columnLength; j++) {
                if (panelArray[i][j].isSelected()) {
                    return panelArray[i][j].getDay();
                }
            }
        }
        return null;
    }

    /**
     * Clears every Button Selection of Appointments
     */
    private void clearButtonSelection() {
        int columnCount = 7;
        for (int i = 0; i < this.panelArray.length; i++) {
            for (int j = 0; j < columnCount; j++) {
                panelArray[i][j].clearButtonSelection();
            }
        }
    }

    /**
     * This function takes a LocalDate as Argument and sets the Day as focused.
     * The Calender is not going to be reloaded.
     *
     * @param ld java.time.LocalDate
     */
    public void focusDayByLocalDate(java.time.LocalDate ld) {

        if (this.getPnlDayPanelByDay(ld) != null) {
            this.clearDaySelection();
            //clearButtonSelection();
            this.getPnlDayPanelByDay(ld).setSelected();
            this.focusDayPanel(this.getPnlDayPanelByDay(ld));
            this.localDate = ld;
        }
    }
    
    /**
     * Executes the procedures which are meant to be executed in the MouseClickListener
     * @param e MouseEvent e
     * @param dayPanel CalendarUtilities.PnlDayPanel
     */
    private void pnlDayPanelMousePressed(java.awt.event.MouseEvent e, PnlDayPanel dayPanel) {
        //if clicked once and button is left click on mouse
        if (e.getClickCount() == 1 && e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            //clear every day selection
            clearDaySelection();
            //clear also every button
            clearButtonSelection();
            //set this dayPanel to selected
            dayPanel.setSelected();
            //Call FrmMain to do some important stuff
            FrmMain.getInstance().displayAllEventsOfDay(dayPanel.getDay());
        } //if clickCount == 2 and the button was the left button
        else if (e.getClickCount() == 2 && e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            //create new Appointment
            FrmMain.getInstance().createNewEvent(dayPanel.getDay());
        } //if rightclick
        else if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //clear focus
            clearDaySelection();
            clearButtonSelection();
        }
    }

    /**
     * This function is called by focusDayByLocalDate and setLocalDate so the Appointments of day are also shown.
     * @param dayPanel CalendarUtilities.PnlDayPanel
     */
    private void focusDayPanel(PnlDayPanel dayPanel) {
        if (dayPanel == null)
            return;
        //clear every day selection
        clearDaySelection();
        //clear also every button
        //clearButtonSelection();
        //set this dayPanel to selected
        dayPanel.setSelected();
        //Call FrmMain to do some important stuff
        FrmMain.getInstance().displayAllEventsOfDay(dayPanel.getDay());
    }
    
    /**
     * Returns the Month value from current month
     * @return Returns the Month value of current month (1 - 12). PanelArray is null, -1 is returned.
     */
    public int getCurrentMonthValue(){
        
        int columnCount = 7;
        
        for (int i = 0; i < this.panelArray.length; i++){
            
            for (int j = 0; j < columnCount; j++){
                
                if (this.panelArray[i][j] != null && this.panelArray[i][j].getType() == CalendarUtilities.PnlDayPanel.TYPE.CURRENT_MONTH)
                    return this.panelArray[i][j].getDay().getMonthValue();
                
            }
            
        }
        return -1;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
