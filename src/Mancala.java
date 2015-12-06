import java.awt.*;

/**
 * The mancala view. Naturally an extension of the Pit view class. The
 * mancala view is also themeable via the {@code BoardTheme} class.
 *
 * @see Pit
 */
public final class Mancala extends Pit
{
    private static final int MANCALA_CORNER_RADIUS = 64;

    /**
     * Constructs a new mancala with {@code stoneCount} stones.
     *
     * @param stoneCount The amount of stones
     * @see #Mancala(int, BoardTheme)
     */
    public Mancala(int stoneCount)
    {
        super(stoneCount);
    }

    /**
     * Constructs a new mancala with {@code stoneCount} stones and a theme.
     *
     * @param stoneCount The amount of stones
     * @param theme      The theme
     */
    public Mancala(int stoneCount, BoardTheme theme)
    {
        super(stoneCount, theme);
    }

    /**
     * Paints the mancala.
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
        g2.setColor(theme.getMancalaColor());
        g2.fillRoundRect(0, 0,
                         r.width, r.height,
                         MANCALA_CORNER_RADIUS, MANCALA_CORNER_RADIUS);

        paintStoneCount(g2);
    }
}
