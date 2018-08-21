import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class ClockGui {

    static SerialPort chosenPort;
    static PaintClock clock;
    static JSlider redSlider;
    static JSlider greenSlider;
    static JSlider blueSlider;
    static UpdateColors thread;
    static String animation;
    static String timerText;
    static boolean timerEnabled = false;

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Set Clock Colors");
        window.setSize(1300, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = window.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = c.BOTH;

        //create the numbers on the left
        clock = new PaintClock();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 13;
        c.gridwidth = 4;
        c.weightx = 1;
        c.weighty = 1;
        pane.add(clock, c);

        JLabel animationLabel = new JLabel("Animation:", JLabel.CENTER);
        animationLabel.setVerticalAlignment(JLabel.BOTTOM);
        animationLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 4;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(animationLabel, c);

        //create portlist dropdown
        JComboBox<String> animationList = new JComboBox<String>();
        c.gridx = 4;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(animationList, c);

        // populate the drop-down box
        animationList.addItem("None");
        animationList.addItem("Solid Rainbow");

        //create sliders
        JLabel redSliderLabel = new JLabel("Red", JLabel.CENTER);
        redSliderLabel.setVerticalAlignment(JLabel.BOTTOM);
        redSliderLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 4;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        c.ipady = 20;
        pane.add(redSliderLabel, c);
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                int redValue = (int)source.getValue();
                if (clock.getSelected() != null) {
                    for (LineSegment segment : clock.getSelected()) {
                        int greenComponent = segment.getColor().getGreen();
                        int blueComponent = segment.getColor().getBlue();
                        segment.setColor(new Color(redValue, greenComponent, blueComponent));
                    }
                    clock.repaint();
                }
            }
        });
        c.gridx = 4;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        c.ipady = 0;
        pane.add(redSlider, c);

        JLabel greenSliderLabel = new JLabel("Green", JLabel.CENTER);
        greenSliderLabel.setVerticalAlignment(JLabel.BOTTOM);
        greenSliderLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 4;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(greenSliderLabel, c);
        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
        greenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                int greenValue = (int)source.getValue();
                if (clock.getSelected() != null) {
                    for (LineSegment segment : clock.getSelected()) {
                        int redComponent = segment.getColor().getRed();
                        int blueComponent = segment.getColor().getBlue();
                        segment.setColor(new Color(redComponent, greenValue, blueComponent));
                    }
                    clock.repaint();
                }
            }
        });
        c.gridx = 4;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(greenSlider, c);

        JLabel blueSliderLabel = new JLabel("Blue", JLabel.CENTER);
        blueSliderLabel.setVerticalAlignment(JLabel.BOTTOM);
        blueSliderLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 4;
        c.gridy = 6;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(blueSliderLabel, c);
        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 1);
        blueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                int blueValue = (int)source.getValue();
                if (clock.getSelected() != null) {
                    for (LineSegment segment : clock.getSelected()) {
                        int redComponent = segment.getColor().getRed();
                        int greenComponent = segment.getColor().getGreen();
                        segment.setColor(new Color(redComponent, greenComponent, blueValue));
                    }
                    clock.repaint();
                }
            }
        });
        c.gridx = 4;
        c.gridy = 7;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(blueSlider, c);

        JLabel timerLabel = new JLabel("Set Timer (hh:mm:ss):", JLabel.CENTER);
        timerLabel.setVerticalAlignment(JLabel.BOTTOM);
        timerLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 4;
        c.gridy = 8;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(timerLabel, c);

        Checkbox checkbox = new Checkbox("Enable Timer", false);
        c.gridx = 4;
        c.gridy = 9;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.05;
        pane.add(checkbox, c);

        JComboBox<String> hourList = new JComboBox<String>();
        hourList.setEnabled(false);
        for (int i = 0; i <= 19; i++) {
            hourList.addItem("" + i);
        }
        c.gridx = 5;
        c.gridy = 9;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.05;
        pane.add(hourList, c);

        JComboBox<String> minuteList = new JComboBox<String>();
        minuteList.setEnabled(false);
        for (int i = 0; i <= 59; i++) {
            minuteList.addItem("" + i);
        }
        c.gridx = 6;
        c.gridy = 9;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.05;
        pane.add(minuteList, c);

        JComboBox<String> secondList = new JComboBox<String>();
        secondList.setEnabled(false);
        for (int i = 0; i <= 59; i++) {
            secondList.addItem("" + i);
        }
        c.gridx = 7;
        c.gridy = 9;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.05;
        pane.add(secondList, c);

        JTextField timer = new JTextField("hh:mm:ss", 8);
        timer.setEnabled(false);
        checkbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hourList.setEnabled(true);
                    minuteList.setEnabled(true);
                    secondList.setEnabled(true);
                    timerEnabled = true;
                    // TODO:
                }
                else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hourList.setEnabled(false);
                    minuteList.setEnabled(false);
                    secondList.setEnabled(false);
                    timerEnabled = false;
                }
            }
        });
        c.gridx = 4;
        c.gridy = 9;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.05;
        pane.add(timer, c);

        JLabel portConnectionLabel = new JLabel("Select Arduino Port:", JLabel.CENTER);
        portConnectionLabel.setVerticalAlignment(JLabel.BOTTOM);
        portConnectionLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 4;
        c.gridy = 10;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(portConnectionLabel, c);

        //create portlist dropdown
        JComboBox<String> portList = new JComboBox<String>();
        c.gridx = 4;
        c.gridy = 11;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(portList, c);

        // populate the drop-down box
        SerialPort[] portNames = SerialPort.getCommPorts();
        for(int i = 0; i < portNames.length; i++) {
            portList.addItem(portNames[i].getSystemPortName());
            if (portNames[i].getSystemPortName().equals("cu.wchusbserial1410")) {
                portList.setSelectedItem(portNames[i].getSystemPortName());
            }
        }

        //create button
        JButton connectButton = new JButton("Connect");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 12;
        c.gridheight = 1;
        c.gridwidth = 4;
        c.weightx = 0.05;
        pane.add(connectButton, c);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(connectButton.getText().equals("Connect")) {
                    // attempt to connect to the serial port
                    chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                    animation = animationList.getSelectedItem().toString();
                    chosenPort.setBaudRate(9600);
                    chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                    if(chosenPort.openPort()) {
                        connectButton.setText("Update");
                        portList.setEnabled(false);
                        if (timerEnabled) {
                            timerText = hourList.getSelectedItem().toString() + ":" +
                                    minuteList.getSelectedItem().toString() + ":" +
                                    secondList.getSelectedItem().toString();
                        }
                        else {
                            timerText = "";
                        }
                        // wait for arduino to reboot cause of new port connection
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        // create a new thread for sending data to the arduino
                        thread = new UpdateColors(clock, chosenPort, animation, timerText);
                        thread.start();
                    }
                } else {
                    // old disconnect from the serial port code
                    // chosenPort.closePort();
                    // portList.setEnabled(true);
                    // connectButton.setText("Connect");

                    // -------------------------------------
                    // stop old run and run with new clock code
                    thread.kill();
                    try {
                        thread.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    animation = animationList.getSelectedItem().toString();
                    if (timerEnabled) {
                        timerText = hourList.getSelectedItem().toString() + ":" +
                                minuteList.getSelectedItem().toString() + ":" +
                                secondList.getSelectedItem().toString();
                    }
                    else {
                        timerText = "";
                    }
                    thread = new UpdateColors(clock, chosenPort, animation, timerText);
                    thread.start();

                }
            }
        });


        // all done with stuff
        window.setVisible(true);
        clock.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!clock.getSelected().isEmpty()) {
                    // update the sliders to the selected values
                    redSlider.setValue(clock.getSelected().get(0).getColor().getRed());
                    greenSlider.setValue(clock.getSelected().get(0).getColor().getGreen());
                    blueSlider.setValue(clock.getSelected().get(0).getColor().getBlue());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
}