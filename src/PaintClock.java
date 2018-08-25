import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by calebflynn on 8/8/18.
 */

public class PaintClock extends JPanel implements MouseListener {
    int numberHeight;
    int numberWidth;
    int windowPadding = 50;
    public int lineWidth = 10;
    int numberPadding = lineWidth;
    int screenWidth;
    int screenHeight;
    public ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public PaintClock() {
        addMouseListener(this);
        // initialize numbers
        for (int i = 0; i <= 24; i++) {
            segments.add(new LineSegment(i));
        }
        // initialize AM PM and days of week
        for (int i = 25; i <= 27; i++)
            segments.add(new Text(i));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //get screen width and height
        screenWidth = getWidth();
        screenHeight = getHeight();
        numberHeight = Math.min(((screenHeight - 2 * windowPadding - 3 * numberPadding) / 2),
                (screenWidth - 2 * windowPadding) / 4 - 3 * 3 * numberPadding);
        numberWidth = numberHeight;


        Graphics2D g2 = (Graphics2D) g;
        setBackground(Color.white);  // set background color for this JPanel

        //draw numbers:
        g.setColor(new Color(1,1,1)); //TODO: CHANGE TO GET COLOR OF SPECIFIC PART

        /**
         * First number:
         */
        // bottom-right
        segments.get(0).drawLine(g2, windowPadding + numberWidth + numberPadding * 2 + (0 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 3,
                windowPadding + numberWidth + numberPadding * 2 + (0 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight * 2 + numberPadding * 3);

        // top right
        segments.get(1).drawLine(g2, windowPadding + numberWidth + numberPadding * 2 + (0 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberPadding,
                windowPadding + numberWidth + numberPadding * 2 + (0 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberPadding + numberWidth);


        /**
         * Second number:
         */
        // top left
        segments.get(2).drawLine(g2, windowPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberPadding,
                windowPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberPadding + numberHeight);
        // top
        segments.get(3).drawLine(g2, windowPadding + numberPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding,
                windowPadding + numberPadding + numberWidth + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding);
        // top right
        segments.get(4).drawLine(g2, windowPadding + numberWidth + numberPadding * 2 + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberPadding,
                windowPadding + numberWidth + numberPadding * 2 + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberPadding + numberWidth);
        // middle
        segments.get(5).drawLine(g2, windowPadding + numberPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 2,
                windowPadding + numberPadding + numberWidth + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 2);
        // bottom-left
        segments.get(6).drawLine(g2, windowPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 3,
                windowPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight * 2 + numberPadding * 3);
        // bottom
        segments.get(7).drawLine(g2, windowPadding + numberPadding + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight * 2 + numberPadding * 4,
                windowPadding + numberPadding + numberWidth + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight * 2 + numberPadding * 4);
        // bottom-right
        segments.get(8).drawLine(g2, windowPadding + numberWidth + numberPadding * 2 + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 3,
                windowPadding + numberWidth + numberPadding * 2 + (1 * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight * 2 + numberPadding * 3);

        /**
         * colon:
         */
        // bottom colon
        segments.get(9).drawLine(g2, windowPadding + numberPadding + numberWidth + numberWidth + 13 * numberPadding - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 2 + 4 * numberPadding,
                windowPadding + numberPadding + numberWidth + numberWidth + (int)(13.5 * numberPadding)+1 - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 2 + 4 * numberPadding);
        // top colon
        segments.get(10).drawLine(g2, windowPadding + numberPadding + numberWidth + numberWidth + 13 * numberPadding - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 2 - 4 * numberPadding,
                windowPadding + numberPadding + numberWidth + numberWidth + (int)(13.5 * numberPadding)+1 - numberWidth - numberPadding,
                windowPadding + numberHeight + numberPadding * 2 - 4 * numberPadding);


        /**
         * Third and fourth number
         */
        for (int i = 2; i < 4; i++) {
            // top left
            segments.get(4+7*(i-1)).drawLine(g2, windowPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberPadding,
                    windowPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberPadding + numberHeight);
            // top
            segments.get(5+7*(i-1)).drawLine(g2, windowPadding + numberPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding,
                    windowPadding + numberPadding + numberWidth + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding);
            // top right
            segments.get(6+7*(i-1)).drawLine(g2, windowPadding + numberWidth + numberPadding * 2 + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberPadding,
                    windowPadding + numberWidth + numberPadding * 2 + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberPadding + numberWidth);
            // middle
            segments.get(7+7*(i-1)).drawLine(g2, windowPadding + numberPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight + numberPadding * 2,
                    windowPadding + numberPadding + numberWidth + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight + numberPadding * 2);
            // bottom-left
            segments.get(8+7*(i-1)).drawLine(g2, windowPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight + numberPadding * 3,
                    windowPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight * 2 + numberPadding * 3);
            // bottom
            segments.get(9+7*(i-1)).drawLine(g2, windowPadding + numberPadding + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight * 2 + numberPadding * 4,
                    windowPadding + numberPadding + numberWidth + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight * 2 + numberPadding * 4);
            // bottom-right
            segments.get(10+7*(i-1)).drawLine(g2, windowPadding + numberWidth + numberPadding * 2 + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight + numberPadding * 3,
                    windowPadding + numberWidth + numberPadding * 2 + (i * (numberWidth + 9 * numberPadding)) - numberWidth - numberPadding,
                    windowPadding + numberHeight * 2 + numberPadding * 3);
        }

        /**
         * AM, PM
         */
        segments.get(25).drawString(g2, "AM", windowPadding + numberWidth + numberPadding * 2 + (3 * (numberWidth + 11 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + 3* numberPadding);
        segments.get(26).drawString(g2, "PM", windowPadding + numberWidth + numberPadding * 2 + (3 * (numberWidth + 11 * numberPadding)) - numberWidth - numberPadding,
                windowPadding + numberHeight * 2 + numberPadding * 3);
    }

    public ArrayList<LineSegment> getSelected() {
        ArrayList<LineSegment> ret = new ArrayList<LineSegment>();
        for (LineSegment segment : segments) {
            if (segment.isSelected())
                ret.add(segment);
        }
        return ret;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        boolean noneSelected = true;
        boolean allSelected = true;
        for (LineSegment segment : segments) {
            if (segment.contains(x,y)) {
                if (segment.isSelected()) {
                    segment.setUnselected();
                    allSelected = false;
                    noneSelected = false;
                }
                else {
                    segment.setSelected();
                    noneSelected = false;
                }
            }
            else {
                if (segment.isSelected()) {
                    noneSelected = false;
                }
                else {
                    allSelected = false;
                }
                // segment.setUnselected();
            }
        }
        if (noneSelected) {
            for (LineSegment segment : segments) {
                segment.setSelected();
            }
        }
        else if (allSelected) {
            for (LineSegment segment : segments) {
                segment.setUnselected();
            }
        }

        repaint();
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
}
