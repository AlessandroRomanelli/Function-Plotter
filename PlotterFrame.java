import java.util.HashMap;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

/**
 * The main frame of the Function Plotter application.
 * The "GUI".
 * The "GUI" knows the "model", it depends on the "model",
 * and it cannot exist without the "model".
 */
public final class PlotterFrame extends JFrame {

    public PlotterFrame(final Plot plot) {

        setTitle("Function Plotter");
        setLayout(new BorderLayout());

        Range range = plot.getRange();
        Function[] functions = plot.getFunctions();

        ExpressionsPanel expressions = new ExpressionsPanel(functions);
        add(expressions, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        PlotCanvas plottedCanvas = new PlotCanvas(plot);
        panel.add(plottedCanvas, BorderLayout.CENTER);
        panel.add(new ShadeCanvas(plot), BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);

        SettingsPanel settings = new SettingsPanel(range);

        add(settings, BorderLayout.SOUTH);

        expressions.addExpressionListener(new ExpressionPanelListener() {
                public void derivativeToggled(int index) {
                    plottedCanvas.toggleFunctionDX(index);
                }
                
                public void integralToggled(int index) {
                    plottedCanvas.toggleFunctionINT(index);
                }
            });

        pack();
    }

}