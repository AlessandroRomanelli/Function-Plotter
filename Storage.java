/**
 * A structure that conveniently contains the stack and the varSpace
 */
public final class Storage {

    private OperandStack stack;
    private VariableSpace variables;

    public Storage(final int MAX_STACK_SIZE, VariableSpace variables) {
       stack = new OperandStack(MAX_STACK_SIZE);
       this.variables  = variables;
    }
    
    public OperandStack getStack() {
        return stack;
    }
    
    public VariableSpace getVariables() {
        return variables;
    }
}
