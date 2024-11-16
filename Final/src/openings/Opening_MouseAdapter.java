package openings;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Main.Room;
import Main.Canvas;
import Main._ConnectedRooms;


public class Opening_MouseAdapter extends MouseAdapter {
    Room room;
    Canvas canvas;
    int initialX;
    int initialY;
    String type;
    String side;
    int panel_size = 10;
    Opening panel;
    _ConnectedRooms connectedRooms;
    ArrayList<Integer> bounds = new ArrayList<>();
    int upperbound;
    int lowerbound;
    // decide panel
    public Opening_MouseAdapter(Room room, Canvas canvas, String side, String type) {
        this.room = room;
        this.canvas= canvas;
        this.type = type; // door or window - deal with window later
        this.side=side;
        // setting door or window
        if(type.equals("door")){
            panel = new Door(room);
            panel.setType("door");
            //panel.setBackground(Color.WHITE);
            panel.setBackground(room.color);
        }
        else {
            switch (side) {
                case "l":
                    panel = new RoomWindow(room,"vertical",room.color,false);
                    break;
                case "r":
                    panel = new RoomWindow(room,"vertical",room.color,true);
                    break;
                case "t":
                    panel = new RoomWindow(room,"horizontal",room.color,false);
                    break;
                case "b":
                    panel = new RoomWindow(room,"horizontal",room.color,true);
            } // making windows
            if(panel!=null){
                panel.setType("window");
            }
        }

        room.add(panel);
        connectedRooms = new _ConnectedRooms();

        for(Room x:canvas.rooms){
            connectedRooms.add(x,room.areconnected(x));
        }
        connectedRooms.sort();
        switch(side){
            case "t":
                bounds.add(0);
                bounds.add(room.getWidth());
                if(connectedRooms.top.isEmpty()){
                    break;
                }
                for(Room room1:connectedRooms.top){
                    bounds.add(room1.getX()-room.getX());
                    if((room1.getX()+room1.getWidth())<room.getWidth()+room.getX()){
                        bounds.add(room1.getX()+room1.getWidth()-(room.getWidth()+room.getX()));
                    }

                }
                //Collections.sort(bounds);

                break;
            case "b":
                bounds.add(0);
                bounds.add(room.getWidth());
                if(connectedRooms.bottom.isEmpty()){
                    break;
                }
                for(Room room1:connectedRooms.bottom){
                    bounds.add(room1.getX()-room.getX());
                    if((room1.getX()+room1.getWidth())<room.getWidth()+room.getX()){
                        bounds.add(room1.getX()+room1.getWidth()-(room.getWidth()+room.getX()));
                    }
                }
                Collections.sort(bounds);
                break;
            case "r":
                bounds.add(0);
                bounds.add(room.getHeight());
                if(connectedRooms.right.isEmpty()){
                    break;
                }
                for(Room room1:connectedRooms.right){
                    bounds.add(room1.getY()-room.getY());
                    if((room1.getY()+room1.getHeight())<room.getHeight()+room.getY()){
                        bounds.add(room1.getY()+room1.getHeight()-(room.getHeight()+room.getY()));
                    }
                }
                    Collections.sort(bounds);

                break;
            case "l":
                bounds.add(0);
                bounds.add(room.getHeight());
                if(connectedRooms.left.isEmpty()){
                    break;
                }
                for(Room room1:connectedRooms.left){
                    bounds.add(room1.getY()-room.getY());
                    if((room1.getY()+room1.getHeight())<room.getHeight()+room.getY()){
                        bounds.add(room1.getY()+room1.getHeight()-(room.getHeight()+room.getY()));
                    }
                }
                Collections.sort(bounds);

                break;
        }
        //room.openings.add(panel);
        System.out.println("Door added!");
    }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //System.out.println(x+","+y);
        bounds.sort(Comparator.naturalOrder());
        switch (side) {
            case "t":
                for(int bound: bounds){
                    if(x>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                //initialX = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialX = room.get_grid_coords(x);
                initialY =0;
                panel.setLocation(initialX,initialY);


                break;
            case "b":
                for(int bound: bounds){
                    if(x>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                System.out.println("B selected");

                //initialX = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialX = room.get_grid_coords(x);
                initialY =room.getHeight()-panel_size;
                panel.setLocation(initialX,initialY);
                break;

            case "l":
                for(int bound: bounds){
                    if(y>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                System.out.println("L selected");
                //initialY = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialY = room.get_grid_coords(y);
                initialX = 0;
                panel.setLocation(initialX,initialY);
                break;
            case "r":
                for(int bound: bounds){
                    if(y>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                System.out.println("R selected");
                //initialY = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialY = room.get_grid_coords(y);
                initialX = room.getWidth()-panel_size;
                panel.setLocation(initialX,initialY);
                break;
        }


        System.out.println("bounds:"+lowerbound+","+upperbound);
        System.out.println(bounds);
    }
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x>room.getWidth() || x<0 || y>room.getHeight() || y<0){
            Canvas.showDialog(canvas.frame,"Keep the door inside!!");
            remove_opening();
        }

        int length;
        int height;
        switch (side) {
            case "t":
                if(x>upperbound||x<lowerbound){
                    Canvas.showDialog(canvas.frame,"Bounds crossed!");
                    remove_opening();
                }
                //length = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialX;
                length = room.get_grid_coords(x)-initialX;

                if(length<0){

                    panel.setLocation(x,initialY);
                    length= -length;
                }

                panel.setSize(length,panel_size);
                break;

            case "b":
                if(x>upperbound||x<lowerbound){
                    Canvas.showDialog(canvas.frame,"Bounds crossed!");
                    remove_opening();
                }
                //length = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialX;
                length = room.get_grid_coords(x)-initialX;

                if(length<0){

                    panel.setLocation(x,initialY);
                    length= -length;
                }

                panel.setSize(length,panel_size);
                break;

            case "l":
                if(y>upperbound||y<lowerbound){
                    Canvas.showDialog(canvas.frame,"Bounds crossed!");
                    remove_opening();
                }
                //height = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialY;
                height = room.get_grid_coords(y)-initialY;
                if(height<0){

                    panel.setLocation(initialX,y);
                    height= -height;
                }


                panel.setSize(panel_size,height);
                break;
            case "r":
                if(y>upperbound||y<lowerbound){
                    Canvas.showDialog(canvas.frame,"Bounds crossed!");
                    remove_opening();
                }
                //height = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialY;
                height = room.get_grid_coords(y)-initialY;
                if(height<0){

                    panel.setLocation(initialX,y);
                    height= -height;
                }


                panel.setSize(panel_size,height);
                break;
        }
        panel.repaint();
    }
    public void mouseReleased(MouseEvent e) {
        if(type.equals("Window")){
            room.openings.add(panel);

        }else{
            room.openings.add(panel);
        }
        room.removeMouseListener(this);
        room.removeMouseMotionListener(this);
        room.addMouseListener(room.mouse);
        room.addMouseMotionListener(room.mouse);
        room.opening_popup.removeAll();
        System.out.println("ending coords:"+(initialX+panel.getWidth())+" "+(initialY+panel.getHeight()));
        room.setComponentZOrder(room.furniture_canvas,0);
        room.repaint();

    }
    public void remove_opening(){
        room.remove(panel);
        room.repaint();
        room.removeMouseListener(this);
        room.removeMouseMotionListener(this);
        room.addMouseListener(room.mouse);
        room.addMouseMotionListener(room.mouse);
    }
    //door
}
class SpaceType{
    Opening type;


}