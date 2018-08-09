import java.awt.*;

/**
 * Created by calebflynn on 8/8/18.
 */
public class Text extends LineSegment {
    int fontSize;

    public Text(int idNum) {
        super(idNum);
        this.idNum = idNum;
        this.x1 = 0;
        this.y1 = 0;
        this.fontSize = 70;
    }

    public void drawString(Graphics2D g2, String str, int x, int y) {
        this.x1 = x;
        this.x2 = x;
        this.y1 = y;
        this.y2 = y;
        g2.setColor(this.color);
        g2.setFont(new Font("Arial", Font.PLAIN, fontSize));
        if (selected) {
            g2.setFont(new Font("Arial", Font.BOLD, fontSize));
        }
        g2.drawString(str, x, y);
    }

    public boolean contains(int x, int y) {
        int width = getLineWidth() * 10;
        if (x >= x1 && x <= x1 + width && y <= y1 && y >= y1 - (2 * width / 3))
            return true;
        return false;
    }

}
