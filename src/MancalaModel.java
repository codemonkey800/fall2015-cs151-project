/**
 * A class representing a Mancala at the ends of a Mancala board.
 */
public final class MancalaModel extends Model
{
    private int stones;
    private int lastStonesState;

    /**
     * Adds a new stone to the Mancala.
     */
    public void addStone()
    {
        addStones(1);
    }

    /**
     * Adds a nonegative amount of stones into the Mancala.
     *
     * @param stones The amount of stones to add
     * @throws IllegalArgumentException If the amount is negative
     */
    public void addStones(int stones)
    {
        if(stones < 0) throw new IllegalArgumentException("stones cannot be negative");
        this.stones += stones;
        invokeListeners();
    }

    /**
     * Returns the amount of stones in this Mancala.
     *
     * @return The amount of stones
     */
    public int getStones()
    {
        return stones;
    }

    /**
     * Returns true if the Mancala is empty, false otherwise
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
        return String.format("MancalaModel[stones=%d, lastStonesState=%d]", stones, lastStonesState);
    }
}
