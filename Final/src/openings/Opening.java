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

        this.room = room;
        this.opening = opening;
        System.out.println("Listener Added!");
        room.setComponentZOrder(opening,0);
        opening.setBorder(BorderFactory.createLineBorder(Color.PINK, 4));
        if(opening.getHeight()==room.borderwidth){
            opening.setSize(opening.getWidth(),10);
        }
        if(opening.getWidth()==room.borderwidth){
            opening.setSize(10,opening.getHeight());
        }
        room.repaint();
    }
    public void mousePressed(MouseEvent e) {

        System.out.println("Opening clicked ON!");
        room.remove(opening);
        room.repaint();
        room.openings.remove(opening);
        System.out.println("Opening clicked Released!");
        for(Opening opening:room.openings){
            System.out.println(opening.type);
            if(opening.getHeight()==10){
                opening.setSize(opening.getWidth(),room.borderwidth);
            }
            if(opening.getWidth()==10){
                opening.setSize(room.borderwidth,opening.getHeight());
            }

            opening.setBorder(BorderFactory.createLineBorder(Color.PINK, 0));
            room.repaint();
            room.revalidate();
            opening.removeMouseListener(opening.adapter);
        }

    }/*
    public void mouseReleased(MouseEvent e) {
        System.out.println("Opening clicked Released!");
        for(Opening opening:room.openings){
            System.out.println(opening.type);
            if(opening.getHeight()==10){
                opening.setSize(opening.getWidth(),room.borderwidth);
            }
            if(opening.getWidth()==10){
                opening.setSize(room.borderwidth,opening.getHeight());
            }

            opening.setBorder(BorderFactory.createLineBorder(Color.PINK, 0));
            room.repaint();
            room.revalidate();
            opening.removeMouseListener(opening.adapter);
        }

    }
    */
}

