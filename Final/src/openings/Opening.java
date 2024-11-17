package openings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Main.Canvas;
import Main.Room;

public class Opening extends JPanel {
    //Boolean delete_toggle = false;
    Color room_color;
    String horizontal_or_vertical;
    Boolean opp;
    String type;                            //*** in saving
    Room room;
    Adapter adapter;


    public Opening(Room room) {
        this.room = room;

    }
    public void setType(String type) {
        this.type = type;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    public void add_listener(){
        this.adapter = new Adapter(room,this);
        addMouseListener(adapter);
    }
}

class Adapter extends MouseAdapter {
    Room room;
    Opening opening;
    public Adapter(Room room,Opening opening) {
        System.out.println("Listener Added!");
        if(opening.getHeight()==room.borderwidth){
            opening.setSize(opening.getWidth(),10);
        }
        if(opening.getWidth()==room.borderwidth){
            opening.setSize(10,opening.getHeight());
        }
        opening.repaint();
        this.room = room;
        this.opening = opening;
    }
    public void mousePressed(MouseEvent e) {

        System.out.println("Opening clicked ON!");
        room.remove(opening);
        room.repaint();
        room.openings.remove(opening);


    }
    public void mouseReleased(MouseEvent e) {
        for(Opening opening:room.openings){

            opening.removeMouseListener(opening.adapter);
            opening.setBorder(null);

        }
        room.repaint();
    }
}

