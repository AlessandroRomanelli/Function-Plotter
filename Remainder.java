/**
 * An AST node representing the remainder (a%b).
 */
public final class Remainder extends Node {

    private final Node left;
    private final Node right;

    public Remainder(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public void generateCode(final Program program) {
        left.generateCode(program);
        right.generateCode(program);
        program.append(new DREM());
    }

    public String toString() {
        return "(" + left.toString() + "%" + right.toString() + ")";
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new Remainder(left.optimize(program), right.optimize(program));
        }
    }

    public Node derivative() {
        return new Subtraction(left.derivative(), 
            new Multiplication(right.derivative(), 
                new Division(left,right)));
    }
}
