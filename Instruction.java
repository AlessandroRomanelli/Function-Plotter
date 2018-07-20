public abstract class Instruction {

    /**
     * This method executes this instruction,
     * given an operand stack.
     * The instruction takes its arguments (if any) from the stack,
     * and puts its result (if any) onto the stack.
     */
    public abstract void execute(Storage stack);
    
    /**
     * Produce a human-readable String-representation of this instruction.
     */
    public abstract String toString();
    
}
