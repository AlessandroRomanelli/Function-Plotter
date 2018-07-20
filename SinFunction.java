/**
 * An AST node representing a function call to the sine function (sin(a)).
 */
public final class SinFunction extends Node {

    private final Node argument;

    public SinFunction(final Node argument) {
        this.argument = argument;
    }

    public void generateCode(final Program program) {
        argument.generateCode(program);
        program.append(new SIN());
    }

    public String toString() {
        return "sin(" + argument.toString() + ")";
    }

    public boolean isConstant() {
        return argument.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new SinFunction(argument.optimize(program));
        }
    }

    public Node derivative() {
        if (isConstant()) {
            return new Literal(0);
        } else {
            return new Multiplication(argument.derivative(), 
                new CosFunction(argument));
        }
    }

}
