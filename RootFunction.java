/**
 * An AST node representing a function call to the square root function sqrt(a).
 */
public final class RootFunction extends Node { 
    private final Node argument;

    public RootFunction(final Node argument) {
        this.argument = argument;
    }

    public void generateCode(final Program program) {
        argument.generateCode(program);
        program.append(new SQRT());
    }

    public String toString() {
        return "sqrt(" + argument.toString() + ")";
    }

    public boolean isConstant() {
        return argument.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new RootFunction(argument.optimize(program));
        }
    }

    public Node derivative() {
        return new Division(argument.derivative(), 
            new Multiplication(new Literal(2), 
                new RootFunction(argument)));
    }

}