import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The PlotCanvas is part of the GUI.
 * It is a custom component, which draws itself
 * (in the paintComponent method)
 * and which provides information on how big it would like to be
 * (via the getPreferredSize method).
 */
public final class PlotCanvas extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(400,300);

    private final Function[] functions;
    private final Range range;
    private final RangeIndicator indicator;
    private final Cursor cursor;
    private boolean[] visibleFPs;
    private boolean[] visibleDXs;
    private boolean[] visibleINTs;

    private double tempMinY;
    private double tempMaxY;

    public PlotCanvas(final Plot plot) {
        this.functions = plot.getFunctions();
        this.range = plot.getRange();
        this.cursor = plot.getCursor();
        this.visibleFPs = new boolean[functions.length];
        this.visibleDXs = new boolean[functions.length];
        this.visibleINTs = new boolean[functions.length];
        this.indicator = plot.getRange().getIndicator();

        tempMinY = Double.NaN;
        tempMaxY = Double.NaN;

        // register listeners
        //TODO: register FunctionListener
        for (Function function: functions) {
            function.addFunctionListener(new FunctionListener() {
                    public void functionChanged(Function function) {
                        repaint();
                    }
                });
        }

        range.addRangeListener(new RangeListener() {
                public void rangeChanged(Range range) {
                    repaint();
                }
            });
        //TODO: register RangeListener
        cursor.addCursorListener(new CursorListener() {
                public void cursorChanged(final Cursor cursor) {
                    repaint();
                }
            });

        addMouseListener(new MouseAdapter() {
                public void mousePressed(final MouseEvent ev) {
                    cursor.setX(xViewToModel(ev.getX()));
                }
            });
        addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(final MouseEvent ev) {
                    cursor.setX(xViewToModel(ev.getX()));
                    System.out.println(functions[0].compute(xViewToModel(ev.getX())));
                }
            });

        addMouseListener(new MouseAdapter() {
                public void mousePressed(final MouseEvent ev) {
                    indicator.setStartPoint(ev.getX(), ev.getY());
                    indicator.setEndPoint(ev.getX(), ev.getY());
                    repaint();
                }
            });

        addMouseListener(new MouseAdapter() {
                public void mouseReleased(final MouseEvent ev) {
                    if (indicator.checkVisible()) {
                        final int[] indStart = indicator.getStart();
                        final int[] indEnd = indicator.getEnd();
                        final double endX;
                        if (indEnd[0] < getWidth() && indEnd[0] > 0) {
                            endX = xViewToModel(indEnd[0]);
                        } else if (indEnd[0] > 0) {
                            endX = xViewToModel(getWidth()-1);
                        } else {
                            endX = xViewToModel(1);
                        }
                        range.setRanges(new double[] {xViewToModel(indStart[0]), endX}, 
                            new double[] {(yViewToModel(indStart[1])), yViewToModel(indEnd[1])});

                        indicator.reset();    
                        repaint();
                    }
                }
            });

        addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(final MouseEvent ev) {
                    indicator.setEndPoint(ev.getX(), ev.getY());
                    repaint();
                }
            });
    }

    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    public void paintComponent(final Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);
        // find min and max y
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        //TODO: find the minimum and maximum y value in the given Range of x values
        for (Function function: functions) {
            for (int i = 0; i < w; i++) {
                if (!function.getExpression().equals("")) {
                    double result = function.compute(xViewToModel(i))*1.1;
                    if (result > maxY) {
                        maxY = result;
                    }

                    if (result < minY) {
                        minY = result;
                    }
                }
            }
        }
        tempMaxY = maxY;
        tempMinY = minY;

        if (!Double.isNaN(range.getMaxY())) {
            maxY = range.getMaxY();
        }

        if (!Double.isNaN(range.getMinY())) {
            minY = range.getMinY();
        }

        // plot x axis (at y=0)
        g2d.setColor(Color.GRAY);
        final int vy0 = yModelToView(0, minY, maxY);
        final int vx0 = xModelToView(0);
        g2d.drawLine(vx0,0,vx0,h);
        g2d.drawLine(0, vy0, w, vy0);

        // plot curve

        //TODO: plot f(x) in the given Range of x values
        for (int i = 0; i < functions.length; i++) {
            Color color = null;
            switch (i) {
                case 0: color = new Color(198, 23, 23); break;
                case 1: color = new Color(23, 198, 38); break;
                case 2: color = new Color(23, 81, 198); break;
                default: color = new Color(0,0,0,50); break;
            }
            g2d.setColor(color);
            int prec = 0;
            if (!functions[i].getExpression().equals("")) {
                for (int j=1; j < w; j++) {
                    double computedY = functions[i].compute(xViewToModel(j));
                    double computedPrec = functions[i].compute(xViewToModel(j-1));
                    int value = yModelToView(computedY, minY, maxY);
                    int valuePrec = yModelToView(computedPrec, minY, maxY);
                    if (!Double.isNaN(computedY)) {
                        g2d.drawLine(j,value,j,value);
                        if (!Double.isNaN(computedPrec)) {
                            g2d.drawLine(j-1,valuePrec,j,value);
                        }
                    }
                }  
            }

            if (visibleDXs[i]) {
                drawDerivatives(g2d, minY, maxY, functions[i], color);
            }

            drawFlexPoints(g2d, minY, maxY, functions[i], color);

            if (visibleINTs[i]) {
                drawIntegral(g2d, minY, maxY, functions[i], color);
            }
        }

        if (indicator.checkVisible()) {
            drawInterval(g2d, indicator.getStart(), indicator.getEnd());
        } else {
            final int cursorX = xModelToView(cursor.getX());
            drawCursorX(g2d, cursorX);
            int counter = 0;
            for (Function function: functions) {
                drawCursorY(g2d, function, minY, maxY, cursorX, counter);                
                counter++;
            }
        }
    }

    private void drawCursorX(Graphics2D g, int cursorX) {
        g.setStroke(new BasicStroke(
                1f, 
                BasicStroke.CAP_ROUND, 
                BasicStroke.JOIN_ROUND, 
                1f, 
                new float[] {2f}, 
                0f));

        // draw x cursor
        g.setColor(new Color(0,0,0,50));
        g.drawLine(cursorX, 0, cursorX, getHeight());
        g.setColor(new Color(0,0,0,200));
        if (cursorX > getWidth()*3/4) {
            g.drawString("x = "+Math.floor(cursor.getX()*1000)/1000, cursorX-75, getHeight()-5);
        } else {
            g.drawString("x = "+Math.floor(cursor.getX()*1000)/1000, cursorX+4, getHeight()-5);
        }
    }

    private void drawCursorY(Graphics2D g, Function function, double minY, double maxY, int cursorX, int counter) {
        Color color = null;
        switch (counter) {
            case 0: color = new Color(198, 23, 23,50); break;
            case 1: color = new Color(23, 198, 38, 50); break;
            case 2: color = new Color(23, 81, 198,50); break;
            default: color = new Color(0,0,0,50); break;
        }

        if (!function.getExpression().equals("")) {
            g.setColor(color);
            // draw y cursor
            final double cursorModelY = function.compute(cursor.getX());
            final int cursorY = yModelToView(cursorModelY, minY, maxY);
            g.drawLine(0, cursorY, getWidth(), cursorY);

            g.setColor(changeTransparency(color, 200));

            if (cursorX > getWidth()*3/4) {
                g.drawString("y"+(counter+1)+" = "+
                    Math.floor(cursorModelY*1000)/1000, cursorX-75, cursorY-5);
            } else {
                g.drawString("y"+(counter+1)+" = "+
                    Math.floor(cursorModelY*1000)/1000, cursorX+4, cursorY-5);
            }
            int index = -1;
            for (int i = 0; i < functions.length; i++) {
                if (functions[i] == function) {
                    index = i;
                }
            }
            g.setColor(color);
            if (visibleDXs[index]) {
                final double cursorModelYDX = function.computeDX(cursor.getX());
                final int cursorYDX = yModelToView(cursorModelYDX, minY, maxY);
                g.drawLine(0, cursorYDX, getWidth(), cursorYDX);
                g.setColor(changeTransparency(color, 200));
                if (cursorX > getWidth()*3/4) {
                    g.drawString("y'"+(counter+1)+" = "+
                        Math.floor(cursorModelYDX*1000)/1000, cursorX-75, cursorYDX-5);
                } else {
                    g.drawString("y'"+(counter+1)+" = "+
                        Math.floor(cursorModelYDX*1000)/1000, cursorX+4, cursorYDX-5);
                }
            }

        }
        counter++;
    }

    private void drawDerivatives(Graphics2D g, double minY, double maxY, Function function, Color color) {
        int w = getWidth();
        int precDX = 0;
        if (!function.getExpression().equals("")) {
            for (int i=0; i < w; i++) {
                double computedY = function.computeDX(xViewToModel(i));
                double computedPrec = function.computeDX(xViewToModel(precDX));
                int value = yModelToView(computedY, minY, maxY);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
                if (!Double.isNaN(computedY)) {
                    g.drawLine(i,value,i,value);
                }
                if (!Double.isNaN(computedY) && !Double.isNaN(computedPrec) && Math.abs(value-precDX) < getHeight()) {
                    g.drawLine(i-1,precDX,i,value);
                }
                precDX = value;
            }
        }
        g.setColor(color);
    }

    private void drawFlexPoints(Graphics2D g, double minY, double maxY, Function function, Color color) {
        int w = getWidth();
        if (!function.getExpression().equals("") && !function.getDerivativeExpr().equals("0.0") && !function.getDerivativeExpr().equals("-0.0")) {
            double precDX = function.computeDX(xViewToModel(0));
            for (int i=1; i < w; i++) {
                double dx = function.computeDX(xViewToModel(i));
                if ((dx >= 0 && precDX <= 0) || (precDX >= 0 && dx <= 0)) {
                    g.setColor(Color.BLACK);
                    int value = yModelToView(function.compute(xViewToModel(i)), minY, maxY);
                    Ellipse2D.Double circle = new Ellipse2D.Double(i-2, value-2, 4, 4);
                    g.fill(circle);
                }
                g.setColor(color);
                precDX = dx;
            }
        }
    }

    private void drawIntegral(Graphics2D g, double minY, double maxY, Function function, Color color) {
        int w = getWidth();
        if (!function.getExpression().equals("")) {
            int[] dxPosX = new int[w+3];
            int[] dxPosY = new int[w+3];
            dxPosX[0] = 0;
            dxPosY[0] = yModelToView(0, minY, maxY);
            for (int i=0; i < w; i++) {
                int value = yModelToView(function.compute(xViewToModel(i)), minY, maxY);
                dxPosX[i+1] = i;
                dxPosY[i+1] = value; 
            }  

            dxPosX[w+1] = w;
            dxPosY[w+1] = yModelToView(0, minY, maxY);
            dxPosX[w+2] = 0;
            dxPosY[w+2] = yModelToView(0, minY, maxY);
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
            g.fillPolygon(dxPosX, dxPosY, dxPosX.length);
        }
    }

    public void toggleFunctionDX(int i) {
        visibleDXs[i] = !visibleDXs[i];
        repaint();
    }

    public void toggleFunctionINT(int i) {
        visibleINTs[i] = !visibleINTs[i];
        repaint();
    }

    private void drawInterval(Graphics2D g, int[] start, int[] end) {
        int h = getHeight();
        g.setColor(new Color(0,0,0,60));
        g.setColor(new Color(0,0,0,30));
        g.fillPolygon(new int[]{start[0],start[0],end[0],end[0]}, new int[]{start[1],end[1],end[1],start[1]}, 4);
        g.setColor(new Color(0,0,0,60));
        g.drawPolygon(new int[]{start[0],start[0],end[0],end[0]}, new int[]{start[1],end[1],end[1],start[1]}, 4);
    }

    private double xViewToModel(final int vx) {
        return ((double)vx)/getWidth()*range.getExtentX()+range.getMinX();
    }

    private int xModelToView(final double x) {
        return (int)((x-range.getMinX())/range.getExtentX()*getWidth());
    }

    private Color changeTransparency(Color color, int opacity) {
        return new Color(color.getRed(), 
            color.getGreen(), 
            color.getBlue(), 
            opacity);
    }

    private double yViewToModel(final int vy) {
        if (Double.isNaN(range.getMinY()) || Double.isNaN(range.getMaxY())) {
            double extentY = tempMaxY - tempMinY;
            return (getHeight()- (double)vy)/getHeight()*extentY+tempMinY;
        } else {
            return (getHeight() - (double)vy)/getHeight()*range.getExtentY()+range.getMinY();
        }
    }

    private int yModelToView(final double y, final double minY, final double maxY) {
        return (int)(getHeight()-1-(y-minY)/(maxY-minY)*getHeight());
    }
}