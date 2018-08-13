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
    public UpdateColors(PaintClock clock, SerialPort chosenPort, String animation) {
        this.clock = clock;
        this.chosenPort = chosenPort;
        isAlive = true;
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
                Thread.sleep(1500);
            } catch (Exception e) {
            }

            // get the current time + 1 minute to send
            Date date = new Date();
            date = new Date(date.getTime() + 60000); // add 1 minute to the time
            String strDateFormat = "hh:mma";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String currentTime = dateFormat.format(date);
            System.out.println("1: " + currentTime);
            // actually send the current time
            output.print("1: " + currentTime);
            output.flush();
        }

        if (isAlive) {
            try {
                Thread.sleep(1500);
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
                    // ledCommand += ((i * 3) + ",");
                    ledCommand += (clock.segments.get(i).toString());

                    // ledCommand += ((i * 3 + 1) + ",");
                    ledCommand += (clock.segments.get(i).toString());

                    // ledCommand += ((i * 3 + 2) + ",");
                    ledCommand += (clock.segments.get(i).toString());
                }

                // send colon
                // ledCommand += ((27) + ","); // LED #30
                ledCommand += (clock.segments.get(9).toString());

                // ledCommand += ((28) + ","); //LED #31
                ledCommand += (clock.segments.get(10).toString());


                //last 2 numbers
                for (int i = 11; i < 25; i++) {
                    // ledCommand += ((i * 3 - 4) + ",");
                    ledCommand += (clock.segments.get(i).toString());

                    // ledCommand += ((i * 3 - 3) + ",");
                    ledCommand += (clock.segments.get(i).toString());

                    // ledCommand += ((i * 3 - 2) + ",");
                    ledCommand += (clock.segments.get(i).toString());
                }

                // AM/PM and reverse order Sat - Sun
                for (int i = 25; i < 27; i++) {
                    // ledCommand += ((46 + i) + ","); //LED #74 - 82
                    ledCommand += (clock.segments.get(i).toString());
                }
                System.out.println(ledCommand);
                output.print(ledCommand);
                output.flush();
            }
        }
        if (isAlive) {
            try {
                Thread.sleep(1500);
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
                Thread.sleep(1500);
            } catch (Exception e) {
            }
        }
    }

    void kill() {
        isAlive = false;
        output.print("5:"); // turn LEDs back on
        output.flush();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
