/**
 * An AST node representing addition (a+b).
 */
public final class Addition extends Node {

    private final Node left;
    private final Node right;

    public Addition(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public void generateCode(final Program program) {
        left.generateCode(program);
        right.generateCode(program);
        program.append(new DADD());
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public Node optimize(final Program program) {
        final boolean leftIsZero = left.toString() == "0.0";
        final boolean rightIsZero = right.toString() == "0.0";
        final boolean bothLiterals = left instanceof Literal && right instanceof Literal;
        final boolean bothVariables = left instanceof Variable && right instanceof Variable;
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else if (bothVariables && left.isEqual(right)) {
            return new Multiplication(left.optimize(program), new Literal(2)).optimize(program);
        } else if (leftIsZero) {
            return right.optimize(program).optimize(program);
        } else if (rightIsZero) {
            return left.optimize(program).optimize(program);
        } else if (left.toString().equals(right.toString())) {
            return new Multiplication(left.optimize(program), new Literal(2)).optimize(program);
        } else {
            return new Addition(left.optimize(program), right.optimize(program));
        }
    }

    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    public Node derivative() {
        return new Addition(left.derivative(), right.derivative());
    }

}
