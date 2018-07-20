import java.util.ArrayList;

/**
 * A Range represents a range of values (from a given minimum to a given maximum).
 * It is mutable and observable
 * (observable means that one can register a listener to get notified of changes).
 */
public final class Range {

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private ArrayList<RangeListener> listeners;
    private RangeIndicator indicator;
    private RangeHistory records;

    public Range(final double min, final double max) {
        listeners = new ArrayList();
        this.xMin = min;
        this.xMax = max;
        this.yMin = Double.NaN;
        this.yMax = Double.NaN;
        this.indicator = new RangeIndicator();
        this.records = new RangeHistory(new double[] {min,max});
    }

    public void reset() {
        this.xMax = Math.PI;
        this.xMin = -Math.PI;
        this.yMin = Double.NaN;
        this.yMax = Double.NaN;
        records = new RangeHistory(new double[] {xMin, xMax});
        fireRangeChanged();
    }

    public boolean hasNextRange() {
        return records.hasNext();
    }

    public boolean hasPrevRange() {
        return records.hasPrev();
    }

    public void setPrevRange() {
        if (!hasPrevRange()) {
            reset();
            return;
        }
        final double[][] prevRanges = records.prevRanges();
        setRanges(prevRanges);
    }

    public void setNextRange() {
        final double[][] nextRanges = records.nextRanges();
        if (nextRanges != null) {
            setRanges(nextRanges);
        } else {
            reset();
        }

    }

    public void toggleIndicator() {
        indicator.toggleVisibility();
    }

    public double getMinX() {
        return xMin;
    }

    public double getMinY() {
        return yMin;
    }

    public void setRangeX(double min, double max) {
        double[][] prevRanges = records.prevRanges();
        if (prevRanges != null) {
            double[] prev = prevRanges[0];
            if (prev[0] != min && prev[1] != max) {
                records.push(new double[] {min, max}, records.getCurrentY());
                setRangeX(new double[]{min,max});
            }
        } else {
            records.push(new double[] {min, max}, records.getCurrentY());
            setRangeX(new double[]{min,max});
        }

    }

    public void setRangeX(final double[] range) {
        if (range[0] < range[1]) {
            xMin = range[0];
            xMax = range[1];
        } else {
            xMin = range[1];
            xMax = range[0];
        }
        fireRangeChanged();
    }

    public void setRanges(final double[][] ranges) {
        setRangeX(ranges[0]);
        setRangeY(ranges[1]);
    }

    public void setRanges(final double[] rangeX, final double[] rangeY) {
        records.push(rangeX, rangeY);
        setRangeX(rangeX);
        setRangeY(rangeY);
    }

    public void setRangeY(double min, double max) {
        double[][] prevRanges = records.prevRanges();
        double[] prev = prevRanges[1];
        if (prev[0] != min && prev[1] != max) {
            records.push(records.getCurrentX(), new double[] {min, max});
            setRangeY(new double[] {min, max});
        }
    }

    public void setRangeY(final double[] range) {
        if (range[0] < range[1]) {
            yMin = range[0];
            yMax = range[1];
        } else {
            yMin = range[1];
            yMax = range[0];
        }
        fireRangeChanged();
    }

    public void setMinX(final double min) {
        if (min != getMinX()) {
            this.xMin = min;
            records.push(new double[] {min, getMaxX()}, records.getCurrentY());
            fireRangeChanged();
        }
    }

    public void setMinY(final double min) {
        if (min != getMinY()) {
            this.yMin = min;
            records.push(records.getCurrentX(), new double[] {min, getMaxY()});
            fireRangeChanged();
        }
    }

    public double getMaxX() {
        return xMax;
    }

    public double getMaxY() {
        return yMax;
    }

    public void setMaxX(final double max) {
        if (max != getMaxX()) {
            this.xMax = max;
            records.push(new double[] {getMinX(), max}, records.getCurrentY());
            fireRangeChanged();
        }
    }

    public void setMaxY(final double max) {
        if (max != getMaxY()) {
            this.yMax = max;
            records.push(records.getCurrentX(), new double[] {getMinY(), max});
            fireRangeChanged();
        }
    }

    public void modifyRangeX(final double multiplier) {
        final double xMid = (xMin + xMax)/2;
        double delta = xMax - xMid;
        delta *= multiplier;
        setRangeX(new double[] {xMid-delta, xMid+delta});
    }

    public void modifyRanges(final double multiplier) {
        final double xMid = (xMin + xMax)/2;
        double deltaX = xMax - xMid;
        if (!Double.isNaN(yMin) && !Double.isNaN(yMax)) {
            final double yMid = (yMin + yMax)/2;
            double deltaY = yMax - yMid;
            deltaY *= multiplier;
            setRangeY(new double[] {yMid-deltaY, yMid+deltaY});
        }
        deltaX *= multiplier;
        setRangeX(new double[] {xMid-deltaX, xMid+deltaX});
    }

    public double getExtentX() {
        return xMax-xMin;
    }

    public double getExtentY() {
        return yMax-yMin;
    }

    public RangeIndicator getIndicator() {
        return indicator;
    }

    public void addRangeListener(RangeListener li) {
        listeners.add(li);
    }

    public void removeRangeListener(RangeListener li) {
        listeners.remove(li);
    }

    //--- listener management
    private void fireRangeChanged() {
        for (RangeListener li: listeners) {
            li.rangeChanged(this);
        }
    }

    // TODO
}
