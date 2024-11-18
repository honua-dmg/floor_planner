package openings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Main.Canvas;
import Main.Room;

public class Opening extends JPanel {
    //Boolean delete_toggle = false;
    public Boolean connected=false;               //*** in saving
    String type;                            //*** in saving
    Room room;
    Adapter adapter;
    public Room adjacentRoom;
    public Opening adjacentopening;


    public Opening(Room room,String type) {
        this.room = room;
        this.type = type;

    }
    public void setType(String type) {
        this.type = type;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }
    public void setAdjacentRoom(Room adjacentRoom) {
        this.adjacentRoom = adjacentRoom;
    }
    public void setAdjacentopening(Opening adjacentopening) {
        this.adjacentopening = adjacentopening;
        adjacentopening.adjacentopening=this;

        setAdjacentRoom(adjacentopening.room);
        adjacentopening.setAdjacentRoom(room);
        adjacentopening.connected=true;
        adjacentRoom.openings.add(adjacentopening);

    }

    public void add_listener(){
        this.adapter = new Adapter(room,this);
        addMouseListener(adapter);
    }
    public void remove(){
        if(connected){
            adjacentRoom.openings.remove(adjacentopening);
            System.out.println("removed adjacent opening");
            adjacentRoom.remove(adjacentopening);
            adjacentRoom.repaint();
        }
        room.openings.remove(this);
        System.out.println("removed opening");
        room.remove(this);
        room.repaint();
    }
    public Boolean equals(Opening opening){
        return room.getX()==opening.room.getX() && room.getY()==opening.room.getY();

    }
    public Boolean overlap(){
        for(Opening x:room.openings){
            if(this.equals(x)){
                continue;
            }
            if(type.equals("t") || type.equals("b")){
                if(getX()<x.getX() && getWidth()>-getX()+x.getX()){
                    return true;
                }
                if(x.getX()<getX()&& x.getWidth()>getX()-x.getX()){
                    return true;
                }
            }
            if(type.equals("l") || type.equals("r")){
                if(getY()<x.getY() && getHeight()>-getY()+x.getY()){
                    return true;
                }
                if(x.getY()<getY()&& x.getHeight()>getY()-x.getY()){
                    return true;
                }
            }
        }

        return false;
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
        opening.adjacentRoom.openings.remove(opening.adjacentopening);
        opening.adjacentRoom.remove(opening.adjacentopening);
        opening.adjacentRoom.repaint();
        room.openings.remove(opening);
        room.remove(opening);
        room.repaint();
        System.out.println("Opening clicked Released!");
        for(Opening opening:room.openings){
            System.out.println(opening.type);
            if(opening.getHeight()==10){
                opening.setSize(opening.getWidth(),room.borderwidth);
                opening.adjacentopening.setSize(opening.getWidth(),room.borderwidth);
            }
            if(opening.getWidth()==10){
                opening.setSize(room.borderwidth,opening.getHeight());
                opening.adjacentopening.setSize(room.borderwidth,opening.getHeight());
            }

            opening.setBorder(BorderFactory.createLineBorder(Color.PINK, 0));
            opening.adjacentopening.setBorder(BorderFactory.createLineBorder(Color.PINK, 0));
            opening.adjacentRoom.repaint();
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

