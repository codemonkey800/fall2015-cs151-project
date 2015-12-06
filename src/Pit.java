import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A view representing a pit. A pit displays a count of how many stones
 * are inside of the pit in the center. The view is themeable using the
 * {@code BoardTheme} interface, via the strategy pattern.
 */
public class Pit extends JComponent
{
    private static final Font PIT_FONT = new Font("Comic Sans MS", Font.BOLD, 56);

    protected BoardTheme theme;
    protected int        stoneCount;

    /**
     * Constructs a new pit with {@code stoneCount} stones.
     *
     * @param stoneCount The amount of stones
     * @see #Pit(int, BoardTheme)
     */
    public Pit(int stoneCount)
    {
        this(stoneCount, BoardTheme.THEME_1);
    }

    /**
     * Constructs a new pit with {@code stoneCount} stones
     * and a theme.
     *
     * @param stoneCount The amount of stones
     * @param theme      The theme
     */
    public Pit(int stoneCount, BoardTheme theme)
    {
        this.stoneCount = stoneCount;
        this.theme = theme;
    }

    /**
     * Returns the amount of stones in this pit.
     *
     * @return The amount of stones.
     */
    public int getStoneCount()
    {
        return stoneCount;
    }

    /**
     * Sets the stone count in the pit.
     *
     * @param stoneCount The amount of stones
     */
    public void setStoneCount(int stoneCount)
    {
        if(stoneCount < 0) throw new RuntimeException("Stone count must be >= 0");
        this.stoneCount = stoneCount;
        repaint();
    }

    /**
     * Returns the current theme.
     *
     * @return The theme
     */
    public BoardTheme getTheme()
    {
        return theme;
    }

    /**
     * Sets the theme for this pit.
     *
     * @param theme The theme
     */
    public void setTheme(BoardTheme theme)
    {
        this.theme = theme;
        repaint();
    }

    /**
     * Paints the pit view. Layout and positioning should be handled by the
     * top level component.
     *
     * @param g The graphics object
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle r = getBounds();
        g2.setColor(theme.getPitColor());
        // Paint different shapes depending on the shape type
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

    /**
     * Paints the stone count in the center of the pit.
     *
     * @param g2 The graphics object
     */
    protected void paintStoneCount(Graphics2D g2)
    {
        g2.setColor(theme.getStoneCountColor());
        g2.setFont(PIT_FONT);

        String      str         = String.valueOf(stoneCount);
        FontMetrics fontMetrics = g2.getFontMetrics();
        Rectangle   rect        = getBounds();
        Rectangle2D stringRect  = fontMetrics.getStringBounds(str, g2);
        int         x           = (rect.width - (int) stringRect.getWidth()) / 2;
        int         y           = (rect.height - (int) stringRect.getHeight()) / 2 + fontMetrics.getAscent();
        g2.drawString(str, x, y);
    }
}
