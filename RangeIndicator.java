
/**
 * A RangeIndicator stores the position of the points that define the 
 * range indication when using the RESIZE mode.
 */
public class RangeIndicator
{
    private boolean isVisible;
    private int viewX1;
    private int viewY1;
    private int viewX2;
    private int viewY2;

    /**
     * Sets the start point of the resize region
     * @param x The horizontal component of the start point
     * @param y The vertical component of the start point
     */
    public void setStartPoint(int x, int y) {
        viewX1 = x;
        viewY1 = y;
    }

    /**
     * Sets the end point of the resize region
     * @param x The horizontal component of the end point
     * @param y The vertical component of the end point
     */
    public void setEndPoint(int x, int y) {
        viewX2 = x;
        viewY2 = y;
    }

    /**
     * Resets the range indicator area to be 0
     */
    public void reset() {
        viewX1 = 0;
        viewX2 = 0;
        viewY1 = 0;
        viewY2 = 0;
    }

    /**
     * Returns the position of the start point.
     * @return An array of 2 components, specifing the position of the start point
     */
    public int[] getStart() {
        return new int[] {viewX1, viewY1};
    }

    /**
     * Returns the position of the end point.
     * @return An array of 2 components, specifing the position of the end point
     */
    public int[] getEnd() {
        return new int[] {viewX2, viewY2};
    }

    /**
     * Toggles the state of the indicator, whether it should be visible or not.
     */
    public void toggleVisibility() {
        isVisible = !isVisible;
    }

    /**
     * A method that hides the indicator
     */
    public void hide() {
        isVisible = false;
    }

    /**
     * A method that displays the indicator
     */
    public void show() {
        isVisible = true;
    }

    
    /**
     * A method that returns whether the indicator is currently visible.
     * @return A boolean that represents whether the indicator is visible.
     */
    public boolean checkVisible() {
        return isVisible;
    }

}
