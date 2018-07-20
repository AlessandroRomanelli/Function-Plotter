import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * The SettingsPanel contains all the options to change the state of the
 * currently plotted functions: X/Y ranges, Resize Mode, Reset and History
 */

public final class SettingsPanel extends JPanel
{
    
    private Range range;
    
    public SettingsPanel(final Range range) {
        this.range = range;
        setLayout(new BorderLayout());
        
        RangePanel rangePanel = new RangePanel(range);
        add(rangePanel, BorderLayout.NORTH);

        JToggleButton buttonToggle = new JToggleButton("RESIZE");
        JButton buttonReset = new JButton("RESET");

        RangeHistoryPanel historyPanel = new RangeHistoryPanel(range);

        JPanel interactivePanel = new JPanel();
        interactivePanel.setLayout(new BorderLayout());
        interactivePanel.add(buttonToggle, BorderLayout.EAST);
        interactivePanel.add(buttonReset, BorderLayout.WEST);
        interactivePanel.add(historyPanel, BorderLayout.CENTER);

        add(interactivePanel, BorderLayout.CENTER);

        buttonToggle.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent ev) {
                    range.toggleIndicator();
                }
            });

        buttonReset.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent ev) {
                    range.getIndicator().hide();
                    range.reset();
                    buttonToggle.setSelected(false);
                }
            });
    }
}
