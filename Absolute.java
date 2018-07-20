/**
 * An AST node representing a function call to the absolute value 
 * function abs(a).
 */
public final class Absolute extends Node {
    private final Node argument;

    public Absolute(final Node argument) {
        this.argument = argument;
    }

    public void generateCode(final Program program) {
        argument.generateCode(program);
        program.append(new ABS());
    }

    public String toString() {
        return "abs(" + argument.toString() + ")";
    }

    public boolean isConstant() {
        return argument.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new Absolute(argument.optimize(program));
        }
    }

    public Node derivative() {
        return new Division(
            new Multiplication(argument, argument.derivative()), 
            new Absolute(argument));
    }

}