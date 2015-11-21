import javax.swing.event.ChangeListener;

/**
 * An implementation of the Mancala game using two arrays to hold both
 * player's pits.
 *
 * @author Jeremy Asuncion
 */
public final class MancalaGame
{
    /**
     * The maximum amount of players allowed in a game.
     */
    public static final int MAX_PLAYERS        = 2;
    /**
     * An integer constant representing player A.
     */
    public static final int PLAYER_A           = 0;
    /**
     * An integer constant representing player B.
     */
    public static final int PLAYER_B           = 1;
    /**
     * The maximum amount of pits in a game.
     */
    public static final int MAX_PITS           = 6;
    /**
     * The minimum amount of initial stones allowed for each pit.
     */
    public static final int MIN_INITIAL_STONES = 3;
    /**
     * The maximum amount of initial stones allowed for each pit.
     */
    public static final int MAX_INITIAL_STONES = 4;

    /* Date Models */
    private MancalaModel[] mancalas = new MancalaModel[MAX_PLAYERS];
    // A 2x6 array representation of the pits using the pit data models
    private PitModel[][] board;

    /* Game State */
    // Currently player A's turn
    private int     currentPlayer    = PLAYER_A;
    // Indicates the current player earned an extra turn
    // for landing the last stone in their Mancala pit.
    private boolean hasExtraTurn     = false;
    // Indicates the current player has an undo available to use.
    // The undo may be used once. This flag is set to false
    // After the player uses it.
    private boolean hasUndoAvailable = true;
    // Indicates that there is a pending commit.
    // Calls to selectPit() will not work unless this flag is false.
    // Similarly, calls to commitLastSelection() and undoLastSelection()
    // will not work unless this flag is true.
    private boolean hasPendingCommit = false;
    // The player who won the game. Set to -1 by default
    // When this value is no longer -1, calls to selectPit(),
    // commitLastSelection(), and undoLastSelection() will not follow through
    private int     winningPlayer    = -1;


    /**
     * Constructs a new Mancala game using the default pit stone count, 3.
     *
     * @see #MancalaGame(int)
     */
    public MancalaGame()
    {
        this(MIN_INITIAL_STONES);
    }

    /**
     * Constructs a new Mancala game with {@code initialStones} stones
     * filled in each pit.
     *
     * @param initialStones The amount of stones to fill in each pit
     */
    public MancalaGame(int initialStones)
    {
        if(initialStones < MIN_INITIAL_STONES || initialStones > MAX_INITIAL_STONES)
        {
            throw new RuntimeException("Initial stones is not >=" + MIN_INITIAL_STONES +
                                       " nor <=" + MAX_INITIAL_STONES);
        }

        mancalas = new MancalaModel[MAX_PLAYERS];
        mancalas[PLAYER_A] = new MancalaModel();
        mancalas[PLAYER_B] = new MancalaModel();

        board = new PitModel[2][MAX_PITS];

        for(int i = 0; i < MAX_PITS; i++)
        {
            board[PLAYER_A][i] = new PitModel(initialStones);
            board[PLAYER_B][i] = new PitModel(initialStones);
        }
    }

    /**
     * Add a change listener to the specified player's Mancala.
     *
     * @param player   The player to attach a listener to
     * @param listener The listener
     */
    public void addChangeListenerToMancala(int player, ChangeListener listener)
    {
        mancalas[player].addChangeListener(listener);
    }

    /**
     * Adds a change listener to the specified player's pit.
     *
     * @param player   The player to attach a listener to
     * @param position The position of the pit
     * @param listener The listener
     */
    public void addChangeListenerToPit(int player, int position, ChangeListener listener)
    {
        board[player][position].addChangeListener(listener);
    }

    /**
     * Clears all listeners for both Mancalas and pits
     */
    public void clearListeners()
    {
        for(int i = 0; i < MAX_PLAYERS; i++)
        {
            mancalas[i].clearChangeListeners();
            for(int j = 0; j < MAX_PITS; j++)
            {
                board[i][j].clearChangeListeners();
            }
        }
    }

    /**
     * Returns the current player.
     *
     * @return The current player.
     */
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * Returns the winning player. Returns -1
     * if the game is still going and no one has won.
     *
     * @return The winning player, or -1 if no one has won
     */
    public int getWinningPlayer()
    {
        return winningPlayer;
    }

    /**
     * Returns true if the game is over, false if not.
     *
     * @return True if game over, false if not
     */
    public boolean isGameOver()
    {
        return winningPlayer != -1;
    }

    /**
     * Selects a pit for the current player at the specified position, and distributes
     * the stones according to the rules of the Mancala game.
     *
     * @param position The position of the pit. The value should be within {@code 0 <= position < 6}
     * @throws ArrayIndexOutOfBoundsException If the position is not within {@code 0 <= position < 6}
     * @throws RuntimeException               If the game is over or if there is a pending commit
     */
    public void selectPit(int position)
    {
        if(winningPlayer != -1) throw new RuntimeException("The game is already over");
        if(hasPendingCommit) throw new RuntimeException("The game's data has to be committed first");

        if(position < 0 || position >= MAX_PITS)
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        // This is the heart of the game yo
        int row = currentPlayer;
        int stones = board[row][position++].removeAllStones();

        // If the pit is empty, then return
        // so that the user can make a valid selection.
        if(stones == 0) return;

        hasPendingCommit = true;

        while(stones > 0)
        {
            // Add a stone to the next pit in the sequence
            // until we reach the end of the row, or until we are out of stones
            for(; position < MAX_PITS && stones > 0; position++)
            {
                board[row][position].addStone();
                stones--;
            }

            // If there are still stones present
            // and we are on the current player's row,
            // add a stone to their Mancala
            if(stones > 0 && row == currentPlayer)
            {
                mancalas[row].addStone();
                stones--;

                // If we stop at the Mancala, then the player gets another turn,
                // so we end the loop here. We set the hasExtraTurn variable
                // to true so that the player value doesn't get reassigned
                if(stones == 0 && position == MAX_PITS)
                {
                    hasExtraTurn = true;
                    break;
                }
            }

            // If the board ends up on the current player's row,
            // then we take the opposing player's stones from opposite pit
            // Since we have already added the stones, we need to check
            // the pit before the current one. If the pit was empty,
            // then there should only be 1 stone in there.
            if(stones == 0 && row == currentPlayer &&
               position - 1 < MAX_PITS && board[row][position - 1].getStones() == 1)
            {

                // Since we aligned the rows on index, we need to reverse
                // the index to get the opposite pit.
                // Moreover, we are checking position - 1, so the index should be:
                // i = MAX_PITS - (position - 1) + 1
                //   = MAX_PITS - position
                int oppositeStones = board[row ^ 1][MAX_PITS - position].removeAllStones();
                // We also remove the stone from that pit
                oppositeStones += board[row][position - 1].removeAllStones();
                mancalas[row].addStones(oppositeStones);
            }

            row ^= 1;
            // Start on opposite player's row
            position = 0;
        }
    }

    /**
     * Commits the last selection to the pit data models. After this method is called,
     * the listeners of the data models are invoked.
     * <p>
     * When the game data is committed to the models, the game checks for a winner.
     * If there is a winner, {@code getWinningPlayer()} will return a positive
     * value (Either {@code PLAYER_A} or {@code PLAYER_B}), and further calls to
     * any commands will cause {@code RuntimeExceptions}
     *
     * @throws RuntimeException If the game is over or if there is no pending commit
     */
    public void commitLastSelection()
    {
        if(winningPlayer != -1) throw new RuntimeException("The game is already over");
        if(!hasPendingCommit) throw new RuntimeException("There is no pending data commit");

        for(int i = 0; i < MAX_PLAYERS; i++)
        {
            mancalas[i].commitChange();

            for(int j = 0; j < MAX_PITS; j++)
            {
                board[i][j].commitChange();
            }
        }
        hasPendingCommit = false;
        hasUndoAvailable = true;
        if(hasExtraTurn)
        {
            hasExtraTurn = false;
        }
        else
        {
            currentPlayer ^= 1;
        }
        checkForWinners();
    }

    /**
     * Undos the last selection if the undo is available. Returns
     * if the undo was successful. This call will return false if
     * the game is over, if there is no pending commit, or if
     * there is no undo available.
     *
     * @throws RuntimeException If the game is already over,
     *                          if there is no pending commit,
     *                          or if there is no undo available
     */
    public void undoLastSelection()
    {
        if(winningPlayer != -1) throw new RuntimeException("The game is already over");
        if(!hasPendingCommit) throw new RuntimeException("There is no pending data commit");
        if(!hasUndoAvailable) throw new RuntimeException("The current player cannot undo multiple times");

        for(int i = 0; i < MAX_PLAYERS; i++)
        {
            mancalas[i].undoChange();

            for(int j = 0; j < MAX_PITS; j++)
            {
                board[i][j].undoChange();
            }
        }
        hasUndoAvailable = false;
    }

    /**
     * Checks the board for winners and assigns the appropriate variables in the
     * case that happens.
     */
    private void checkForWinners()
    {
        for(int i = 0; i < MAX_PLAYERS; i++)
        {
            boolean isRowEmpty = true;
            for(int j = 0; j < MAX_PITS && isRowEmpty; j++)
            {
                if(!board[i][j].isEmpty()) isRowEmpty = false;
            }

            // If a row is empty, then we
            // sum up the total stones
            // in the opposite row and add it to that
            // player's Mancala
            if(isRowEmpty)
            {
                int stones = 0;
                int player = i == PLAYER_A ? PLAYER_B : PLAYER_A;
                for(int j = 0; j < MAX_PITS; j++)
                {
                    stones += board[player][j].removeAllStones();
                }
                mancalas[player].addStones(stones);

                // We assign the winning player
                // to be the player with the most stones.
                winningPlayer = mancalas[PLAYER_A].getStones() > mancalas[PLAYER_B].getStones() ?
                                PLAYER_A :
                                PLAYER_B;

                break;
            }
        }
    }
}
