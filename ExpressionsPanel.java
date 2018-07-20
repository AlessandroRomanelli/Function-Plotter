import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

/**
 * Generates a panel that contains the ExpressionFields for the functions.
 */

public final class ExpressionsPanel extends JPanel
{
    private final Function[] functions;
    private final ArrayList<ExpressionPanelListener> listeners;

    public ExpressionsPanel(final Function[] functions) {
        this.functions = functions;
        this.listeners = new ArrayList();
        //final HashMap<JTextField,Function> fieldToFnMap = new HashMap<JTextField,Function>();
        final JPanel[] panels = new JPanel[functions.length];
        final JTextField[] exprFields = new JTextField[functions.length];
        final JToggleButton[] dxToggles = new JToggleButton[functions.length];
        final JToggleButton[] intToggles = new JToggleButton[functions.length];
        setLayout(new GridBagLayout());

        Dimension toggleDimension = new Dimension(50,30);

        for (int i = 0; i < functions.length; i++) {
            final JPanel p = new JPanel();
            p.setLayout(new BorderLayout());

            final JTextField exprField = new JTextField(functions[i].getExpression());
            final JLabel label = new JLabel(" f"+(i+1)+": ");
            final JToggleButton dxToggle = new JToggleButton("dx");
            dxToggle.setPreferredSize(toggleDimension);
            final JToggleButton intToggle = new JToggleButton("int");
            intToggle.setPreferredSize(toggleDimension);

            panels[i] = p;
            exprFields[i] = exprField;
            dxToggles[i] = dxToggle;
            intToggles[i] = intToggle;

            if (exprFields[i].getText().equals("")) {
                dxToggles[i].setEnabled(false);
                intToggles[i].setEnabled(false);
            }  

            final JPanel toggles = new JPanel();
            toggles.setLayout(new BorderLayout());
            toggles.add(dxToggle, BorderLayout.WEST);
            toggles.add(intToggle, BorderLayout.EAST);
            toggles.setPreferredSize(new Dimension(90, 30));

            p.add(toggles, BorderLayout.EAST);
            p.add(exprField, BorderLayout.CENTER);  
            p.add(label, BorderLayout.WEST);

            addComponent(panels[i], 0, i, 1, 1, GridBagConstraints.HORIZONTAL);
        }

        showNextField(exprFields, panels);

        for (int i = 0; i < functions.length; i++) {
            final int index = i;
            exprFields[index].addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        // called when user presses ENTER key in expressionField
                        final String text = exprFields[index].getText();
                        Function function = functions[index];
                        function.setExpression(text);
                        exprFields[index].setText(function.getExpression());
                        if (exprFields[index].getText().equals("")) {
                            dxToggles[index].setEnabled(false);
                            dxToggles[index].setSelected(false);
                            intToggles[index].setEnabled(false);
                            intToggles[index].setSelected(false);
                        } else {
                            dxToggles[index].setEnabled(true);
                            intToggles[index].setEnabled(true);
                        }
                        showNextField(exprFields, panels);
                    }
                });

            dxToggles[index].addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        fireDerivativeToggled(index);
                    }
                });

            intToggles[index].addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent ev) {
                        fireIntegralToggled(index);
                    }
                });
        }
    }

    public void addComponent(final JComponent component, final int x, final int y, final double width, final double height, final int type) {
        addComponent(component, x, y, width, height, 1, 1, type);
    }

    public void addComponent(final JComponent component, final int x, final int y, final double width, final double height, final int cellX, 
    final int cellY, final int type) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = width;
        gbc.weighty = height;
        gbc.gridwidth = cellX;
        gbc.gridheight = cellY;
        gbc.fill = type;
        add(component, gbc);
    }

    public void addExpressionListener(final ExpressionPanelListener li) {
        listeners.add(li);
    }

    public void removeExpressionListener(final ExpressionPanelListener li) {
        listeners.remove(li);
    }

    public void showNextField(final JTextField[] expressions, final JPanel[] panels) {
        for (int i = 0; i < functions.length; i++) {
            if (expressions[i].getText().equals("")) {
                panels[i].setVisible(false);
            }
        }
        
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].getText().equals("")) {
                panels[i].setVisible(true);
                break;
            }
        }
        
        for (int i = 0; i < panels.length; i ++) {
            addComponent(panels[i], 0, i, 1, 1, GridBagConstraints.HORIZONTAL);
        }
    }

    private void fireDerivativeToggled(final int index) {
        for (ExpressionPanelListener li: listeners) {
            li.derivativeToggled(index);
        }
    }

    private void fireIntegralToggled(final int index) {
        for (ExpressionPanelListener li: listeners) {
            li.integralToggled(index);
        }
    }
}
