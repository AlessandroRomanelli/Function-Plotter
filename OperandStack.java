/**
 * A stack with a fixed size that is used during the execution of the bytecode.
 */
public final class OperandStack {

    private final double[] slots;
    private int top;
    
    public OperandStack(final int capacity) {
        slots = new double[capacity];
        top = -1;
    }
    
    public void push(double value) {
        top++;
        slots[top] = value;
        
    }
    
    public double pop() {        
        return slots[top--];
    }
    
    
}
