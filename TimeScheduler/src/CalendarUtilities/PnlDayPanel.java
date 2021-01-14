/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Benny
 */
public class PnlDayPanel extends javax.swing.JPanel {

    /**
     * Type for the Panel. There are 3 Types: LAST_MONTH, CURRENT_MONTH,
     * NEXT_MONTH
     */
    public enum TYPE {
        LAST_MONTH, CURRENT_MONTH, NEXT_MONTH
    }

    /**
     * Scroll Enum for Scroll up or down
     */
    private enum Scroll {
        DOWN, UP;
    }

    private java.util.ArrayList<BtnAppointment> btnAppointmentList;
    private javax.swing.JLabel lblDayNumber;
    private java.time.LocalDate day;
    private java.awt.Color selectionColor;
    private TYPE type;
    private boolean isSelected;

    /**
     * Constructor create a PnlDayPanel() and sets some properties.
     */
    public PnlDayPanel() {
        this.selectionColor = Color.decode("0x0088FF");
        this.setLayout(null);
        this.lblDayNumber = new javax.swing.JLabel();
        this.lblDayNumber.setVisible(true);
        this.lblDayNumber.setLayout(null);
        this.add(this.lblDayNumber);
        this.btnAppointmentList = new ArrayList<>();
        this.setVisible(true);
        this.isSelected = false;
        this.addPanelListener();
    }

    /**
     * Gets the LblDayNumber
     *
     * @return returns javax.swing.JLabel
     */
    public JLabel getLblDayNumber() {
        return lblDayNumber;
    }

    /**
     * Sets a lblDayNumber
     *
     * @param lblDayNumber javax.swing.JLabel
     */
    public void setLblDayNumber(JLabel lblDayNumber) {
        this.lblDayNumber = lblDayNumber;
    }

    /**
     * Returns the LocalDate of the current Panel.
     *
     * @return
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * Sets the Day of the Panel
     *
     * @param day Receives a java.time.LocalDate
     */
    public void setDay(LocalDate day) {
        this.day = day;
        this.initializeLblDayNumber();
    }

    /**
     * Returns the AppointmentList of Buttons
     *
     * @return returns a java.util.ArrayList(BtnAppointment)
     */
    public ArrayList<BtnAppointment> getBtnAppointmentList() {
        return this.btnAppointmentList;
    }

    /**
     * Initializes the Label, which contains the number of the date.
     */
    private void initializeLblDayNumber() {

        this.remove(this.lblDayNumber);

        this.lblDayNumber = new javax.swing.JLabel();
        this.lblDayNumber.setText(day.getDayOfMonth() + ".");

        //add resize listener
        this.lblDayNumber.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }

            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                scaleLblDayNumber();
            }
        });

        this.add(this.lblDayNumber);

        //scale the label to current size
        this.scaleLblDayNumber();
    }

    /**
     * Scales the Label, which contains the day number to fit in the size of the
     * view.
     */
    private void scaleLblDayNumber() {

        this.lblDayNumber.setVisible(true);

        //calculate position and size of label
        this.lblDayNumber.setBounds((int) Math.ceil((float) (this.getWidth() / 30)),
                (int) Math.ceil((float) (this.getHeight() / 30)), this.getWidth() / 5,
                this.getHeight() / 5);
        //scale the size of label
        this.lblDayNumber.setFont(new java.awt.Font(this.lblDayNumber.getFont().getName(), java.awt.Font.PLAIN, this.getWidth() / 10));

        //If day is sunday, then label is going to be red.
        if (this.day.getDayOfWeek() == DayOfWeek.SUNDAY) {
            this.lblDayNumber.setForeground(Color.red);
        }
    }

    /**
     * Adds MouseListener for the Appointment Buttons
     *
     * @param btn CalendarUtilities.BtnAppointment
     */
    private void addButtonListener(BtnAppointment btn) {
        
        
        btn.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    forms.FrmMain.getInstance().editEvent(btn.getEvent().getID());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btnMousePressed(e, btn);
                btnMousePressed(e, btn);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Function adds ComponentListener and MouseWheelListener
     */
    private void addPanelListener() {
        //add listener
        this.addComponentListener(new java.awt.event.ComponentAdapter() {

            public void componentResized(java.awt.event.ComponentEvent evt) {
                //scales the buttons if panel is resized
                scaleButtons();
            }

        });

        //enables scrolling in day
        this.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                //returns if user is trying to scroll on a non selected day
                if (!isSelected) {
                    return;
                }

                //checks if user is scrolling up or down
                if (e.getWheelRotation() < 0) {
                    scrollEventButtons(Scroll.UP);
                } else {
                    scrollEventButtons(Scroll.DOWN);
                }
            }
        });
    }

    /**
     * Clears the Button Selection and set its Color to Green Yellow Red based
     * on priority
     */
    private void clearButton() {
        for (BtnAppointment btn : this.btnAppointmentList) {
            btn.setPriority(btn.getEvent().getPriority());
        }
    }

    /**
     * Scrolls based on Argument Scroll type
     *
     * @param type PnlDayPanel.Scroll up or down
     */
    private void scrollEventButtons(Scroll type) {

        //Prüfung ob der User versucht den Button über bzw. unter das panel zu scrollen
        try {
            if (type == type.DOWN) {
                if (this.btnAppointmentList.get(this.btnAppointmentList.size() - 1).getY() <= 0) {
                    return;
                }
            } else if (type == type.UP) {
                if (this.btnAppointmentList.get(0).getY() >= this.getHeight() - this.btnAppointmentList.get(0).getHeight()) {
                    return;
                }
            }
        } catch (java.lang.IndexOutOfBoundsException iooe) {
            return;
        }

        //Set every location, of a button based on Scroll type, new.
        for (BtnAppointment btn : this.btnAppointmentList) {
            if (type == type.UP) {
                btn.setLocation(btn.getX(), btn.getY() + 10);
            } else {
                btn.setLocation(btn.getX(), btn.getY() - 10);
            }
        }
    }

    /**
     * Sets the Color of the Panel based on the Type, white on CURRENT_MONTH
     * else light gray
     *
     * @param type TYPE
     */
    public void setType(TYPE type) {
        this.type = type;
        if (type == TYPE.CURRENT_MONTH) {
            this.setBackground(java.awt.Color.WHITE);
        } else {
            this.setBackground(java.awt.Color.LIGHT_GRAY);
        }
    }

    /**
     * Returns the TYPE of the panel for easier maintenance
     *
     * @return Returns the Type of the panel
     */
    public TYPE getType() {
        return this.type;
    }

    /**
     * Sets this panel to selected
     */
    public void setSelected() {
        this.setBackground(this.selectionColor);
        this.isSelected = true;
    }

    /**
     * Unselect the day and sets it Color back to White or Gray based on the
     * TYPE
     */
    public void setUnselected() {
        this.setType(this.type);
        this.isSelected = false;
    }

    /**
     * Adds an appointment to the current day
     *
     * @param event
     */
    public void addAppointment(classes.Event event) {
        BtnAppointment btn = new BtnAppointment(event);

        this.addButtonListener(btn);

        this.btnAppointmentList.add(btn);
        this.add(btn);

        //sorts the button
        java.util.Collections.sort(this.btnAppointmentList);

        //scales the button
        this.scaleButtons();
    }

    /**
     * Removes all Buttons from panel first and add them fresh from
     * btnAppointmentList and sets the bounds.
     */
    public void scaleButtons() {
        //remove all appointments
        for (BtnAppointment btn : this.btnAppointmentList) {
            this.remove(btn);
        }

        this.revalidate();
        this.repaint();

        int count = 0;
        //buttonPadding is used to have a distance between 2 buttons
        int buttonPadding = (int) Math.ceil((float) this.getHeight() / 40);
        //Labeloffset so button is not created on day label
        int labelOffset = (int) (Math.ceil((float) this.getHeight() / 5));

        for (BtnAppointment bt : this.btnAppointmentList) {
            //set the bounds for 
            bt.setBounds(0, count * (int) Math.ceil((float) this.getHeight() / 4) + labelOffset, this.getWidth(), (int) Math.ceil((float) this.getHeight() / 5) - buttonPadding);
            this.add(bt);
            bt.setVisible(true);
            count++;
        }
    }

    /**
     * Clears the Button Selection of every button on the FrmCalendar
     */
    public void clearButtonSelection() {
        //Gets the FrmCalendar and gets every single PnlDayPanel and executes clearButton
        if (this.getParent() instanceof javax.swing.JPanel) {
            for (java.awt.Component c : ((javax.swing.JPanel) this.getParent()).getComponents()) {
                if (c instanceof PnlDayPanel) {
                    ((PnlDayPanel) c).clearButton();
                }
            }
        }
    }

    /**
     * Remove all Appointment Buttons
     */
    public void removeAllAppointments() {
        this.btnAppointmentList.clear();
        for (java.awt.Component c : this.getComponents()) {
            if (c instanceof BtnAppointment) {
                this.remove(c);
            }
        }
    }

    /**
     * Returns if Panel is selected
     *
     * @return Returns a boolean.
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    private void btnMousePressed(MouseEvent e, BtnAppointment btn) {
        
        clearButtonSelection();
            
        btn.setBackground(java.awt.Color.DARK_GRAY);

        if (getParent() instanceof forms.FrmCalendar) {
            ((forms.FrmCalendar) getParent()).clearDaySelection();
        }
        setSelected();
        forms.FrmMain.getInstance().displayEventDetails(btn.getEvent().getID());
    }
}
