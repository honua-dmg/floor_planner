import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Line2D;

public class CustomBorderPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Border with Half Right Border");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // Create JPanel with custom border
            JPanel panel = new JPanel();
            panel.setBorder(new MyCustomBorder());

            panel.setLayout(new BorderLayout());
            panel.add(new JLabel("Panel with custom half right border"), BorderLayout.CENTER);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    // Define a custom border class
    static class MyCustomBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Top border
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(new Line2D.Float(x, y, x + width, y));

            // Left border
            g2d.setColor(Color.GREEN);
            g2d.draw(new Line2D.Float(x, y, x, y + height));

            // Right border (only top half with a different color and thickness)
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2)); // Thinner stroke for top half
            int middleY = y + height / 2;
            g2d.draw(new Line2D.Float(x + width, y, x + width, middleY));  // Top half

            // Bottom half of the right border (different style or color)
            g2d.setColor(Color.ORANGE);
            g2d.setStroke(new BasicStroke(4)); // Thicker stroke for bottom half
            g2d.draw(new Line2D.Float(x + width, middleY, x + width, y + height));  // Bottom half

            // Bottom border
            g2d.setColor(Color.MAGENTA);
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(new Line2D.Float(x, y + height, x + width, y + height));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5); // Border insets (padding around the border)
        }
    }
}
