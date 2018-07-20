import java.util.ArrayList;

/**
 * A Cursor represents the pointer for our function
 */

public final class Cursor {

    private final ArrayList<CursorListener> listeners;
    private double x;

    public Cursor(final double initialX) {
        listeners = new ArrayList<CursorListener>();
        this.x = initialX;
    }

    /**
     * @return the current x of the cursor in respect to the model
     * 
     */
    public double getX() {
        return x;
    }

    /**
     * A method that sets a new x for the pointer
     * @param x A double in respect to the model that the horizontal 
     * component of the cursor
     */
    public void setX(final double x) {
        this.x = x;
        fireCursorChanged();
    }

    //--- listener management
    public void addCursorListener(final CursorListener li) {
        listeners.add(li);
    }

    public void removeCursorListener(final CursorListener li) {
        listeners.remove(li);
    }

    private void fireCursorChanged() {
        for (final CursorListener li : listeners) {
            li.cursorChanged(this);
        }
    }

}
