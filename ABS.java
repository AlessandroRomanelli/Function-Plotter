/**
 * ABS takes one number from the operand stack, 
 * computes the absolute value 
 * and pushes the result back onto the operand stack.
 */
public final class ABS extends Instruction {
    public void execute(final Storage storage) {
        final double argument = storage.getStack().pop();
        final double result = Math.abs(argument);
        storage.getStack().push(result);
    }

    public String toString() {
        return "ABS";
    }

}