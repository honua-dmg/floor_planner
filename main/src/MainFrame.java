package main.src;

import rooms.src.*;
import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    Canvas canvasClass;
    Context_manager manager;
    public MainFrame(){
        setTitle("Canvas");
        setSize(1100,1000);
        setLayout((new FlowLayout()));

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // creating canvas object
        canvasClass = new Canvas();
        manager = new Context_manager(canvasClass);
        add(manager);
        manager.setPreferredSize(new Dimension(300,1000));


        // Adding canvas to JFrame

        canvasClass.setPreferredSize(new Dimension(1000,1000));
        add(canvasClass);




        pack();
    }
    public static void main(String[] args) {
        new MainFrame();
    }
}

class Context_manager extends JPanel {
    public Context_manager(Canvas canvasClass) {
        //setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(300,1000));
        // Buttons
        JButton Ktichen = new JButton(      "      Add Kitchen      ");
        add(Ktichen);
        JButton bedroom = new JButton(      "      Add Bedroom      ");
        add(bedroom);
        JButton bathroom = new JButton(     "      Add Bathroom     ");
        add(bathroom);
        JButton DrawingRoom = new JButton(  "    Add Drawing room   ");
        add(DrawingRoom);
        JButton outside = new JButton(     "       Add Outside      ");
        add(outside);
        JButton Dining = new JButton(     "      Add Dining room    ");
        add(Dining);

        // Button elements
        Ktichen.addActionListener(e -> {
            canvasClass.addRoom(new Kitchen(canvasClass,canvasClass.gridsize,canvasClass.borderwidth));
            System.out.println("  Room added");
        });
        bedroom.addActionListener(e -> {
            canvasClass.addRoom(new Bedroom(canvasClass,canvasClass.gridsize,canvasClass.borderwidth));
            System.out.println("  Room added");

        });
        bathroom.addActionListener(e -> {
            canvasClass.addRoom(new Bathroom(canvasClass,canvasClass.gridsize,canvasClass.borderwidth));
            System.out.println("  Room added");

        });
        DrawingRoom.addActionListener(e -> {
            canvasClass.addRoom(new Drawing(canvasClass,canvasClass.gridsize,canvasClass.borderwidth));
            System.out.println("  Room added");

        });
        outside.addActionListener(e -> {
            canvasClass.addRoom(new Outside(canvasClass,canvasClass.gridsize,canvasClass.borderwidth));
            System.out.println("  Room added");

        });
        Dining.addActionListener(e -> {
            canvasClass.addRoom(new Dining(canvasClass,canvasClass.gridsize,canvasClass.borderwidth));
            System.out.println("  Room added");

        });


    }
}