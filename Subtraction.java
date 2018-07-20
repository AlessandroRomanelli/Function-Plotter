/**
 * An AST node representing subtraction (a-b).
 */
public final class Subtraction extends Node {

    private final Node left;
    private final Node right;

    public Subtraction(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public void generateCode(final Program program) {
        left.generateCode(program);
        right.generateCode(program);
        program.append(new DSUB());
    }

    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
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
        } else if ((bothLiterals && left.isEqual(right)) || (leftIsZero && rightIsZero) || (bothVariables && left.isEqual(right))) {
            return new Literal(0);
        } else if (leftIsZero) {
            return right.optimize(program);
        } else if (rightIsZero) {
            return left.optimize(program);
        } else {
            return new Subtraction(left.optimize(program), right.optimize(program));
        }
    }

    public Node derivative() {
        return new Subtraction(left.derivative(), right.derivative());
    }

}
