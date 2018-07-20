/**
 * An AST node representing a literal double value.
 */
public final class Literal extends Node {

    private final double value;

    public Literal(final double value) {
        this.value = value;
    }

    public void generateCode(final Program program) {
        program.append(new DCONST(value));
    }

    public String toString() {
        return "" + value;
    }

    public boolean isConstant() {
        return true;
    }

    public Node optimize(final Program program) {
        return this;
    }

    public Node derivative() {
        return new Literal(0);
    }
    
    public boolean isEqual(Literal that) {
        return this.value == that.value;
    }

}
