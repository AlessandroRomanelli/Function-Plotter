/**
 * DSUB takes two numbers from the operand stack, 
 * subtracts them (it subtracts the top of stack from the second-to-top of stack), 
 * and pushes the result back onto the operand stack.
 */
public final class DSUB extends Instruction {

    public void execute(final Storage storage) {
        final double a = storage.getStack().pop();
        final double b = storage.getStack().pop();
        storage.getStack().push(b-a);
    }

    public String toString() {
        return "DSUB";
    }

}
