/**
 * DDIV takes two numbers from the operand stack, divides the second by the first, 
 * and pushes the result back onto the operand stack.
 */
public final class DDIV extends Instruction {

    public void execute(final Storage storage) {
        final double a = storage.getStack().pop();
        final double b = storage.getStack().pop();
        try {
            storage.getStack().push(b/a);
        } catch (ArithmeticException e)  {
            System.out.println("How dare you divide by zero, you filthy peasant!");
        }
        
    }

    public String toString() {
        return "DDIV";
    }
    
}
