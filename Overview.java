

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
/*
public class Overview extends JFrame{
    ArrayList<Room> rooms = new ArrayList<Room>();
    Canvas canvas;
    Controls controls;
    public Overview(){
        // Canvas and control panel setup
        setLayout(new GridBagLayout());
        canvas = new Canvas();
        controls = new Controls(canvas);
        GridBagConstraints grid = new GridBagConstraints();
            grid.gridx=0;
            grid.weightx=1;
            grid.weighty=1;
            grid.fill = GridBagConstraints.BOTH;
        this.add(controls,grid);
            grid.gridx=0;
            grid.weightx=1;
            grid.weighty=3;
            grid.fill = GridBagConstraints.BOTH;
        this.add(canvas,grid);

        Room room = new Room(Color.RED,canvas);
        canvas.add(room);
        Room rom = new Room(Color.RED,canvas);
        canvas.add(rom);
        setSize(900,800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

class Controls extends JPanel{

    JButton new_element = new JButton("ADD New Element");

    public Controls(Canvas canvas){
        new_element.addActionListener(e->{
            System.out.println("New element to be added");
            Room room = new Room(Color.RED,canvas);
            canvas.add(room);
        });
        add(new_element); // adds create new element button to controls




        setLayout(new FlowLayout());
        //setSize(600,100);
        setBackground(Color.GRAY);
        setVisible(true);

    }
}


*/

