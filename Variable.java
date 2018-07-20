/**
 * An AST node representing a variable.
 */
public final class Variable extends Node {

    private final String name;

    public Variable(final String name) {
        this.name = name;
    }             

    public void generateCode(final Program program) {
        program.append(new DLOAD(name));
    }

    public boolean isConstant() {
        return name.equals("pi") || name.equals("e");
    }

    public String toString() {
        return name;
    }

    public Node optimize(final Program program) {
        if (isConstant()) {
            generateCode(program);
            return new Literal(program.execute(new VariableSpace()));
        } else {
            return this;
        } 
    }
    
    public boolean isEqual(final Variable that) {
        return this.name.equals(that.name);
    }

    public Node derivative() {
        return new Literal(1);
    }

}
