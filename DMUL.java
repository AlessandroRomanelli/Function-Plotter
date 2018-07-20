/**
 * DMUL takes two numbers from the operand stack, multiplies them, 
 * and pushes the result back onto the operand stack.
 */
public final class DMUL extends Instruction {

    public void execute(final Storage storage) {
        storage.getStack().push( storage.getStack().pop() * storage.getStack().pop());
    }

    public String toString() {
        return "DMUL";
    }
    
}
