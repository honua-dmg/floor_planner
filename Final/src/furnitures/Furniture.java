package furnitures;
import Main.Canvas;
import Main.Move;
import Main.Room;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Furniture extends Room{
    public static int width=100;
    public static int height=60;

    Delete_listener delete_listener;
    @Override
    public void setFurnitureCanvas() {}
    @Override
    public void setPopupMenu(){

    }
    @Override
    public void setBasics(){
        setSize(10, 10);
        setPreferredSize(new Dimension(10, 10));
        setBackground(Color.WHITE);
        setVisible(true);
        //setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        // borders
        setBorder(null);
    }
     @Override
    public void setHotCorners(){}
    public Furniture(Canvas furnitureCanvas){
        super(Color.WHITE,furnitureCanvas, furnitureCanvas.gridsize, furnitureCanvas.borderwidth);
        room_type= "furniture-"; // format will be furniture-{furniture_type}

    }
    public void add_delete_listener(){
        delete_listener = new Delete_listener(this);
        addMouseListener(delete_listener);
    }
}

class Delete_listener extends MouseAdapter {
    Furniture furniture;
    public Delete_listener(Furniture furniture){
        this.furniture = furniture;
    }
    public void mouseClicked(MouseEvent e){
        furniture.canvas.rooms.remove(furniture);
        furniture.canvas.remove(furniture);
        furniture.canvas.repaint();
        furniture.removeMouseListener(this);
    }
}

/*
        setSize(20, 20);
        setPreferredSize(new Dimension(20, 20));
        setBackground(Color.WHITE);
        setVisible(true);
        // borders
        this.setBorder(null);}

 */