/**
 * DREM takes two numbers from the operand stack, 
 * computes the remainder of dividing the second by the first, 
 * and pushes the result back onto the operand stack.
 */
public final class DREM extends Instruction {

    public void execute(final Storage storage) {
        final double a = storage.getStack().pop();
        final double b = storage.getStack().pop();
        storage.getStack().push(b%a);
    }

    public String toString() {
        return "DREM";
    }
    
}
