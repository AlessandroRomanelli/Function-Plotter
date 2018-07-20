import java.util.ArrayList;


/**
 * A Program is a list of Instructions
 * (Instructions are similar to IJVM or Java bytecode instructions)
 */
public final class Program {

    private static final int MAX_STACK_SIZE = 100; // assume operand stack never grows larger
    
    private final ArrayList<Instruction> instructions;
    
    
    public Program() {
        this.instructions = new ArrayList<Instruction>();
    }
    
    public void append(final Instruction instruction) {
        instructions.add(instruction);
    }
    
    /**
     * This method is a small "Java interpreter"
     * that executes the bytecode instructions in this program,
     * one by one, and then returns the result.
     */
    public double execute(final VariableSpace variables) {
        // we start with an empty stack and a set of initialized variables
        final Storage storage = new Storage(MAX_STACK_SIZE, variables);
                
        for (final Instruction instruction : instructions) {
            // we execute one instruction after the other
            instruction.execute(storage);
        }
        // at the end of the program, the result (of computing the expression) is on the stack
        return Math.floor(storage.getStack().pop()*10000)/10000;
    }
    
    /**
     * Generate a String representation of this Program,
     * one line for each instruction in the program.
     */
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (final Instruction instruction : instructions) {
            sb.append("  "+instruction.toString()+"\n");
        }
        return sb.toString();
    }
    
}
