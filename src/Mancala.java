import java.awt.*;

/**
 * @author Jeremy Asuncion
 */
public final class Mancala extends Pit
{
    private static final int MANCALA_CORNER_RADIUS = 64;

    public Mancala(int stoneCount)
    {
        super(stoneCount);
    }

    public Mancala(int stoneCount, BoardTheme theme)
    {
        super(stoneCount, theme);
    }

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
        g2.drawString(String.valueOf(stoneCount), r.width / 2, r.height / 2);
    }
}
