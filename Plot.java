/**
 * The Plot is the "model" of this application.
 * It is an immutable class that
 * points to the two mutable and observable parts of the "model":
 * 1) a Function, and
 * 2) a Range (along the x-axis, over which the function should be plotted).
 */
public final class Plot {

    private final Function[] functions;
    private final Range range;
    private final Cursor cursor;
    
    public Plot(final Function[] functions, final Range range, 
    final Cursor cursor) {
        this.functions = functions;
        this.range = range;
        this.cursor = cursor;
    }

    /** This method is a getter for other classes to access this private field
    * @return Returns an array of Functions
    */
    public Function[] getFunctions() {
        return functions;
    }

    /** This method is a getter for other classes to access this private field 
    * @return Returns the Range object 
    */
    public Range getRange() {
        return range;
    }

    /** This method is a getter for other classes to access this private field 
    * @return Returns the Cursor object
    */
    public Cursor getCursor() {
        return cursor;
    }

}
