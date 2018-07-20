/**
 * TAN takes one number from the operand stack, 
 * computes the tangent of that value 
 * and pushes the result back onto the operand stack.
 */
public final class TAN extends Instruction {

    public void execute(final Storage storage) {
        final double argument = storage.getStack().pop();
        final double result = Math.tan(argument);
        storage.getStack().push(result);
    }

    public String toString() {
        return "TAN";
    }

}