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

    public Boolean overlap(){
        //System.out.println("Before loop");
        //System.out.println(getWidth()+" "+getHeight());
        for(Opening x:room.openings){
            if(x==this){
                //System.out.println("same room");
                continue;
            }
            //System.out.println("IN LOOP");
            //System.out.println(x.getWidth()+" "+x.getHeight());

            if (this.getHeight() == room.borderwidth && this.getY()==x.getY()) { // Horizontal check
                System.out.println("HORIZONTQLA");
                if (this.getX() < x.getX() + x.getWidth() && this.getX() + this.getWidth() > x.getX()) {
                    return true; // Horizontal overlap
                }
            }
            if (this.getWidth() == room.borderwidth && this.getX()==x.getX()) { // Vertical check
                System.out.println("VERTIQUIOLA");
                if (this.getY() < x.getY() + x.getHeight() && this.getY() + this.getHeight() > x.getY()) {
                    return true; // Vertical overlap
                }
            }
            /*
            if(this.getHeight()==room.borderwidth){
                System.out.println("horizontal");
                if(getX()<=x.getX() && getWidth()>=-getX()+x.getX()){
                    return true;
                }
                if(x.getX()<getX()&& x.getWidth()>getX()-x.getX()){
                    return true;
                }
            }
            if(this.getWidth()==room.borderwidth){
                System.out.println("vertical");
                if(getY()<x.getY() && getHeight()>-getY()+x.getY()){
                    return true;
                }
                if(x.getY()<getY()&& x.getHeight()>getY()-x.getY()){
                    return true;
                }
            }

             */
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
        if(opening.connected) {
            opening.adjacentRoom.openings.remove(opening.adjacentopening);
            opening.adjacentRoom.remove(opening.adjacentopening);
            opening.adjacentRoom.repaint();
        }
        room.openings.remove(opening);
        room.remove(opening);
        room.repaint();
        System.out.println("Opening clicked Released!");
        for(Opening opening:room.openings){
            System.out.println(opening.type);
            if(opening.getHeight()==10){
                opening.setSize(opening.getWidth(),room.borderwidth);
                if(opening.connected){
                opening.adjacentopening.setSize(opening.getWidth(),room.borderwidth);}
            }
            if(opening.getWidth()==10){
                opening.setSize(room.borderwidth,opening.getHeight());
                if(opening.connected){
                    opening.adjacentopening.setSize(room.borderwidth,opening.getHeight());
                }

            }

            opening.setBorder(BorderFactory.createLineBorder(Color.PINK, 0));
            if(opening.connected) {
                opening.adjacentopening.setBorder(BorderFactory.createLineBorder(Color.PINK, 0));
                opening.adjacentRoom.repaint();
            }
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

