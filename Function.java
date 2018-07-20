import java.util.ArrayList;

/**
 * The Function is the most important part of the "model" in our function plotter application.
 * The "model" does not know anything about the "GUI".
 * It could exist without "GUI" (e.g., for a command-line interface).
 */
public final class Function {

    private String expressionText;
    private String derivativeText;
    private Program program;
    private Program programDX;
    private int expressionResult;
    private ArrayList<FunctionListener> listeners;

    public Function(final String expressionText) {
        listeners = new ArrayList();
        setExpression(expressionText);
    }

    /**
     * A method that modifies the expression being computed by the function
     * compiles the program for the function, for its derivative and 
     * optimizes both.
     * @param String The new expression that needs to be assigned to the function
     */
    public void setExpression(final String expressionText) {
        this.expressionText = expressionText;
        if (expressionText.equals("")) {
            fireFunctionChanged();
            return;
        }
        final LexicalAnalyzer lexer = new LexicalAnalyzer(expressionText);
        final Parser parser = new Parser(lexer);
        Node parsedExpression = parser.parse();
        Node derivative = parsedExpression.derivative();
        System.out.println(parsedExpression.toString());
        program = new Program();
        programDX = new Program();
        final Node optimisedExpression = parsedExpression.optimize(new Program());
        final Node optimisedDerivative = derivative.optimize(new Program());
        parsedExpression = optimisedExpression;
        derivative = optimisedDerivative;
        parsedExpression.generateCode(program);
        this.expressionText = parsedExpression.toString();
        derivative.generateCode(programDX);
        this.derivativeText = derivative.toString();
        System.out.println(program.toString());
        System.out.println(derivativeText);
        fireFunctionChanged();
    }

    /**
     * Returns the expression currently stored in the function
     * @return String The current expression of the function
     */
    public String getExpression() {
        return expressionText;
    }

    /**
     * Returns the expression currently stored in the function
     * @return String The current expression of the function
     */
    public String getDerivativeExpr() {
        return derivativeText;
    }

    /**
     * A method that takes an argument "x" and return the corresponding "y"
     * of the model.
     * @param argument A double which represents the input of the
     * function
     * @return A double which represents the output of the function 
     * being computed 
     */
    public double compute(final double argument) {
        final VariableSpace variableSpace = new VariableSpace();
        variableSpace.store("x", argument);
        return program.execute(variableSpace);
    }

    /**
     * A method that takes an argument "x" and return the corresponding "y"
     * of the derivative of the computed function.
     * @param argument A double which represent the input of the function
     * @return A double which represents the output of the computed derivative
     */
    public double computeDX(final double argument) {
        final VariableSpace variableSpace = new VariableSpace();
        variableSpace.store("x", argument);
        return programDX.execute(variableSpace);
    }

    /**
     * A method that adds a FunctionListener to the array of active listeners
     * @param li The FunctionListener to be added
     */
    public void addFunctionListener(FunctionListener li) {
        listeners.add(li);
    }

    /**
     * A method that remove a FunctionListener from the array of active listeners
     * @param li The FunctionListener to be removed
     */
    public void removeFunctionListener(FunctionListener li) {
        listeners.remove(li);
    }

    /**
     * A method to fire the functionChanged method on all active listeners
     */
    private void fireFunctionChanged() {
        for (FunctionListener li: listeners) {
            li.functionChanged(this);
        }
    }
    // TODO

}