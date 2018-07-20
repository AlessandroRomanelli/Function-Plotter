/**
 * SQRT takes a number from the operand stack, 
 * computes the square root of that number 
 * and pushes the result back onto the operand stack.
 */
public final class SQRT extends Instruction {
    public void execute(final Storage storage) {
        final double argument = storage.getStack().pop();
        final double result = Math.sqrt(argument);
        storage.getStack().push(result);
    }

    public String toString() {
        return "SQRT";
    }

}