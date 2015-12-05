import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Jeremy Asuncion
 */
public class Pit extends JComponent
{
    private static final Font PIT_FONT = new Font("Comic Sans MS", Font.BOLD, 56);

    protected BoardTheme theme;
    protected int        stoneCount;

    public Pit(int stoneCount)
    {
        this(stoneCount, BoardTheme.THEME_1);
    }

    public Pit(int stoneCount, BoardTheme theme)
    {
        this.stoneCount = stoneCount;
        this.theme = theme;
    }

    public int getStoneCount()
    {
        return stoneCount;
    }

    public void setStoneCount(int stoneCount)
    {
        if(stoneCount < 0) throw new RuntimeException("Stone count must be >= 0");
        this.stoneCount = stoneCount;
        repaint();
    }

    public BoardTheme getTheme()
    {
        return theme;
    }

    public void setTheme(BoardTheme theme)
    {
        this.theme = theme;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle r = getBounds();
        g2.setColor(theme.getPitColor());
        switch(theme.getPitShape())
        {
            case CIRCLE:
                g2.fillOval(0, 0, r.width, r.height);
                break;
            case SQUARE:
                g2.fillRect(0, 0, r.width, r.height);
                break;
        }

        paintStoneCount(g2);
    }

    protected void paintStoneCount(Graphics2D g2)
    {
        g2.setColor(theme.getStoneCountColor());
        g2.setFont(PIT_FONT);

        String str = String.valueOf(stoneCount);
        FontMetrics fontMetrics = g2.getFontMetrics();
        Rectangle rect = getBounds();
        Rectangle2D stringRect = fontMetrics.getStringBounds(str, g2);
        int x = (rect.width - (int) stringRect.getWidth()) / 2;
        int y = (rect.height - (int) stringRect.getHeight()) / 2 + fontMetrics.getAscent();
        g2.drawString(str, x, y);
    }
}
