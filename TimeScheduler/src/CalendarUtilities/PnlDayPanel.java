/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalendarUtilities;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.event.AncestorEvent;

/**
 *
 * @author Benny
 */
public class PnlDayPanel extends javax.swing.JPanel {

    public enum TYPE {
        LAST_MONTH, CURRENT_MONTH, NEXT_MONTH
    }

    private enum Scroll {
        DOWN, UP;
    }

    private java.util.ArrayList<BtnAppointment> btnAppointmentList;
    private javax.swing.JLabel lblDayNumber;
    private java.time.LocalDate day;
    private java.awt.Color selectionColor;
    private TYPE type;
    private boolean isSelected;

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

    public JLabel getLblDayNumber() {
        return lblDayNumber;
    }

    public void setLblDayNumber(JLabel lblDayNumber) {
        this.lblDayNumber = lblDayNumber;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
        this.initializeLblDayNumber();
    }

    public void setBtnAppointmentList(ArrayList<BtnAppointment> btnAppointmentList) {
        if (btnAppointmentList == null) {
            return;
        }
        if (btnAppointmentList.size() > 0) {
            this.btnAppointmentList.addAll(btnAppointmentList);
        }
    }

    public ArrayList<BtnAppointment> getBtnAppointmentList() {
        return this.btnAppointmentList;
    }

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

        this.scaleLblDayNumber();
    }

    private void scaleLblDayNumber() {

        this.lblDayNumber.setVisible(true);

        //calculate position and size of label
        this.lblDayNumber.setBounds((int) Math.ceil((float) (this.getWidth() / 30)),
                (int) Math.ceil((float) (this.getHeight() / 30)), this.getWidth() / 5,
                this.getHeight() / 5);
        //scale the size of label
        this.lblDayNumber.setFont(new java.awt.Font(this.lblDayNumber.getFont().getName(), java.awt.Font.PLAIN, this.getWidth() / 10));

        int year = this.day.getYear();

//        System.out.println("Date:\t" + this.day.getDayOfMonth() + "/" + this.day.getMonthValue() + "/" + this.day.getYear() + "\tDay:\t" + this.day.getDayOfWeek().toString() + "   \tMonth:\t" + this.day.getMonthValue() + "\tYear:\t" + this.day.getYear());
        if (this.day.getDayOfWeek() == DayOfWeek.SUNDAY) {
            this.lblDayNumber.setForeground(Color.red);
        }
    }

    private void addButtonListenr(BtnAppointment btn) {
        btn.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() >= 2) {
                    forms.FrmMain.getInstance().editEvent(btn.getEvent().getID());
                }

                clearButtonSelection();
                btn.setBackground(java.awt.Color.DARK_GRAY);
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

        btn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                forms.FrmMain.getInstance().displayEventDetails(btn.getEvent().getID());
            }
        });
    }

    private void addPanelListener(){
        //add listener
        this.addComponentListener(new java.awt.event.ComponentAdapter() {

            public void componentResized(java.awt.event.ComponentEvent evt) {
                scaleButtons();
            }

        });

        this.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                
                if (!isSelected){
                    return;
                }
                
                if (e.getWheelRotation() < 0) {
                    scrollEventButtons(Scroll.UP);
                } else {
                    scrollEventButtons(Scroll.DOWN);
                }
            }
        });
    }
    
    private void clearButton() {
        for (BtnAppointment btn : this.btnAppointmentList) {
            btn.setPriority(btn.getEvent().getPriority());
        }
    }

    private void scrollEventButtons(Scroll type) {

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
        } catch(java.lang.IndexOutOfBoundsException iooe){
            return;
        }

        for (BtnAppointment btn : this.btnAppointmentList) {
            if (type == type.UP) {
                btn.setLocation(btn.getX(), btn.getY() + 10);
            } else {
                btn.setLocation(btn.getX(), btn.getY() - 10);
            }
        }
    }

    public void setType(TYPE type) {
        this.type = type;
        if (type == TYPE.CURRENT_MONTH) {
            this.setBackground(java.awt.Color.WHITE);
        } else {
            this.setBackground(java.awt.Color.LIGHT_GRAY);
        }
    }

    public TYPE getType() {
        return this.type;
    }

    public void setSelected() {
        this.setBackground(this.selectionColor);
        this.isSelected = true;
    }

    public void setUnselected() {
        this.setType(this.type);
        this.isSelected = false;
    }

    public void addAppointment(classes.Event event) {
        BtnAppointment btn = new BtnAppointment(event);

        this.addButtonListenr(btn);

        this.btnAppointmentList.add(btn);
        this.add(btn);

        java.util.Collections.sort(this.btnAppointmentList);

        this.scaleButtons();
    }

    public void scaleButtons() {
        //hide all appointments
        for (BtnAppointment btn : this.btnAppointmentList) {
            this.remove(btn);
        }

//        this.revalidate();
//        this.repaint();

        int count = 0;
        int buttonPadding = (int) Math.ceil((float) this.getHeight() / 40);
        int labelOffset = (int) (Math.ceil((float) this.getHeight() / 5));

        for (BtnAppointment bt : this.btnAppointmentList) {
            bt.setBounds(0, count * (int) Math.ceil((float) this.getHeight() / 4) + labelOffset, this.getWidth(), (int) Math.ceil((float) this.getHeight() / 5) - buttonPadding);
            this.add(bt);
            bt.setVisible(true);
            count++;
        }
    }

    public void clearButtonSelection() {

        if (this.getParent() instanceof javax.swing.JPanel) {
            for (java.awt.Component c : ((javax.swing.JPanel) this.getParent()).getComponents()) {
                if (c instanceof PnlDayPanel) {
                    ((PnlDayPanel) c).clearButton();
                }
            }
        }
    }

    public void removeAllAppointments() {
        this.btnAppointmentList.clear();
        for (java.awt.Component c : this.getComponents()){
            if (c instanceof BtnAppointment){
                this.remove(c);
            }
        }
    }
}
