/**
 * DLOAD loads the value of a double variable and pushes it onto the operand stack.
 */
public final class DLOAD extends Instruction {

    private final String name;
    
    
    public DLOAD (final String name){
        this.name = name;
    }
    
    public void execute(final Storage storage) {
        final double value = storage.getVariables().load(name);
        storage.getStack().push( value );
    }

    public String toString() {
        return "DLOAD " + name;
    }
    
}
