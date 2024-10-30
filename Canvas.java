import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Canvas extends JPanel {
    ArrayList<Room> rooms;
    public Canvas(){
        rooms = new ArrayList<Room>();
        setLayout(null);
        setSize(600,500);
        setBackground(Color.WHITE);
        setVisible(true);

    }


    public void addRoom(java.awt.Color x){
        Room room = new Room(x,this);
        Random rand = new Random();

        int x_coord = Math.floorDiv(rand.nextInt(0,getWidth()-100),10)*10;
        int y_coord = Math.floorDiv(rand.nextInt(0,getHeight()-100),10)*10;
        room.setBounds(x_coord,y_coord,100,50);
        this.add(room);
        // essentially refresh the canvas to show the updated elements
        this.revalidate();
        setComponentZOrder(room,0);
        this.repaint();

        rooms.add(room);
    }

    public static void main(String[]kwargs){
         class tempFrame extends JFrame{
             public tempFrame(){
                 setTitle("Canvas");
                 setSize(1000,500);
                 setLayout((new FlowLayout()));

                 setVisible(true);
                 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // creating canvas object
                 Canvas canvasClass = new Canvas();
                 // Buttons
                 JButton RedRoom = new JButton("Add Red Element");
                 add(RedRoom);
                 JButton BlueRoom = new JButton("Add Blue Element");
                 add(BlueRoom);

                 // Adding canvas to JFrame

                 canvasClass.setPreferredSize(new Dimension(1000,500));
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

        }
        new tempFrame();

    }

}

