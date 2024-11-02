import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

class Canvas extends JPanel {
    int [] room_coords = {-1,-1}; // this is used incase we need to add a room wrt another room
    ArrayList<Room> rooms;
    JFrame frame;
    public Canvas(JFrame frame) {
        rooms = new ArrayList<Room>();
        setLayout(null);
        setSize(600,500);
        setBackground(Color.WHITE);
        setVisible(true);
        this.frame = frame;
    }


    public void addRoom(java.awt.Color x,int[] coords){
        Room room = new Room(x,this);
        Random rand = new Random();

        //int x_coord = Math.floorDiv(rand.nextInt(0,getWidth()-100),10)*10;
        //int y_coord = Math.floorDiv(rand.nextInt(0,getHeight()-100),10)*10;

        if (coords[0]!=-1){
            int x_coord = coords[0];
            int y_coord = coords[1];

            room.setBounds(x_coord,y_coord,100,50);


            if(!room.room_overlap()){
                this.add(room);
            }else{
                showDialog(this.frame,"Overlap, move a room please!");
            }
        }
        else{
            showDialog(this.frame,"Move/delete a room to add another!");
        }


        // essentially refresh the canvas to show the updated elements
        this.revalidate();
        //setComponentZOrder(room,0);
        this.repaint();

        rooms.add(room);
    }
    public static void showDialog(JFrame parent,String text) {
        // Create the dialog
        JDialog dialog = new JDialog(parent, "ERROR!", true); // true for modal
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(2,1));
        GridBagConstraints grid = new GridBagConstraints();


        // Add components to the dialog
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(300, 50));
        grid.gridx=0;
        grid.gridy=0;
        grid.weightx=1;
        grid.weighty=1;
        //grid.fill = GridBagConstraints.BOTH;

        dialog.add(label, grid);


        // OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Close the dialog
            }
        });
        grid.gridx=0;
        grid.gridy=1;
        //grid.weightx=1;
        //grid.weighty=1;
        //grid.fill = GridBagConstraints.BOTH;
        dialog.add(okButton,grid);

        // Set the dialog location relative to the parent
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true); // Show the dialog
    }

    public int[] rowmajorcoords(){

    for (int i =10;i<910;i+=10){
        boolean present = false;
        for(Room room:rooms){
            if(room.getY()!=0){
                // we only care about the top most rooms
                continue;
            }
            if(room.getX()==i){
                i+=room.getWidth(); // 90 and not a 100 because 10 will be appended anyway after the next iteration
                present = true;
                break;
            }
        }
        Room test = new Room(Color.BLACK,this);
        test.setBounds(i,0,100,50);
        if(test.room_overlap()){
            present = true;
        }
        if(!present){
            return new int[]{i,0};
        }
    }
    return new int[]{-1,0};
    }

    public static void main(String[]kwargs){

        new tempFrame();

    }

}

class tempFrame extends JFrame{
    Canvas canvasClass;
    public tempFrame(){
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
            int [] coords=canvasClass.rowmajorcoords();
            if(canvasClass.room_coords[0]!=-1){ // if x coordinate of room coords is -1 it means that no specific value
                                                // has been assigned.
                coords = canvasClass.rowmajorcoords();
                canvasClass.room_coords[0] = -1;
            }
            canvasClass.addRoom(Color.RED,coords);
            System.out.println("  Room added");
        });
        BlueRoom.addActionListener(e -> {
            int [] coords=canvasClass.rowmajorcoords();
            if(canvasClass.room_coords[0]!=-1){ // if x coordinate of room coords is -1 it means that no specific value
                // has been assigned.
                coords = canvasClass.rowmajorcoords();
                canvasClass.room_coords[0] = -1;
            }
            canvasClass.addRoom(Color.BLUE,coords);
            System.out.println("  Room added");

        });



        pack();
    }

}