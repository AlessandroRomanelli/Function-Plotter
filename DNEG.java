/**
 * DNEG takes one number from the operand stack, 
 * negates it (it computes -a),
 * and pushes the result back onto the operand stack.
 */
public final class DNEG extends Instruction {

    public void execute(final Storage storage) {
        final double a = storage.getStack().pop();
        storage.getStack().push(-a);
    }

    public String toString() {
        return "DNEG";
    }

}
