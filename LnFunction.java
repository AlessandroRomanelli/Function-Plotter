/**
 * An AST node representing a function call to the logarithmic function (ln(a)).
 */
public final class LnFunction extends Node {
    private final Node argument;

    public LnFunction(final Node argument) {
        this.argument = argument;
    }

    public void generateCode(final Program program) {
        argument.generateCode(program);
        program.append(new LN());
    }

    public String toString() {
        return "ln(" + argument.toString() + ")";
    }

    public boolean isConstant() {
        return argument.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new LnFunction(argument.optimize(program));
        }
    }

    public Node derivative() {
        return new Division(argument.derivative(), argument);
    }

}
