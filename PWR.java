/**
 * PWR takes two numbers from the operand stack, 
 * computes elevates to the power of the top of stack the number undearneath it 
 * and pushes the result back onto the operand stack.
 */
public final class PWR extends Instruction {

    public void execute(final Storage storage) {
        final double b = storage.getStack().pop();
        final double a = storage.getStack().pop();
        storage.getStack().push(Math.pow(a,b));
    }

    public String toString() {
        return "PWR";
    }

}
