/**
 * An AST node representing multiplication (a*b).
 */
public final class Multiplication extends Node {

    private final Node left;
    private final Node right;

    public Multiplication(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public void generateCode(final Program program) {
        left.generateCode(program);
        right.generateCode(program);
        program.append(new DMUL());
    }

    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public Node optimize(final Program program) {
        final boolean leftIsZero = left.toString() == "0.0";
        final boolean rightIsZero = right.toString() == "0.0";
        final boolean bothVariables = left instanceof Variable && right instanceof Variable;
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else if (leftIsZero || rightIsZero) {
            return new Literal(0);
        } else if (left.toString().equals(right.toString())) {
            return new Power(left.optimize(program), new Literal(2)).optimize(program);
        } else {
            return new Multiplication(left.optimize(program), right.optimize(program));
        }
    }

    public Node derivative() {
        return new Addition(new Multiplication(right, left.derivative()),
            new Multiplication(left, right.derivative()));
    }

}
