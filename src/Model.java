import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract base class representing a data model in MVC.
 *
 * @author Jeremy Asuncion
 */
public abstract class Model
{
    private List<ChangeListener> listeners;

    /**
     * Constructs a new model object.
     */
    public Model()
    {
        listeners = new ArrayList<>();
    }

    /**
     * Adds a change listener to the model.
     *
     * @param listener A change listener
     * @see ChangeListener
     */
    public void addChangeListener(ChangeListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes all change listeners from the model.
     */
    public void clearChangeListeners()
    {
        listeners.clear();
    }

    /**
     * Invokes all change listeners with the current object
     * instance as the change event.
     */
    protected void invokeListeners()
    {
        ChangeEvent event = new ChangeEvent(this);
        for(ChangeListener listener : listeners)
        {
            listener.stateChanged(event);
        }
    }

    /**
     * Commits the changes to the data model.
     * The state is then moved to the current state.
     * When the data is committed, the change listeners
     * should be invoked by calling {@code invokeListeners()}
     *
     * @see #invokeListeners()
     */
    public abstract void commitChange();

    /**
     * Undos the most recent change to the data model.
     * Used by the Mancala game since undos are allowed.
     */
    public abstract void undoChange();
}
