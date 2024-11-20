package Main;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;



public class Canvas extends JPanel {
    int[] room_coords = {-1, -1}; // this is used incase we need to add a room wrt another room
    boolean furniture_canvas = false;
    boolean wrt_room = false;
    public ArrayList<Room> rooms;
    public JFrame frame;
    int standard_room_width ;
    int standard_room_height ;

    public int gridsize=10;
    public int borderwidth=4;

    public Canvas() {
        rooms = new ArrayList<>();
        setLayout(null);
        setSize(1200, 1000);
        setBackground(Color.WHITE);
        setVisible(true);

        standard_room_width=100*2;
        standard_room_height=50*2;


    }

    public void set_color(Color x){
        setBackground(x);
    }


    public void addRoom(Room room) {

        //Random rand = new Random();
        int[] coords;
        //int x_coord = Math.floorDiv(rand.nextInt(0,getWidth()-100),10)*10;
        //int y_coord = Math.floorDiv(rand.nextInt(0,getHeight()-100),10)*10;
        if (wrt_room) { // if x coordinate of room coords is -1 it means that no specific value
            // has been assigned.
            coords = room_coords;
            System.out.println(coords[0] + " " + coords[1] + " canvasclass room coords added");
            wrt_room = false;
        } else {
            coords = rowmajorcoords();
            System.out.println("added normally");
        }


        int x_coord = coords[0];
        int y_coord = coords[1];

        //System.out.println("\n\n"+x_coord + " " + y_coord + "  room added here\n\n");
        room.setBounds(x_coord, y_coord, standard_room_width, standard_room_height);
        System.out.println(room.getWidth() + " " + room.getHeight());

        //setComponentZOrder(this, 0);
        if(room.getX()+room.getWidth()>getWidth()) {
            showDialog(this.frame,"No Space, move a room please!");
            return;
        }

        if(!room.room_overlap()){
            this.add(room);
            rooms.add(room);
        }else{
            showDialog(this.frame,"No Space, move a room please!");
        }




        // essentially refresh the canvas to show the updated elements
        this.revalidate();
        //setComponentZOrder(room,0);
        this.repaint();

        //rooms.add(room);
    }

    public static void showDialog(JFrame parent, String text) {
        // Create the dialog
        JDialog dialog = new JDialog(parent, "ERROR!", true); // true for modal
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(2, 1));
        GridBagConstraints grid = new GridBagConstraints();


        // Add components to the dialog
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(300, 50));
        grid.gridx = 0;
        grid.gridy = 0;
        grid.weightx = 1;
        grid.weighty = 1;
        //grid.fill = GridBagConstraints.BOTH;

        dialog.add(label, grid);


        // OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dialog.dispose();
        });
        grid.gridx = 0;
        grid.gridy = 1;
        //grid.weightx=1;
        //grid.weighty=1;
        //grid.fill = GridBagConstraints.BOTH;
        dialog.add(okButton, grid);

        // Set the dialog location relative to the parent
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true); // Show the dialog
    }

    public int[] rowmajorcoords() {

        for (int i = 10; i < 910; i += 10) {
            boolean present = false;
            for (Room room : rooms) {
                if (room.getY() != 0) {
                    // we only care about the top most rooms
                    continue;
                }
                if (room.getX() == i) {
                    i += room.getWidth(); // 90 and not a 100 because 10 will be appended anyway after the next iteration
                    present = true;
                    break;
                }
            }
            Room test = new Room(Color.BLACK, this,gridsize,borderwidth);
            test.setBounds(i, 0, 100, 50);
            if (test.room_overlap()) {
                present = true;
            }
            if (!present) {
                return new int[]{i, 0};
            }
        }
        return new int[]{-1, 0};
    }
    //}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting

        // Set the color for the dots
        if(furniture_canvas){
            g.setColor(new Color(70,70,70));
        }else{
        g.setColor(Color.LIGHT_GRAY);}

        // Draw the dot grid
        int x;
        int spacing;
        if(!furniture_canvas) {
            x = -borderwidth;


            for (; x < getWidth(); x += gridsize * 2) {
                for (int y = 0; y < getHeight(); y += gridsize * 2) {
                    g.fillOval(x - 1, y - 1, 2, 2); // Draw a dot (4x4 pixels)
                }
            }
        }


        //setBorder(BorderFactory.createLineBorder(Color.BLACK,borderwidth));
    }


}


