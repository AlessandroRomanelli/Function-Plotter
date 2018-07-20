/**
 * An AST node representing division (a/b).
 */
public final class Division extends Node {

    private final Node left;
    private final Node right;

    public Division(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public void generateCode(final Program program) {
        left.generateCode(program);
        right.generateCode(program);
        program.append(new DDIV());
    }

    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new Division(left.optimize(program), right.optimize(program));
        }
    }

    public Node derivative() {
        return new Division(new Subtraction(
                new Multiplication(right, left.derivative()),
                new Multiplication(left, right.derivative())),
            new Power(right, new Literal(2)));
    }

}
