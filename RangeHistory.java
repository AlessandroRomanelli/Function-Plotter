/**
 * The RangeHistory keeps track of the changes to both horizontal and vertical
 * ranges, it uses two stacks that are in sync to be able to return the
 * previously set range or go back to the latest.
 */
public final class RangeHistory
{
    private double[][] XRanges;
    private double[][] YRanges;
    private int TOS;

    public RangeHistory(double[] initialRange) {
        XRanges = new double[1][];
        XRanges[0] = initialRange;
        YRanges = new double[1][];
        YRanges[0] = new double[] {Double.NaN, Double.NaN};
        TOS = 0;
    }

    /**
     * This method pushes an horizontal and vertical range on both stacks
     * @param XCoordinates The horizontal range that needs to be pushed
     * @param YCoordinates The vertical range that needs to be pushed
     */
    public void push(double[] XCoordinates, double[] YCoordinates) {
        TOS++;
        double[][] newXRange = new double[TOS+1][];
        double[][] newYRange = new double[TOS+1][];
        for (int i = 0; i < TOS; i++) {
            newXRange[i] = XRanges[i];
            newYRange[i] = YRanges[i];
        }
        newXRange[TOS] = XCoordinates;
        newYRange[TOS] = YCoordinates;
        XRanges = newXRange;
        YRanges = newYRange;
    }

    /**
     * Checks whether the stack has a next state
     * (If the TOS is NOT pointing to the end of the array)
     */
    public boolean hasNext() {
        return TOS+1 < XRanges.length;
    }

    /**
     * Checks whether the stack has a prev state
     * (If the TOS is NOT pointing to the start of the array)
     */
    public boolean hasPrev() {
        return TOS > 0;
    }

    /**
     * This method returns a horizontal and vertical ranges that are the next
     * ones in respect to the current ones.
     * @return An array made up by two distinct arrays, the X and Y ranges.
     */
    public double[][] nextRanges() {
        if (TOS+1 < XRanges.length) {
            TOS++;
        }
        return getCurrentRanges();
    }

    /**
     * This method returns a horizontal and vertical ranges that are the 
     * previous ones in respect to the current ones.
     * @return An array made up by two distinct arrays, the X and Y ranges.
     */
    public double[][] prevRanges() {
        if (TOS > 0) {
            TOS--;
        }
        return getCurrentRanges();
    }

    /**
     * This method returns the current horizontal range
     * @return The current X range
     */
    public double[] getCurrentX() {
        return XRanges[TOS];
    }

    
    /**
     * This method returns the current vertical range
     * @return The current Y range
     */
    public double[] getCurrentY() {
        return YRanges[TOS];
    }

    
    /**
     * This method returns the current ranges
     * @return The current X and Y ranges
     */
    public double[][] getCurrentRanges() {
        return new double[][] {getCurrentX(), getCurrentY()};
    }

}
