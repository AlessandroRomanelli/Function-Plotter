/**
 * The ExpressionPanel will call integralToggled or derivativeToggled 
 * on all registered listeners, when one of the two buttons is clicked.
 */
public interface ExpressionPanelListener
{    
    public abstract void integralToggled(int index);
    
    public abstract void derivativeToggled(int index);
}
