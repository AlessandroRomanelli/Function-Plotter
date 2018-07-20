/**
 * LN takes one number from the operand stack, 
 * computes the natural logarithm of said number 
 * and pushes the result back onto the operand stack.
 */
public final class LN extends Instruction {
    
    public void execute(final Storage storage) {
        final double argument = storage.getStack().pop();
        final double result = Math.log(argument);
        storage.getStack().push(result);
    }

    public String toString() {
        return "LN";
    }

}