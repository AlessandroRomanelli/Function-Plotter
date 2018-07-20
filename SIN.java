public class SIN extends Instruction {

    public void execute(final Storage storage) {
        final double argument = storage.getStack().pop();
        final double result = Math.sin(argument);
        storage.getStack().push(result);
    }

    public String toString() {
        return "SIN";
    }

}