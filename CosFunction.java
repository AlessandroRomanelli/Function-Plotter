/**
 * An AST node representing a function call to the sine function (cos(a)).
 */
public final class CosFunction extends Node {
    private final Node argument;

    public CosFunction(final Node argument) {
        this.argument = argument;
    }

    public void generateCode(final Program program) {
        argument.generateCode(program);
        program.append(new COS());
    }

    public String toString() {
        return "cos(" + argument.toString() + ")";
    }

    public boolean isConstant() {
        return argument.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new CosFunction(argument.optimize(program));
        }
    }

    public Node derivative() {
        if (isConstant()) {
            return new Literal(0);
        } else {
            return new Multiplication(argument.derivative(), 
                new Negation(new SinFunction(argument)));
        }
    }
}
