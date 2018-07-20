/**
 * The "model" doesn't know the specific "GUI" classes.
 * But it still wants to notify the "GUI" (or text UI, or ...)
 * whenever the "model" changes, so that the "GUI" (or text UI, or ...)
 * can react to those changes (e.g., by repainting itself).
 * 
 * To enable this, we have this FunctionListener interface.
 * The "model" (i.e., the Function) will call functionChanged() on all registered listeners,
 * without knowing the specific subtype of FunctionListener
 * (e.g., the "model" does not know about the PlotCanvas class).
 */
public interface FunctionListener {

    public abstract void functionChanged(Function function);
    
}
