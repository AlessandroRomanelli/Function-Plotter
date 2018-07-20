/**
 * An AST node representing a function call to the sine function (tan(a)).
 */
public final class TanFunction extends Node {

    private final Node argument;

    public TanFunction(final Node argument) {
        this.argument = argument;
    }

    public void generateCode(final Program program) {
        argument.generateCode(program);
        program.append(new TAN());
    }

    public String toString() {
        return "tan(" + argument.toString() + ")";
    }

    public boolean isConstant() {
        return argument.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new TanFunction(argument.optimize(program));
        }
    }

    public Node derivative() {
        return new Division(argument.derivative(), 
            new Power(new CosFunction(argument), new Literal(2)));
    }

}
