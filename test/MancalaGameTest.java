import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the Mancala game
 *
 * @author Jeremy Asuncion
 */
public final class MancalaGameTest
{
    // The actual Mancala game
    private MancalaGame game;
    // Player mancalas
    int[]   mancalas;
    // A matrix representation of a mancala board
    int[][] board;

    /**
     * Sets up the Mancala game and test data.
     *
     * @throws Exception Because unit test
     */
    @Before
    public void setUp() throws Exception
    {
        game = new MancalaGame(MancalaGame.MAX_INITIAL_STONES);
        mancalas = new int[MancalaGame.MAX_PLAYERS];
        board = new int[MancalaGame.MAX_PLAYERS][MancalaGame.MAX_PITS];

        // Attaches a change listener to each mancala and pit.
        // The change listeners set the data changes to the array
        // and matrix representations
        for(int i = 0; i < MancalaGame.MAX_PLAYERS; i++)
        {
            final int player = i;
            game.getMancalaModel(i).addChangeListener(event -> {
                mancalas[player] = ((MancalaModel) event.getSource()).getStones();
            });

            for(int j = 0; j < MancalaGame.MAX_PITS; j++)
            {
                final int pit = j;
                game.getPitModel(player, pit).addChangeListener(event -> {
                    board[player][pit] = ((PitModel) event.getSource()).getStones();
                });

                board[player][pit] = MancalaGame.MAX_INITIAL_STONES;
            }
        }
    }

    /**
     * Clears the listeners from the game so that they can be candidates
     * for garbage collection.
     *
     * @throws Exception Because unit test
     */
    @After
    public void tearDown() throws Exception
    {
        game.clearListeners();
    }

    /**
     * Tests selection from both players.
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testStoneSelect() throws Exception
    {
        game.selectPit(3);
        game.commitLastSelection();
        assertEquals(game.getCurrentPlayer(), MancalaGame.PLAYER_B);

        game.selectPit(4);
        game.commitLastSelection();
        assertEquals(game.getCurrentPlayer(), MancalaGame.PLAYER_A);

        int[] expectedMancalas = {1, 1};
        int[] expectedRowA = {5, 5, 4, 0, 5, 5};
        int[] expectedRowB = {5, 4, 4, 4, 0, 5};

        assertArrayEquals(expectedMancalas, mancalas);
        assertArrayEquals(expectedRowA, board[MancalaGame.PLAYER_A]);
        assertArrayEquals(expectedRowB, board[MancalaGame.PLAYER_B]);
    }

    /**
     * Tests a successful undo selection.
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testUndoSelection() throws Exception
    {
        int[] row = {4, 4, 4, 4, 4, 4};

        assertArrayEquals(row, board[MancalaGame.PLAYER_A]);
        game.selectPit(0);
        game.undoLastSelection();
        assertArrayEquals(row, board[MancalaGame.PLAYER_A]);
    }

    /**
     * Tests a failed undo selection.
     *
     * @throws Exception Because unit test
     */
    @Test(expected = RuntimeException.class)
    public void testUndoSelectionFailed() throws Exception
    {
        game.selectPit(0);
        game.undoLastSelection();
        game.selectPit(1);
        game.undoLastSelection();
    }

    /**
     * Tests the rule where a player gets an extra turn if they stop on their
     * Mancala.
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testExtraTurn() throws Exception
    {
        assertEquals(MancalaGame.PLAYER_A, game.getCurrentPlayer());
        game.selectPit(2);
        game.commitLastSelection();
        assertEquals(MancalaGame.PLAYER_A, game.getCurrentPlayer());
    }

    /**
     * Test the rule where if a player selects a pit such that
     * the last stone ends on an empty pit, the opposite pit's
     * stones are added to the player's Mancala.
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testPitSelectEndOnEmpty() throws Exception
    {
        game.selectPit(4);
        game.commitLastSelection();
        game.selectPit(0);
        game.commitLastSelection();
        game.selectPit(0);
        game.commitLastSelection();

        assertEquals(8, mancalas[MancalaGame.PLAYER_A]);
    }

    /**
     * Tests the same rule as above, but for the case where
     * the empty pit is all the way on the left.
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testPitSelectEndOnEmptyLeft() throws Exception
    {
        runWinningGame(game);
        assertEquals(12, mancalas[MancalaGame.PLAYER_A]);
    }

    /**
     * Tests the same rule as above, but for the case where
     * the empty pit is all the way on the right.
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testPitSelectEndOnEmptyRight() throws Exception
    {
        game.selectPit(5);
        game.commitLastSelection();
        game.selectPit(2);
        game.commitLastSelection();
        game.selectPit(1);
        game.commitLastSelection();

        assertEquals(7, mancalas[MancalaGame.PLAYER_A]);
    }

    /**
     * Test a game where the player wins
     *
     * @throws Exception Because unit test
     */
    @Test
    public void testGameWin() throws Exception
    {
        runWinningGame(game);
        assertEquals(game.getWinningPlayer(), MancalaGame.PLAYER_B);
    }

    /**
     * Runs the {@code game} object through a winning game.
     *
     * @param game A Mancala game object
     */
    private void runWinningGame(MancalaGame game)
    {
        game.selectPit(0);
        game.commitLastSelection();
        game.selectPit(0);
        game.commitLastSelection();
        game.selectPit(1);
        game.commitLastSelection();
        game.selectPit(2);
        game.commitLastSelection();
        game.selectPit(0);
        game.commitLastSelection();
        game.selectPit(3);
        game.commitLastSelection();
        game.selectPit(0);
        game.commitLastSelection();
        game.selectPit(4);
        game.commitLastSelection();
        game.selectPit(0);
        game.commitLastSelection();
        game.selectPit(5);
        game.commitLastSelection();
    }
}
