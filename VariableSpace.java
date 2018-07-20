import java.util.HashMap;

/**
 * A space that stores the variables during the execution of the bytecode.
 */
public final class VariableSpace {

    private HashMap<String,Double> variables;

    public VariableSpace() {
        variables = new HashMap<String,Double>();
        variables.put("pi", Math.PI);
        variables.put("e", Math.E);
    }

    public void store(final String name, final double value) {
        variables.put(name, value);   
    }

    public double load(final String name) {
        return variables.get(name);
    }

}
