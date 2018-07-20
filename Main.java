import java.util.ArrayList;
import java.awt.EventQueue;

/**
 * The FunctionPlotter program implements a function plotter that can be used
 * render the graph of a function in the bi-dimensional plane.
 * 
 * @author Alessandro Romanelli
 * @author Alessandra Vicini
 * @version 0.5
 * @since 2018-06-01
 * 
*/
public final class Main {

    public static void main(final String[] args) {
        final Plot plot = new Plot(
            new Function[] {new Function("sin(x)"), new Function(""), new Function(""), new Function("")}, 
            new Range(-Math.PI, Math.PI), 
            new Cursor(0));
        
        // Almost all calls to AWT/Swing should come from Event Dispach Thread.
        // The code below causes the Event Dispatch Thread to call the run function,
        // which creates the GUI.
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Create the GUI, passing in the model
                // (GUI knows model, model does not know GUI)
                final PlotterFrame frame = new PlotterFrame(plot);
                // making the frame visible causes the event dispatching 
                // (handling of mouse and keyboard events) to start
                frame.setVisible(true);
            }
        });
    }
    
}
