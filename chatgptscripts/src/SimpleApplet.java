import javax.swing.JApplet;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class SimpleApplet extends JApplet {
    @Override
    public void init() {
        // Set up the applet's UI
        JLabel label = new JLabel("Hello, JApplet!", JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
