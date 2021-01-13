/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventUtilities;

import classes.Event;
import java.awt.Component;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author nilss
 */
public class EventsOfDayListCellRenderer extends DefaultListCellRenderer {

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
