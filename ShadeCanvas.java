import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComponent;

/**
 * The PlotCanvas is part of the GUI.
 * It is a custom component, which draws itself
 * (in the paintComponent method)
 * and which provides information on how big it would like to be
 * (via the getPreferredSize method).
 */
public final class ShadeCanvas extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(400, 20);

    private final Function[] functions;
    private final Range range;

    public ShadeCanvas(final Plot plot) {
        this.functions = plot.getFunctions();
        this.range = plot.getRange();

        // register listeners
        for (Function function:functions) {
            if (function != null) {
                function.addFunctionListener(new FunctionListener() {
                        public void functionChanged(Function function) {
                            repaint();
                        }
                    });
            }
        }

        range.addRangeListener(new RangeListener() {
                public void rangeChanged(Range range) {
                    repaint();
                }
            });
        //TODO: register the necessary listeners
    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    public void paintComponent(final Graphics g) {

        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        //TODO paint
        for (int i = 0; i < getWidth(); i++) {
            double result = 0;
            for (Function function : functions) {
                if (!function.getExpression().equals("")) {
                    double partial = function.compute(xViewToModel(i));
                    result += partial;
                }
            }

            if (result > maxY) {
                maxY = result;
            }

            if (result < minY) {
                minY = result;
            }

        }

        for (int i = 0; i < getWidth(); i++) {
            double result = 0;
            for (Function function : functions) {
                if (!function.getExpression().equals("")) {
                    double partial = function.compute(xViewToModel(i));
                    result += partial;
                }
            }
            int rgb = yModelToView(result, minY, maxY);
            g.setColor(new Color(rgb,rgb,rgb));
            g.drawLine(i,0,i,getHeight());
        }

    }

    private double xViewToModel(final int vx) {
        return ((double)vx)/getWidth()*range.getExtentX()+range.getMinX();
    }

    private int yModelToView(final double y, final double minY, final double maxY) {
        return (int)((y-minY)/(maxY-minY)*255);
    }
}