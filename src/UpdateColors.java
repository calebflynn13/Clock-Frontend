import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by calebflynn on 8/12/18.
 */
public class UpdateColors extends Thread{
    PaintClock clock;
    SerialPort chosenPort;
    boolean isAlive;
    PrintWriter output;
    String animation;
    String timer;
    int brightness;

    public UpdateColors(PaintClock clock, SerialPort chosenPort, String animation, String timer, int brightness) {
        this.clock = clock;
        this.chosenPort = chosenPort;
        isAlive = true;
        this.timer = timer;
        this.brightness = brightness;
        if (animation.equals("Solid Rainbow")) {
            this.animation = "3:1";
        }
        else {
            this.animation = animation;
        }
    }

    @Override
    public void run() {
        if (isAlive) {
            output = new PrintWriter(chosenPort.getOutputStream());

            // put the arduino in startUpMode
            System.out.println("Putting into setUpMode");
            System.out.println("4:");
            output.print("4:");
            output.flush();
            // TODO: SEND UNTIL WE GET POSITIVE RESPONSE BACK FROM ARDUINO
        }

        if (isAlive) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            System.out.println("setting brightness to " + brightness);
            System.out.println("8:" + brightness);
            output.print("8:" + brightness);
            output.flush();
        }

        if (isAlive) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }

            // get the current time + 1 minute to send
            Date date = new Date();
            date = new Date(date.getTime() + 5000);
            String strDateFormat = "hh:mm:ss:a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String currentTime = dateFormat.format(date);
            System.out.println("1: " + currentTime);
            // actually send the current time
            output.print("1: " + currentTime);
            output.flush();
        }

        if (isAlive) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            if (!animation.toLowerCase().equals("none")) {
                System.out.println(animation);
                output.print(animation);
                output.flush();
            }
            else {

                // send LED commands
                String ledCommand = "2:";
                // send first 2 numbers
                for (int i = 0; i < 9; i++) {
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                }

                // send colon
                ledCommand += (clock.segments.get(9).toString());
                ledCommand += (clock.segments.get(10).toString());


                //last 2 numbers
                for (int i = 11; i < 25; i++) {
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                }

                // AM/PM
                for (int i = 25; i < 27; i++) {
                    ledCommand += (clock.segments.get(i).toString());
                    ledCommand += (clock.segments.get(i).toString());
                }
                System.out.println(ledCommand);
                output.print(ledCommand);
                output.flush();
            }
        }
        if (isAlive) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }

            // take arduino out of startup mode
            System.out.println("5:");
            System.out.println("taking out of setUpMode");
            output.print("5:");
            output.flush();
        }
        if (isAlive) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }
            if (!timer.equals("")) {
                System.out.println("6:" + timer);
                output.print("6:" + timer);
                output.flush();
            }
            else {
                System.out.println("7:");
                output.print("7:"); // return to clock mode if in timer mode
                output.flush();
            }
        }
        if (isAlive) {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
        }
    }

    void kill() {
        isAlive = false;
        output.print("5:"); // turn LEDs back on
        output.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
