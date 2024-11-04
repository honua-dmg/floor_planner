import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    Canvas canvasClass;
    public MainFrame(){
        setTitle("Canvas");
        setSize(1100,1000);
        setLayout((new FlowLayout()));

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // creating canvas object
        canvasClass = new Canvas(this);
        // Buttons
        JButton RedRoom = new JButton("Add Red Element");
        add(RedRoom);
        JButton BlueRoom = new JButton("Add Blue Element");
        add(BlueRoom);

        // Adding canvas to JFrame

        canvasClass.setPreferredSize(new Dimension(1000,1000));
        add(canvasClass);

        // Button elements
        RedRoom.addActionListener(e -> {
            canvasClass.addRoom(Color.RED);
            System.out.println("  Room added");
        });

        BlueRoom.addActionListener(e -> {
            canvasClass.addRoom(Color.BLUE);
            System.out.println("  Room added");

        });

        pack();
    }
    public static void main(String[] args) {
        new MainFrame();
    }

}