/**
 * An AST node representing negation (-a).
 */
public final class Negation extends Node {

    private final Node child;

    public Negation(final Node child) {
        this.child = child;
    }

    public void generateCode(final Program program) {
        child.generateCode(program);
        program.append(new DNEG());
    }

    public String toString() {
        return "-(" + child.toString() + ")";
    }

    public boolean isConstant() {
        return child.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new Negation(child.optimize(program));
        }
    }

    public Node derivative() {
        return new Negation(child.derivative());
    }

}
