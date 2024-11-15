//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Window extends JPanel {
    Color room_color;
    String horizontal_or_vertical;
    Boolean opp;

    public Window(String horizontal_or_vertical, Color color, Boolean opp) {
        this.room_color = color;
        this.horizontal_or_vertical = horizontal_or_vertical;
        this.opp = opp;
    }

    public void setRoom_color(Color room_color) {
        this.room_color = room_color;
    }

    public void toggle_opp() {
        this.opp = !this.opp;
        this.repaint();
    }

    public void toggle_hori_verti() {
        if (this.horizontal_or_vertical.equals("horizontal")) {
            this.horizontal_or_vertical = "vertical";
        } else {
            this.horizontal_or_vertical = "horizontal";
        }

        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        int width = this.getWidth();
        int height = this.getHeight();
        int stripeSize = 5;
        Color[] colors = new Color[]{Color.BLACK, this.room_color};
        int y;
        int x;
        if (this.horizontal_or_vertical.equals("horizontal")) {
            if (this.opp) {
                y = height - 10;
            } else {
                y = 0;
            }

            for(x = 0; x < width; x += stripeSize) {
                g2d.setColor(colors[x / stripeSize % 2]);
                g2d.fillRect(x, y, stripeSize, 20);
            }
        } else {
            if (this.opp) {
                y = width - 10;
            } else {
                y = 0;
            }

            for(x = 0; x < height; x += stripeSize) {
                g2d.setColor(colors[x / stripeSize % 2]);
                g2d.fillRect(y, x, 20, stripeSize);
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean opp = false;
            JFrame frame = new JFrame("Vertically Alternating Colors Panel");
            frame.setLayout(new FlowLayout());
            frame.setDefaultCloseOperation(3);
            frame.setSize(400, 300);
            JButton button = new JButton(" Alternating side");
            JButton button2 = new JButton("switch hori/verti");
            Window panel = new Window("vertical", Color.PINK, opp);
            panel.setPreferredSize(new Dimension(400, 300));
            frame.add(panel);
            button.addActionListener((e) -> {
                panel.toggle_opp();
            });
            button2.addActionListener((e) -> {
                panel.toggle_hori_verti();
            });
            frame.add(button);
            frame.add(button2);
            frame.setVisible(true);
        });
    }
}
