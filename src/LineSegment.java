import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by calebflynn on 8/8/18.
 */
public class LineSegment {
    int lineWidth = 10;
    int x1;
    int x2;
    int y1;
    int y2;
    int idNum;
    Color color;
    boolean selected = false;

    public LineSegment() {
        this(0);
    }

    public LineSegment(int idNum) {
        this.idNum = idNum;
        this.x1 = 0;
        this.x2 = 0;
        this.y1 = 0;
        this.y2 = 0;
        this.color = Color.black;
    }

    public void drawLine(Graphics2D g2, int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        g2.setStroke(new BasicStroke(lineWidth));
        if (selected) {
            g2.setStroke(new BasicStroke(lineWidth+6));
        }
        g2.setColor(this.color);
        g2.draw(new Line2D.Float(x1, y1, x2, y2));
    }

    public void drawString(Graphics2D g2, String str, int x, int y) {
        // do nothing
    }

    public void setSelected() {
        selected = true;
    }

    public void setUnselected() {
        selected = false;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getId() {
        return this.idNum;
    }

    public int getLineWidth() { return this.lineWidth; }

    public boolean isSelected() {
        if (selected) {
            return true;
        }
        return false;
    }

    public boolean contains(int x, int y) {
        int width = lineWidth / 2;
        if ((x >= x1 - width && x <= x2 + width) || (x >= x2 - width && x <= x1 + width))
            if ((y >= y1 - width && y <= y2 + width) || (y >= y2 - width && y <= y1 + width))
                return true;
        return false;
    }

    public String toString() {
        // return string "redColor, greenColor, blueColor"
        return  String.format("%02X", getColor().getRed()) +
                String.format("%02X", getColor().getGreen()) +
                String.format("%02X", getColor().getBlue());
    }

}
