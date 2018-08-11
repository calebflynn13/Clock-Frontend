import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockGui {

    static SerialPort chosenPort;
    static PaintClock clock;
    static JSlider redSlider;
    static JSlider greenSlider;
    static JSlider blueSlider;

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
        c.gridheight = 9;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        pane.add(clock, c);

        //create sliders
        JLabel redSliderLabel = new JLabel("Red", JLabel.CENTER);
        redSliderLabel.setVerticalAlignment(JLabel.BOTTOM);
        redSliderLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
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
        c.gridx = 3;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        c.ipady = 0;
        pane.add(redSlider, c);

        JLabel greenSliderLabel = new JLabel("Green", JLabel.CENTER);
        greenSliderLabel.setVerticalAlignment(JLabel.BOTTOM);
        greenSliderLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 3;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
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
        c.gridx = 3;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        pane.add(greenSlider, c);

        JLabel blueSliderLabel = new JLabel("Blue", JLabel.CENTER);
        blueSliderLabel.setVerticalAlignment(JLabel.BOTTOM);
        blueSliderLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
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
        c.gridx = 3;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        pane.add(blueSlider, c);

        JLabel portConnectionLabel = new JLabel("Select Arduino Port:", JLabel.CENTER);
        portConnectionLabel.setVerticalAlignment(JLabel.BOTTOM);
        portConnectionLabel.setVerticalTextPosition(JLabel.BOTTOM);
        c.gridx = 3;
        c.gridy = 6;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        pane.add(portConnectionLabel, c);

        //create portlist dropdown
        JComboBox<String> portList = new JComboBox<String>();
        c.gridx = 3;
        c.gridy = 7;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        pane.add(portList, c);

        // populate the drop-down box
        SerialPort[] portNames = SerialPort.getCommPorts();
        for(int i = 0; i < portNames.length; i++)
            portList.addItem(portNames[i].getSystemPortName());

        //create button
        JButton connectButton = new JButton("Connect");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 8;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.1;
        pane.add(connectButton, c);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(connectButton.getText().equals("Connect")) {
                    // attempt to connect to the serial port
                    chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                    chosenPort.setBaudRate(38400);
                    chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                    if(chosenPort.openPort()) {
                        connectButton.setText("Disconnect");
                        portList.setEnabled(false);

                        // create a new thread for sending data to the arduino
                        Thread thread = new Thread(){
                            @Override public void run() {
                                // wait after connecting, so the bootloader can finish
                                try {Thread.sleep(8000); } catch(Exception e) {}
                                // send text to the arduino
                                PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                                // get the current time + 1 minute to send
                                Date date = new Date();
                                date = new Date(date.getTime() + 60000); // add 1 minute to the time
                                String strDateFormat = "hh:mma";
                                DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
                                String currentTime= dateFormat.format(date);
                                System.out.println("time: " + currentTime);
                                // actually send the current time
                                output.print("1: " + currentTime);
                                output.flush();
                                try {Thread.sleep(2000); } catch(Exception e) {}

                                // send LED commands
                                String ledCommand = "2:";
                                // send first 2 numbers
                                for (int i = 0; i < 9; i++) {
                                    ledCommand += ((i * 3) + ",");
                                    ledCommand += (clock.segments.get(i).toString());

                                    ledCommand += ((i * 3 + 1) + ",");
                                    ledCommand += (clock.segments.get(i).toString());

                                    ledCommand += ((i * 3 + 2) + ",");
                                    ledCommand += (clock.segments.get(i).toString());
                                }

                                // send colon
                                ledCommand += ((27) + ","); // LED #30
                                ledCommand += (clock.segments.get(9).toString());

                                ledCommand += ((28) + ","); //LED #31
                                ledCommand += (clock.segments.get(10).toString());


                                //last 2 numbers
                                for (int i = 11; i < 25; i++) {
                                    ledCommand += ((i * 3 - 4) + ",");
                                    ledCommand += (clock.segments.get(i).toString());

                                    ledCommand += ((i * 3 - 3) + ",");
                                    ledCommand += (clock.segments.get(i).toString());

                                    ledCommand += ((i * 3 - 2) + ",");
                                    ledCommand += (clock.segments.get(i).toString());
                                }

                                // AM/PM and reverse order Sat - Sun
                                for (int i = 25; i < 34; i++) {
                                    ledCommand += ((46 + i) + ","); //LED #74 - 82
                                    ledCommand += (clock.segments.get(i).toString());
                                }
                                System.out.println(ledCommand);
                                output.print(ledCommand);
                                output.flush();
                                try {Thread.sleep(5000); } catch(Exception e) {}
                            }
                        };
                        thread.start();
                    }
                } else {
                    // disconnect from the serial port
                    chosenPort.closePort();
                    portList.setEnabled(true);
                    connectButton.setText("Connect");
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