import java.util.Hashtable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The RangePanel is a part of the "GUI".
 * It shows the minimum and maximum value visible along the x-axis
 * in two text fields, and the user can update those values by
 * entering them into the text field and pressing ENTER.
 */
public final class RangePanel extends JPanel {

    private static final int CHARS_IN_TEXTFIELD = 6;

    private final Range range;
    private final JSlider slider;

    public RangePanel(final Range range) {
        this.range = range;

        setLayout(new BorderLayout());
        RangeField minField = new RangeField(range, true);
        RangeField maxField = new RangeField(range, false);
        add(minField, BorderLayout.WEST);
        add(maxField, BorderLayout.EAST);
        slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        slider.setMajorTickSpacing(100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( -100 ), new JLabel("-") );
        labelTable.put( new Integer( 0 ), new JLabel("Zoom") );
        labelTable.put( new Integer( 100 ), new JLabel("+") );
        slider.setLabelTable( labelTable );
        add(slider, BorderLayout.CENTER);

        // register listeners
        //TODO: register a RangeListener, call updateView whenever Range changes

        slider.addChangeListener(new ChangeListener() {
                double prev = 0;
                public void stateChanged(ChangeEvent e) {
                    double value = slider.getValue();
                    if (slider.getValueIsAdjusting()) {
                        if ((value < 0 && value < prev) || (value > 0 && value > prev)) {
                            final double multiplier = 1-value/1000;
                            range.modifyRanges(multiplier);
                        }
                    } else {
                        range.setRangeX(range.getMinX(), range.getMaxX());
                        slider.setValue(0);
                        slider.updateUI();
                    }
                    prev = value;
                }
            });

    }

    private double evaluate(final String text) {
        final LexicalAnalyzer lexer = new LexicalAnalyzer(text);
        final Parser parser = new Parser(lexer);
        final Node parsedExpression = parser.parse();
        System.out.println(parsedExpression.toString());
        final Program program = new Program();
        parsedExpression.generateCode(program);
        System.out.println(program.toString());
        final VariableSpace variableSpace = new VariableSpace();
        return program.execute(variableSpace);
    }

}
