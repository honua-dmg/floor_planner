import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RightClickPopupExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Right Click Popup Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel canvas = new JPanel();
        canvas.setBackground(Color.LIGHT_GRAY);
        frame.add(canvas);

        // Create a JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Option 1");
        JMenuItem menuItem2 = new JMenuItem("Option 2");

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);

        // Add mouse listener to detect right-clicks
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Show the popup menu at the mouse location
                    popupMenu.show(canvas, e.getX(), e.getY());
                }
            }
        });

        frame.setVisible(true);
    }
}
