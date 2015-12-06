/**
 * A class representing a pit in the rows of a Mancala board.
 */
public final class PitModel extends Model
{
    private int stones;
    private int lastStonesState;

    public PitModel()
    {
        this(0);
    }

    public PitModel(int stones)
    {
        this.stones = stones;
        this.lastStonesState = stones;
    }

    /**
     * Adds a stone to the pit.
     */
    public void addStone()
    {
        stones++;
        invokeListeners();
    }

    /**
     * Removes all stones from the pit. Returns the amount of stones
     * removed from the pit.
     *
     * @return The amount of stones removed
     */
    public int removeAllStones()
    {
        int stones = this.stones;
        this.stones = 0;
        invokeListeners();
        return stones;
    }

    /**
     * Returns the amount of stones in this pit.
     *
     * @return The amount of stones
     */
    public int getStones()
    {
        return stones;
    }

    /**
     * Returns true if the pit is empty, false otherwise.
     *
     * @return True if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return stones == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commitChange()
    {
        lastStonesState = stones;
        invokeListeners();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undoChange()
    {
        stones = lastStonesState;
        invokeListeners();
    }

    /**
     * Returns a string representation of the class showing its data. Useful debugging.
     *
     * @return A string
     */
    @Override
    public String toString()
    {
        return String.format("PitModel[stones=%d, lastStonesState=%d]", stones, lastStonesState);
    }
}
