package openings;

import javax.swing.*;
import java.awt.*;

public class RoomWindow extends JPanel {
    Color room_color;
    String horizontal_or_vertical;
    Boolean opp;

    public RoomWindow(String horizontal_or_vertical, Color color, Boolean opp) {
        this.room_color = color;
        this.horizontal_or_vertical = horizontal_or_vertical;
        this.opp = opp;
    }

    public void setRoom_color(Color room_color) {
        this.room_color = room_color;
    }

    public void toggle_opp() {
        opp = !opp;
        repaint();
    }

    public void toggle_hori_verti() {
        if (horizontal_or_vertical.equals("horizontal")) {
            horizontal_or_vertical = "vertical";
        } else {
            horizontal_or_vertical = "horizontal";
        }
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call super to ensure proper painting

        // Cast to Graphics2D for advanced control
        Graphics2D g2d = (Graphics2D) g;

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();


        // Define the width of each alternating color band
        int stripeSize = 5; // Width of each vertical stripe

        // Alternate colors (you can add more colors or patterns as needed)
        Color[] colors = {Color.BLACK, room_color};

        // Paint alternating vertical stripes
        if (horizontal_or_vertical.equals("horizontal")) {
            int y;
            if (opp) {
                y = height - 10;
            } else {
                y = 0;
            }
            for (int x = 0; x < width; x += stripeSize) {
                g2d.setColor(colors[(x / stripeSize) % 2]); // Alternate colors for each stripe
                g2d.fillRect(x, y, stripeSize, 10); // Fill the stripe with the selected color
            }
        } else {
            int x;
            if (opp) {
                x = width - 10;
            } else {
                x = 0;
            }
            for (int y = 0; y < height; y += stripeSize) {
                g2d.setColor(colors[(y / stripeSize) % 2]); // Alternate colors for each stripe
                g2d.fillRect(x, y, 10, stripeSize); // Fill the stripe with the selected color
            }
        }

    }
}