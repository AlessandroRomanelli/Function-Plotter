import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The RangeHistoryPanel contains all the GUI elements to use the RangeHistory,
 * in order to revert or forward the state of the history.
 */
public final class RangeHistoryPanel extends JPanel {
    private final Range range;
    private final JButton prevButton;
    private final JButton nextButton;
    public RangeHistoryPanel(Range range) {
        this.range = range;
        setLayout(new BorderLayout());
        prevButton = new JButton("<");
        prevButton.setEnabled(false);
        nextButton = new JButton(">");
        nextButton.setEnabled(false);

        add(prevButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);

        prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent ev) {
                    range.setPrevRange();
                }
            });

        nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent ev) {
                    range.setNextRange();
                }
            });

        range.addRangeListener(new RangeListener() {
                public void rangeChanged(Range range) {
                    if (range.hasPrevRange()) {
                        prevButton.setEnabled(true);
                    } else {
                        prevButton.setEnabled(false);
                    }
                    if (range.hasNextRange()) {
                        nextButton.setEnabled(true);
                    } else {
                        nextButton.setEnabled(false);
                    }
                }
            });
    }
}