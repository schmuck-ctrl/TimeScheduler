/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Event;
import java.awt.Component;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Default ListCellRenderer for the JList {@link forms.FrmEventsOfDay#liEventsOfDay} in {@link forms.FrmEventsOfDay}.
 * to display the name of the Event and EventTime in the specified JList.
 * 
 * @author Nils Schmuck
 */
public class EventsOfDayListCellRenderer extends DefaultListCellRenderer {

    /**
     * Return a JList that has been configured to display the event name and event time value.
     * 
     * @param list The JList we're painting.
     * @param value The value returned by list.getModel().getElementAt(index).
     * @param index The cells index.
     * @param isSelected True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * 
     * @return A component whose paint() method will render the specified value.
     */
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Event event = (Event) value;

        //Create formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

        //Local time instance
        LocalTime localTime = LocalTime.of(event.getDate().getHour(), event.getDate().getMinute());

        //Get formatted String
        String timeString = formatter.format(localTime);

        setText(timeString + " - " + event.getName());

        return this;
    }
}
