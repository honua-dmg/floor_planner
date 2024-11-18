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
    int adjX;
    int adjY;
    String type;
    String side;
    int panel_size ;
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
        this.panel_size = room.borderwidth;
        // setting door or window
        if(type.equals("door")){
            panel = new Door(room);
            //panel.setType("door");
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

        }



        room.add(panel);
        // which rooms are connected? and which side?
        connectedRooms = new _ConnectedRooms();
        for(Room x:canvas.rooms){
                connectedRooms.add(x,room.areconnected(x));
        }
        connectedRooms.sort();

    // get bounds
        switch(side){
            case "t":
                //System.out.println(connectedRooms.top);
                if(connectedRooms.top.isEmpty()){
                    bounds.add(0);
                    bounds.add(room.getWidth());
                    break;
                }
                for(Room room1:connectedRooms.top){
                    add_bounds(room1.getX(),room1.getWidth(),room.getX(),room.getWidth());


                }
                if(!bounds.contains(0)){
                    bounds.add(0);}
                if(!bounds.contains(room.getWidth())){
                    bounds.add(room.getWidth());}
                Collections.sort(bounds);
                break;
            case "b":
                //System.out.println(connectedRooms.bottom);
                if(connectedRooms.bottom.isEmpty()){
                    bounds.add(0);
                    bounds.add(room.getWidth());
                    break;
                }
                for(Room room1:connectedRooms.bottom){
                    add_bounds(room1.getX(),room1.getWidth(),room.getX(),room.getWidth());
                }
                if(!bounds.contains(0)){
                    bounds.add(0);
                }
                if(!bounds.contains(room.getWidth())){
                    bounds.add(room.getWidth());
                }
                Collections.sort(bounds);
                break;
            case "r":
                //System.out.println(connectedRooms.right);
                if(connectedRooms.right.isEmpty()){
                    bounds.add(0);
                    bounds.add(room.getHeight());
                    break;
                }
                for(Room room1:connectedRooms.right){
                    add_bounds(room1.getY(),room1.getHeight(),room.getY(),room.getHeight());
                }
                if(!bounds.contains(0)){
                    bounds.add(0);
                }
                if(!bounds.contains(room.getHeight())){
                    bounds.add(room.getHeight());
                }
                Collections.sort(bounds);

                break;
            case "l":
                //System.out.println(connectedRooms.left);
                if(connectedRooms.left.isEmpty()){
                    bounds.add(0);
                    bounds.add(room.getHeight());
                    break;
                }
                for(Room room1:connectedRooms.left){
                    add_bounds(room1.getY(),room1.getHeight(),room.getY(),room.getHeight());
                }
                if(!bounds.contains(0)){
                    bounds.add(0);
                }
                if(!bounds.contains(room.getHeight())){
                    bounds.add(room.getHeight());
                }
                Collections.sort(bounds);
                break;
        }

        //System.out.println("Door added!");
    }
    public void add_bounds(int con_room_coord,int cont_room_len,int room_coord,int room_len){
        int lower;
        int upper;


        lower = con_room_coord-room_coord;


        upper = con_room_coord+cont_room_len-room_coord;

        if(!(con_room_coord<room_coord)){
            if(!bounds.contains(lower)){
            bounds.add(lower);}
        }
        if(!((con_room_coord+cont_room_len)>room_len+room_coord)){
            if(!bounds.contains(upper)){
                bounds.add(upper);}

        }

    }
    public Boolean connected_ah(int lower_bound,int upperbound,String side){
        return switch (side) {
            case "l" -> {
                for (Room room1 : connectedRooms.left) {
                    if (room1.getY() - room.getY() == lower_bound || room1.getY() + room1.getHeight() - room.getY() == upperbound) {
                        panel.connected = true;
                        panel.setAdjacentRoom(room1);
                        yield true;
                    }
                }
                yield false;
            }
            case "r" -> {
                for (Room room1 : connectedRooms.right) {
                    if (room1.getY() - room.getY() == lower_bound || room1.getY() + room1.getHeight() - room.getY() == upperbound) {
                        panel.connected = true;
                        panel.setAdjacentRoom(room1);
                        yield true;
                    }
                }
                yield false;
            }
            case "t" -> {
                for (Room room1 : connectedRooms.top) {
                    if (room1.getX() - room.getX() == lower_bound || room1.getX() + room1.getWidth() - room.getX() == upperbound) {
                        panel.connected = true;
                        panel.setAdjacentRoom(room1);
                        yield true;
                    }
                }
                yield false;
            }
            case "b" -> {
                for (Room room1 : connectedRooms.bottom) {
                    if (room1.getX() - room.getX() == lower_bound || room1.getX() + room1.getWidth() - room.getX() == upperbound) {
                        panel.connected = true;
                        panel.setAdjacentRoom(room1);
                        yield true;
                    }
                }
                yield false;
            }
            default -> false;
        };
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
                initialX = room.get_grid_coords(x);
                initialY =0;
                //initialX = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                if(connected_ah(lowerbound,upperbound,side)){
                    if(type.equals("window")) {
                        Canvas.showDialog(canvas.frame,"Window's can't be between rooms!");
                        remove_opening();
                        break;
                    }
                        panel.setAdjacentopening(new Door(panel.adjacentRoom));

                    panel.adjacentRoom.add(panel.adjacentopening);

                        adjX = panel.adjacentRoom.get_grid_coords(initialX+room.getX()-panel.adjacentRoom.getX());
                        adjY = panel.adjacentRoom.getHeight()-panel_size;
                        panel.adjacentopening.setLocation(adjX,adjY);

                }else if((room.room_type.equals("bedroom")||room.room_type.equals("bathroom")||room.room_type.equals("dining")
                )&&panel.type.equals("door")){
                    Canvas.showDialog(canvas.frame,room.room_type+" can't have a door outside");
                    remove_opening();
                    break;
                }


                panel.setLocation(initialX,initialY);


                break;
            case "b":
                for(int bound: bounds){
                    if(x>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                //System.out.println("B selected");

                //initialX = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialX = room.get_grid_coords(x);
                initialY =room.getHeight()-panel_size;
                if(connected_ah(lowerbound,upperbound,side)){
                    if(type.equals("window")) {
                        Canvas.showDialog(canvas.frame,"Window's can't be between rooms!");
                        remove_opening();
                        break;
                    }

                        panel.setAdjacentopening(new Door(panel.adjacentRoom));

                    panel.adjacentRoom.add(panel.adjacentopening);

                    adjX = panel.adjacentRoom.get_grid_coords(initialX+room.getX()-panel.adjacentRoom.getX());
                    adjY = 0;
                    panel.adjacentopening.setLocation(adjX,adjY);

                }else if((room.room_type.equals("bedroom")||room.room_type.equals("bathroom")||room.room_type.equals("dining")
                )&&panel.type.equals("door")){
                    Canvas.showDialog(canvas.frame,room.room_type+" can't have a door outside");
                    remove_opening();
                    break;
                }
                panel.setLocation(initialX,initialY);
                break;

            case "l":
                for(int bound: bounds){
                    if(y>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                //System.out.println("L selected");
                //initialY = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialY = room.get_grid_coords(y);
                initialX = 0;
                if(connected_ah(lowerbound,upperbound,side)){
                    if(type.equals("window")) {
                        Canvas.showDialog(canvas.frame,"Window's can't be between rooms!");
                        remove_opening();
                        break;
                    }
                    panel.setAdjacentopening(new Door(panel.adjacentRoom));

                    panel.adjacentRoom.add(panel.adjacentopening);

                    adjY = panel.adjacentRoom.get_grid_coords(initialY+room.getY()-panel.adjacentRoom.getY());
                    adjX = panel.adjacentRoom.getWidth()-panel_size;
                    panel.adjacentopening.setLocation(adjX,adjY);

                }else if((room.room_type.equals("bedroom")||room.room_type.equals("bathroom")||room.room_type.equals("dining")
                )&&panel.type.equals("door")){
                    Canvas.showDialog(canvas.frame,room.room_type+" can't have a door outside");
                    remove_opening();
                    break;
                }
                panel.setLocation(initialX,initialY);
                break;

            case "r":
                for(int bound: bounds){
                    if(y>bound){
                        lowerbound = bound;
                        upperbound = bounds.get(bounds.indexOf(bound)+1);
                    }
                }
                //System.out.println("R selected");
                //initialY = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize;
                initialY = room.get_grid_coords(y);
                initialX = room.getWidth()-panel_size;
                if(connected_ah(lowerbound,upperbound,side)){
                    if(type.equals("window")) {
                        Canvas.showDialog(canvas.frame,"Window's can't be between rooms!");
                        remove_opening();
                        break;
                    }
                    panel.setAdjacentopening(new Door(panel.adjacentRoom));
                    panel.adjacentRoom.add(panel.adjacentopening);
                    adjY = panel.adjacentRoom.get_grid_coords(initialY+room.getY()-panel.adjacentRoom.getY());
                    adjX =0;
                    panel.adjacentopening.setLocation(adjX,adjY);

                }else if((room.room_type.equals("bedroom")||room.room_type.equals("bathroom")||room.room_type.equals("dining")
                )&&panel.type.equals("door")){
                    Canvas.showDialog(canvas.frame,room.room_type+" can't have a door outside");
                    remove_opening();
                    break;
                }
                panel.setLocation(initialX,initialY);
                break;
        }

        if(panel.connected){
            panel.adjacentopening.setBackground(panel.adjacentRoom.color);
        }

        //System.out.println("bounds:"+lowerbound+","+upperbound);
        //System.out.println(bounds);
    }
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x>=room.getWidth() || x<=0|| y>=room.getHeight() || y<=0){
            //Canvas.showDialog(canvas.frame,"Keep the door inside!!");
            return;
            //remove_opening();
        }

        int length;
        int height;
        switch (side) {
            case "t":
                if(x>upperbound||x<lowerbound){
                    break;
                    //Canvas.showDialog(canvas.frame,"Bounds crossed!");
                    //remove_opening();
                }
                //length = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialX;
                length = room.get_grid_coords(x)-initialX;

                if(length<0){

                    panel.setLocation(x,initialY);
                    if(panel.connected) {
                        panel.adjacentopening.setLocation(x + room.getX() - panel.adjacentRoom.getX(), adjY);
                    }
                    length= -length;
                }

                panel.setSize(length,panel_size);
                if(panel.connected){
                    panel.adjacentopening.setSize(length,panel_size);
                }
                break;

            case "b":
                if(x>upperbound||x<lowerbound){
                    break;
                }
                //length = Math.floorDiv(x,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialX;
                length = room.get_grid_coords(x)-initialX;

                if(length<0){
                    if(panel.connected) {
                        panel.adjacentopening.setLocation(x + room.getX() - panel.adjacentRoom.getX(), adjY);
                    }
                    panel.setLocation(x,initialY);
                    length= -length;
                }

                panel.setSize(length,panel_size);
                if(panel.connected){
                panel.adjacentopening.setSize(length,panel_size);
                }
                break;

            case "l":
                if(y>upperbound||y<lowerbound){
                    break;
                    //Canvas.showDialog(canvas.frame,"Bounds crossed!");
                    //remove_opening();
                }
                //height = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialY;
                height = room.get_grid_coords(y)-initialY;
                if(height<0){

                    panel.setLocation(initialX,y);
                    if(panel.connected) {
                        panel.adjacentopening.setLocation(adjX, room.getY()+y-panel.adjacentRoom.getY());
                    }
                    height= -height;
                }


                panel.setSize(panel_size,height);
                if(panel.connected){
                    panel.adjacentopening.setSize(panel_size,height);
                }
                break;
            case "r":
                if(y>upperbound||y<lowerbound){
                    break;
                    //Canvas.showDialog(canvas.frame,"Bounds crossed!");
                   //remove_opening();
                }
                //height = Math.floorDiv(y,room.furniture_canvas.gridsize)*room.furniture_canvas.gridsize-initialY;
                height = room.get_grid_coords(y)-initialY;
                if(height<0){
                    if(panel.connected) {
                        panel.adjacentopening.setLocation(adjX, room.getY()+y-panel.adjacentRoom.getY());
                    }
                    panel.setLocation(initialX,y);
                    height= -height;
                }


                panel.setSize(panel_size,height);
                if(panel.connected){
                    panel.adjacentopening.setSize(panel_size,height);
                }
                break;
        }
        panel.repaint();
    }
    public void mouseReleased(MouseEvent e) {
        room.openings.add(panel);
        if(panel.overlap()) {

            Canvas.showDialog(canvas.frame,"Opening OVERLAP!");
            panel.remove();
            System.out.println(room.openings);


        }else{
            System.out.println("opening no OVERLAP");

        }
        System.out.println(room.openings.size());



        room.removeMouseListener(this);
        room.removeMouseMotionListener(this);
        room.addMouseListener(room.mouse);
        room.addMouseMotionListener(room.mouse);
        room.add_hotcorner_listner();
        room.opening_popup.removeAll();
        //System.out.println("ending coords:"+(initialX+panel.getWidth())+" "+(initialY+panel.getHeight()));
        room.setComponentZOrder(room.furniture_canvas,0);
        room.repaint();
    }
    public void remove_opening(){

        room.remove(panel);
        room.removeMouseListener(this);
        room.removeMouseMotionListener(this);
        room.addMouseListener(room.mouse);
        room.addMouseMotionListener(room.mouse);
        room.add_hotcorner_listner();
    }
    //door
}
