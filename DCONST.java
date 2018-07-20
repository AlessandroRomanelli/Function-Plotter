/**
 * DCONST pushes an int constant onto the operand stack.
 */
public final class DCONST extends Instruction {

    private final double value;
    
    
    public DCONST(final double value){
        this.value = value;
    }
    
    public void execute(final Storage storage) {
        storage.getStack().push(value);
    }

    public String toString() {
        return "DCONST " + value;
    }
    
}
