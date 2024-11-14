/*
import javax.swing.*;
import java.awt.*;

public class AlternatingColorsPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call super to ensure proper painting

        // Cast to Graphics2D for advanced control
        Graphics2D g2d = (Graphics2D) g;

        // Get the width and height of the panel
        int width = getWidth();
        int height = getHeight();

        // Define the number of stripes or alternating sections
        int stripeHeight = 2; // Height of each alternating section

        // Alternate colors
        Color[] colors = {Color.RED, Color.BLUE};

        // Paint alternating stripes
        for (int y = 0; y < height; y += stripeHeight) {
            g2d.setColor(colors[(y / stripeHeight) % 2]); // Choose color based on stripe index
            g2d.fillRect(0, y, width, stripeHeight); // Fill the stripe with the selected color
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Alternating Colors Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // Create and add the custom JPanel
            JPanel panel = new AlternatingColorsPanel();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
*/
import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {
    Color room_color;
    String horizontal_or_vertical;
    Boolean opp;
    public Window(String horizontal_or_vertical,Color color,Boolean opp){
        this.room_color = color;
        this.horizontal_or_vertical = horizontal_or_vertical;
        this.opp = opp;
    }
    public void setRoom_color(Color room_color){
        this.room_color = room_color;
    }
    public void toggle_opp(){
        opp= !opp;
        repaint();
    }
    public void toggle_hori_verti(){
        if (horizontal_or_vertical.equals("horizontal")){
        horizontal_or_vertical="vertical";}
        else{
            horizontal_or_vertical="horizontal";
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
        if(horizontal_or_vertical.equals("horizontal")){
            int y;
            if(opp){
                 y = height-10;
            }else{
                 y = 0;
            }
            for (int x = 0; x < width; x += stripeSize) {
                g2d.setColor(colors[(x / stripeSize) % 2]); // Alternate colors for each stripe
                g2d.fillRect(x, y, stripeSize, 10); // Fill the stripe with the selected color
            }
        }else{
            int x;
            if(opp){
                x = width-10;
            }else{
                x = 0;
            }
            for (int y = 0; y < height; y += stripeSize) {
                g2d.setColor(colors[(y / stripeSize) % 2]); // Alternate colors for each stripe
                g2d.fillRect(x, y, 10, stripeSize); // Fill the stripe with the selected color
            }
        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            boolean opp = false;
            JFrame frame = new JFrame("Vertically Alternating Colors Panel");
            frame.setLayout(new FlowLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            JButton button = new JButton(" Alternating side");
            JButton button2 = new JButton("switch hori/verti");


            // Create and add the custom JPanel
            Window panel = new Window("vertical",Color.PINK,opp);
            panel.setPreferredSize(new Dimension(400, 300));
            frame.add(panel);
            button.addActionListener(e -> {
                panel.toggle_opp();

            });
            button2.addActionListener(e -> {
                panel.toggle_hori_verti();
            });

            frame.add(button);
            frame.add(button2);
            frame.setVisible(true);
        });
    }

}
