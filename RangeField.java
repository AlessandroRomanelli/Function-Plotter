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
 * The RangeField can store either the minimums or maximum of the X and Y
 * ranges. 
 */

public final class RangeField extends JPanel {
    private static final int CHARS_IN_TEXTFIELD = 6;
    private final JTextField fieldX;
    private final JTextField fieldY;
    private final boolean isLeft;
    private Range range;
    public RangeField(Range range, boolean isLeft) {
        this.range = range;
        this.isLeft = isLeft;
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = null;
        if (isLeft) {
            label = new JLabel(" Lower Bounds");
        } else {
            label = new JLabel(" Upper Bounds");
        }
        add(label, BorderLayout.NORTH);
        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BorderLayout());
        JLabel x = new JLabel(" x:");
        fieldX = new JTextField(CHARS_IN_TEXTFIELD);
        xPanel.add(x, BorderLayout.WEST);
        xPanel.add(fieldX, BorderLayout.EAST);

        add(xPanel, BorderLayout.CENTER);

        JPanel yPanel = new JPanel();
        yPanel.setLayout(new BorderLayout());
        JLabel y = new JLabel(" y:");
        fieldY = new JTextField(CHARS_IN_TEXTFIELD);
        yPanel.add(y, BorderLayout.WEST);
        yPanel.add(fieldY, BorderLayout.EAST);

        add(yPanel, BorderLayout.SOUTH);

        if (isLeft) {
            fieldX.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        // GUI changed: update model
                        // we allow users to enter an expression in the min field!
                        //range.setMinX(evaluate(fieldX.getText()));
                        range.setRangeX(evaluate(fieldX.getText()), range.getMaxX());
                    }
                });
            fieldY.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        final String text = fieldY.getText().trim();
                        try {
                            Double.parseDouble(text);
                            range.setMinY(evaluate(text));
                            range.setMaxY(range.getMaxY());
                        } catch (NumberFormatException exception) {
                            range.setMinY(Double.NaN);
                            range.setMaxY(range.getMaxY());
                            fieldY.setText("Auto");
                        }
                    }
                });
        } else {
            fieldX.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        //range.setMaxX(evaluate(fieldX.getText()));
                        range.setRangeX(range.getMinX(), evaluate(fieldX.getText()));
                    }
                });
            fieldY.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        final String text = fieldY.getText().trim();
                        try {
                            Double.parseDouble(text);
                            range.setMinY(range.getMinY());
                            range.setMaxY(evaluate(text));
                            //range.setRangeY(range.getMinY(), evaluate(text));
                        } catch (NumberFormatException exception) {
                            range.setMinY(range.getMinY());
                            range.setMaxY(Double.NaN);
                            fieldY.setText("Auto");
                        }
                    }
                });
        }

        if (isLeft) {
            range.addRangeListener(new RangeListener() {
                    public void rangeChanged(Range range) {
                        fieldX.setText(""+Math.floor(range.getMinX()*1000)/1000);
                        if (Double.isNaN(range.getMinY())) {
                            fieldY.setText(" Auto");
                        } else {
                            fieldY.setText(""+Math.floor(range.getMinY()*1000)/1000);
                        }
                    }
                });
        } else {
            range.addRangeListener(new RangeListener() {
                    public void rangeChanged(Range range) {
                        fieldX.setText(""+Math.floor(range.getMaxX()*1000)/1000);
                        if (Double.isNaN(range.getMaxY())) {
                            fieldY.setText(" Auto");
                        } else {
                            fieldY.setText(""+Math.floor(range.getMaxY()*1000)/1000);
                        }
                    }
                });
        }

        updateView();
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

    private void updateView() {
        if (isLeft) {
            fieldX.setText(""+Math.floor(range.getMinX()*1000)/1000);
            if (Double.isNaN(range.getMinY())) {
                fieldY.setText(" Auto");
            } else {
                //TODO Convert to Y
                fieldY.setText(""+Math.floor(range.getMinX()*1000)/1000);
            }
        } else {
            fieldX.setText(""+Math.floor(range.getMaxX()*1000)/1000);
            if (Double.isNaN(range.getMaxY())) {
                fieldY.setText(" Auto");
            } else {
                //TODO Convert to Y
                fieldY.setText(""+Math.floor(range.getMaxX()*1000)/1000);
            }
        }

    }
}
