import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * Main class. Acts as the main controller for the application.
 */
public final class MancalaTester
{
    private static MancalaGame game;
    private static Board       board;

    private static JFrame frame;

    /**
     * The entry point of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
//            String[] options = {
//                    "3 stones",
//                    "4 stones"
//            };
//            int selection = JOptionPane.showOptionDialog(frame,
//                                                         "How many stones per pit?",
//                                                         "Game Options",
//                                                         JOptionPane.YES_NO_OPTION,
//                                                         JOptionPane.QUESTION_MESSAGE,
//                                                         null,
//                                                         options,
//                                                         options[0]);

            game = new MancalaGame(MancalaGame.MIN_INITIAL_STONES);
            initUI();
            initMenuBar();
        });

    }

    private static void initUI()
    {
        frame = new JFrame("Mancala Game");
        board = new Board(game);

        board.setBoardListener(new Board.BoardListener()
        {
            @Override
            public void pitClicked(int player, int position)
            {
                if(game.isGameOver()) {
                    JOptionPane.showMessageDialog(frame,
                                                  "Player " + (position == 0 ? "A" : "B") + "won!",
                                                  "Winner!",
                                                  JOptionPane.INFORMATION_MESSAGE);
                } else if(player == game.getCurrentPlayer()) {
                    System.out.println("Pit" + (player == 0 ? "A" : "B") + position + " selected!");
                    game.selectPit(position);
                    game.commitLastSelection();
                }
            }
        });

        Container pane = frame.getContentPane();
        pane.add(board, BorderLayout.CENTER);

        JTextField playerADirection = new JTextField("Player A ->");
        JTextField playerBDirection = new JTextField("<- Player B");

        playerADirection.setHorizontalAlignment(JTextField.CENTER);
        playerBDirection.setHorizontalAlignment(JTextField.CENTER);

        Font directionFont = new Font("Comic Sans MS", Font.BOLD, 24);
        playerADirection.setFont(directionFont);
        playerBDirection.setFont(directionFont);

        playerADirection.setEditable(false);
        playerBDirection.setEditable(false);

        pane.add(playerADirection, BorderLayout.PAGE_END);
        pane.add(playerBDirection, BorderLayout.PAGE_START);

        setUIFont(new FontUIResource("Comic Sans MS", Font.BOLD, 16));
        frame.setResizable(false);
        frame.setSize(1280, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void initMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = new JMenu("View");
        JMenuItem theme1Item = new JMenuItem("Theme 1");
        JMenuItem theme2Item = new JMenuItem("Theme 2");

        Font font = Font.getFont("Comic Sans MS");
        theme1Item.setFont(font);
        theme2Item.setFont(font);

        viewMenu.add(theme1Item);
        viewMenu.add(theme2Item);
        menuBar.add(viewMenu);

        theme1Item.addActionListener(e -> board.setTheme(BoardTheme.THEME_1));
        theme2Item.addActionListener(e -> board.setTheme(BoardTheme.THEME_2));

        frame.setJMenuBar(menuBar);
    }

    /**
     * Sets the global font of the Swing application. Found on
     * <a href="http://stackoverflow.com/a/5824460">StackOverflow</a>.
     *
     * @param fontResource The font resource
     */
    private static void setUIFont(FontUIResource fontResource)
    {
        Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value != null && value instanceof FontUIResource)
            {
                UIManager.put(key, fontResource);
            }
        }
    }
}
