/**
 * An AST node representing a function call to the power function a^b.
 */

public final class Power extends Node {
    private final Node left;
    private final Node right;

    public Power(final Node left, final Node right) {
        this.left = left;
        this.right = right;
    }

    public void generateCode(final Program program) {
        left.generateCode(program);
        right.generateCode(program);
        program.append(new PWR());
    }

    public String toString() {
        return left.toString() + "^" + right.toString();
    }

    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return new Power(left, right.optimize(program));
        }
    }

    public Node derivative() {
        return new Multiplication(
            new Power(left, new Subtraction(right, new Literal(1))),
            new Addition(
                new Multiplication(right, left.derivative()),
                new Multiplication(left, 
                    new Multiplication(new LnFunction(left), right.derivative()))));
    }

}