package openings;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    // decide panel
    public Opening_MouseAdapter(Room room, Canvas canvas, String side, String type) {
        this.room = room;
        this.canvas= canvas;
        this.type = type; // door or window - deal with window later
        this.side=side;
        // setting door or window
        if(type.equals("door")){
            panel = new Opening(room);
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

        //room.openings.add(panel);
        System.out.println("Door added!");
    }
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //System.out.println(x+","+y);
        switch (side) {
            case "t":
                System.out.println("TOP selected");
                //initialX = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialX = room.get_grid_coords(x);
                initialY =0;
                panel.setLocation(initialX,initialY);


                break;
            case "b":
                System.out.println("B selected");
                //initialX = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialX = room.get_grid_coords(x);
                initialY =room.getHeight()-panel_size;
                panel.setLocation(initialX,initialY);
                break;

            case "l":
                System.out.println("L selected");
                //initialY = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialY = room.get_grid_coords(y);
                initialX = 0;
                panel.setLocation(initialX,initialY);
                break;
            case "r":
                System.out.println("R selected");
                //initialY = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialY = room.get_grid_coords(y);
                initialX = room.getWidth()-panel_size;
                panel.setLocation(initialX,initialY);
                break;
        }


        System.out.println("Initial coords:"+initialX+" "+initialY);
    }
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x>room.getWidth() || x<0 || y>room.getHeight() || y<0){
            Canvas.showDialog(canvas.frame,"Keep the door inside!!");
            room.remove(panel);
            room.repaint();
            room.removeMouseListener(this);
            room.removeMouseMotionListener(this);
            room.addMouseListener(room.mouse);
            room.addMouseMotionListener(room.mouse);
        }

        int length;
        int height;
        switch (side) {
            case "t":

                //length = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialX;
                length = room.get_grid_coords(x)-initialX;

                if(length<0){

                    panel.setLocation(x,initialY);
                    length= -length;
                }

                panel.setSize(length,panel_size);
                break;

            case "b":
                //length = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialX;
                length = room.get_grid_coords(x)-initialX;

                if(length<0){

                    panel.setLocation(x,initialY);
                    length= -length;
                }

                panel.setSize(length,panel_size);
                break;

            case "l":
                //height = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialY;
                height = room.get_grid_coords(y)-initialY;
                if(height<0){

                    panel.setLocation(initialX,y);
                    height= -height;
                }


                panel.setSize(panel_size,height);
                break;
            case "r":
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
    //door


}