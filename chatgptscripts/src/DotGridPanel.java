import javax.swing.*;
import java.awt.*;

public class DotGridPanel extends JPanel {

    private final int dotSpacing = 20; // Space between dots

    public DotGridPanel() {
        // Set preferred size for the panel
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting

        // Set the color for the dots
        g.setColor(Color.LIGHT_GRAY);

        // Draw the dot grid
        for (int x = 0; x < getWidth(); x += dotSpacing) {
            for (int y = 0; y < getHeight(); y += dotSpacing) {
                g.fillOval(x - 2, y - 2, 4, 4); // Draw a dot (4x4 pixels)
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dot Grid Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null); // Use null layout

            DotGridPanel dotGridPanel = new DotGridPanel();
            dotGridPanel.setBounds(0, 0, 400, 400); // Set the bounds of the panel

            // Add the panel to the frame
            frame.add(dotGridPanel);
            frame.pack(); // Adjusts the frame size based on the preferred size of the panel
            frame.setVisible(true);
        });
    }
}
