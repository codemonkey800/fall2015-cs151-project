import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Jeremy Asuncion
 */
public final class Board extends JPanel
{
    private static final int PADDING = 16;
    private static final int GAP     = 16;

    private MancalaGame game;
    private BoardTheme  theme;

    private Mancala[]     mancalas;
    private Pit[][]       pits;
    private BoardListener listener;

    private int dimen;

    public Board(MancalaGame game)
    {
        this(game, BoardTheme.THEME_1);
    }

    public Board(MancalaGame game, BoardTheme theme)
    {
        this.game = game;
        this.theme = theme;
        setLayout(null);

        mancalas = new Mancala[MancalaGame.MAX_PLAYERS];
        for(int i = 0; i < MancalaGame.MAX_PLAYERS; i++)
        {
            final int pos = i;
            mancalas[i] = new Mancala(0, theme);
            add(mancalas[i]);
            mancalas[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(listener != null)
                    {
                        listener.mancalaClicked(pos ^ 1);
                    }
                }
            });
            game.getMancalaModel(i ^ 1).addChangeListener(e -> {
                MancalaModel model = (MancalaModel) e.getSource();
                mancalas[pos].setStoneCount(model.getStones());
            });
        }

        pits = new Pit[MancalaGame.MAX_PLAYERS][MancalaGame.MAX_PITS];
        int stones = game.getPitModel(MancalaGame.PLAYER_A, 0).getStones();
        for(int i = 0; i < MancalaGame.MAX_PITS; i++)
        {
            final int pos = i;
            Pit pitA = new Pit(stones, theme);
            Pit pitB = new Pit(stones, theme);

            pits[1][i] = pitA;
            pits[0][MancalaGame.MAX_PITS - i - 1] = pitB;

            game.getPitModel(MancalaGame.PLAYER_A, i).addChangeListener(e -> {
                PitModel model = (PitModel) e.getSource();
                pitA.setStoneCount(model.getStones());
            });
            game.getPitModel(MancalaGame.PLAYER_B, MancalaGame.MAX_PITS - i - 1).addChangeListener(e -> {
                PitModel model = (PitModel) e.getSource();
                pitB.setStoneCount(model.getStones());
            });

            add(pitA);
            add(pitB);

            pitA.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(listener != null)
                    {
                        listener.pitClicked(MancalaGame.PLAYER_A, pos);
                    }
                }
            });

            pitB.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if(listener != null)
                    {
                        listener.pitClicked(MancalaGame.PLAYER_B,
                                            MancalaGame.MAX_PITS - pos - 1);
                    }
                }
            });
        }

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                int width = getWidth();
                int height = getHeight();
                dimen = (width - PADDING * 2 - GAP * 7) / 8;

                int mancalaHeight = height - PADDING * 2;

                mancalas[0].setBounds(PADDING, PADDING,
                                      dimen, mancalaHeight);
                mancalas[1].setBounds(width - PADDING - dimen,
                                      height - PADDING - mancalaHeight,
                                      dimen, mancalaHeight);

                for(int i = 0; i < MancalaGame.MAX_PITS; i++)
                {
                    int x = PADDING * (i + 2) + dimen * (i + 1);
                    int y1 = PADDING;
                    int y2 = height - PADDING - dimen;
                    pits[1][i].setBounds(x, y2, dimen, dimen);
                    pits[0][MancalaGame.MAX_PITS - i - 1].setBounds(x, y1, dimen, dimen);
                }
            }
        });
    }

    public BoardTheme getTheme()
    {
        return theme;
    }

    public void setTheme(BoardTheme theme)
    {
        this.theme = theme;
        for(int i = 0; i < mancalas.length; i++) {
            mancalas[i].setTheme(theme);
            for(int j = 0; j < pits[0].length; j++) {
                pits[i][j].setTheme(theme);
            }
        }
        repaint();
    }

    public void setBoardListener(BoardListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Rectangle r = getBounds();
        g.setColor(theme.getBoardColor());
        g.fillRect(0, 0, r.width, r.height);
    }

    public interface BoardListener
    {
        default void mancalaClicked(int player)
        {
        }

        default void pitClicked(int player, int position)
        {
        }
    }
}
