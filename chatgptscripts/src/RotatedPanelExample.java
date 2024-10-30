import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class RotatedPanelExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotated JPanel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        RotatedPanel rotatedPanel = new RotatedPanel();
        frame.add(rotatedPanel);

        frame.setVisible(true);
    }
}

class RotatedPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast Graphics to Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        // Save the current transformation
        AffineTransform originalTransform = g2d.getTransform();

        // Rotate the graphics context
        // Rotate around the center of the panel
        int width = getWidth();
        int height = getHeight();
        g2d.rotate(Math.toRadians(90), width/4, height/4);

        // Draw something (a rectangle, for example)
        g2d.setColor(Color.BLUE);
        g2d.fillRect(-50, -25, 100, 50); // Adjust coordinates as necessary

        // Restore the original transformation
        g2d.setTransform(originalTransform);
    }
}
