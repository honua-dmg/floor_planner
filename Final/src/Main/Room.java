package Main;

import openings.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Iterator;

import furnitures.Furniture;


public class Room extends JPanel {
    // everything the save functions will need;
    /*
    getX()
    getY()
    direction
    */
    public ArrayList<Opening> openings = new ArrayList<>();
    public int direction=0;


    public String room_type;
    public String[] available_furniture;




    public Canvas canvas;
    public Canvas furniture_canvas;

    public MouseAdapter mouse;

     HotCorner lt;
     HotCorner rb;

    public int borderwidth;
    int gridSize;

    public Color color;

    // popup menu
    JPopupMenu popup = new JPopupMenu();
    JMenuItem rotate = new JMenuItem("Rotate");
    JMenuItem delete = new JMenuItem("Delete");
    JMenuItem add_room = new JMenuItem("Add Room");
    JMenuItem resize = new JMenuItem("Resize");
    JMenuItem furniture = new JMenuItem("Furniture");
    JMenuItem door  = new JMenuItem("Door");
    JMenuItem window = new JMenuItem("Window");
    // once add_room is decided, we need to decide which side the room will be added to
    JPopupMenu side_popup = new JPopupMenu();
    JMenuItem left = new JMenuItem("Left");
    JMenuItem right = new JMenuItem("Right");
    JMenuItem top = new JMenuItem("Top");
    JMenuItem bottom = new JMenuItem("Bottom");

    // we need to decide whether the new room will be center left top right or bottom alligned
    JPopupMenu orientation_popup = new JPopupMenu();
    JMenuItem allign_centerX = new JMenuItem("Center");
    JMenuItem allign_centerY = new JMenuItem("Center");
    JMenuItem allign_top = new JMenuItem("Top");
    JMenuItem allign_bottom = new JMenuItem("Bottom");
    JMenuItem allign_left = new JMenuItem("Left");
    JMenuItem allign_right = new JMenuItem("Right");

    // doors

    public JPopupMenu opening_popup = new JPopupMenu();
    JMenuItem opening_type =  new JMenuItem("Type");

    JMenuItem top_door = new JMenuItem("Top");
    JMenuItem bottom_door = new JMenuItem("Bottom");
    JMenuItem left_door = new JMenuItem("Left");
    JMenuItem right_door = new JMenuItem("Right");

    //removing options
    JPopupMenu delete_options = new JPopupMenu("Delete Options");
    JMenuItem remove_opening = new JMenuItem("Opening");
    JMenuItem remove_room = new JMenuItem("Room");
    JMenuItem remove_furniture = new JMenuItem("Furniture");


    public void setFurnitureCanvas(){
        furniture_canvas = new Canvas();
        furniture_canvas.furniture_canvas=true;
        furniture_canvas.set_color(color);
        furniture_canvas.setLocation(borderwidth, borderwidth);
        furniture_canvas.setSize(getWidth()-borderwidth*2, getHeight()-borderwidth*2);
        furniture_canvas.setVisible(true);
        furniture_canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        furniture_canvas.standard_room_height=50;
        furniture_canvas.standard_room_width=50;
        this.add(furniture_canvas);
        setComponentZOrder(furniture_canvas,0);
        setComponentZOrder(lt,1);
        setComponentZOrder(rb,1);
    }
    public void setPopupMenu(){

        popup.add(rotate);
        popup.add(delete);
        popup.add(add_room);
        popup.add(furniture);
        popup.add(door);
        popup.add(window);
        popup.add(resize);


        // initialising side popup
        side_popup.add(left);
        side_popup.add(right);
        side_popup.add(top);
        side_popup.add(bottom);

        //add relative room
        // add room segment
        add_room.addActionListener( e ->{
            side_popup.show(canvas, this.getX(), this.getY());
        });
        left.addActionListener(e -> {
            orientation_options("Left");
        });
        right.addActionListener(e -> {
            orientation_options("Right");
        });
        top.addActionListener(e -> {
            orientation_options("Top");
        });
        bottom.addActionListener(e -> {
            orientation_options("Bottom");
        });
        allign_centerX.addActionListener(e -> {
            alignment("centerX");
        });
        allign_centerY.addActionListener(e -> {
            alignment("centerY");
        });
        allign_left.addActionListener(e -> {
            alignment("left");
        });
        allign_right.addActionListener(e -> {
            alignment("right");
        });
        allign_top.addActionListener(e -> {
            alignment("top");
        });
        allign_bottom.addActionListener(e -> {
            alignment("bottom");
        });


        // initialising rightclick popup:
        // rotate option

        rotate.addActionListener(e -> {
            this.rotate();
        });

        // delete option
        delete_options.add(remove_room);
        delete_options.add(remove_opening);
        delete_options.add(remove_furniture);

        delete.addActionListener(e -> {
            delete_options.show(canvas,this.getX(),this.getY());
        });
        remove_room.addActionListener(e -> {
            canvas.rooms.remove(this);
            canvas.remove(this);

            canvas.revalidate();
            canvas.repaint();
        });
        remove_opening.addActionListener(e -> {
            // highlight (white border) all openings, bring them forward
            for(Opening opening: openings){
                System.out.println(opening.toString());

                opening.add_listener();
            }
        });
        remove_furniture.addActionListener(e -> {
            for(Room furnitureZ: furniture_canvas.rooms){
                furnitureZ.add_delete_listener();
                System.out.println("DELETE ADDED!");
            }
        });


        // furniture pane TODO
        JPopupMenu furniture_list = new JPopupMenu();

        JMenuItem test = new JMenuItem("Test"); // will be added from the available furniture list
        furniture_list.add(test);

        furniture.addActionListener(e -> {
            furniture_list.show(canvas,this.getX(),this.getY());
        });
        test.addActionListener(e -> {
            furniture_canvas.standard_room_height = Furniture.height;
            furniture_canvas.standard_room_width = Furniture.width;
            furniture_canvas.addRoom(new Furniture(furniture_canvas));
        });


        // door and windows popups

        door.addActionListener(e -> {
            opening_type.setText("door");
            opening_type.setEnabled(false);
            opening_popup.add(opening_type);
            opening_popup.add(left_door);
            opening_popup.add(right_door);
            opening_popup.add(top_door);
            opening_popup.add(bottom_door);
            opening_popup.show(canvas, this.getX(), this.getY());
            remove_hotcorner_listner();
        });
        window.addActionListener(e -> {
            opening_type.setText("window");
            opening_type.setEnabled(false);
            opening_popup.add(opening_type);
            opening_popup.add(left_door);
            opening_popup.add(right_door);
            opening_popup.add(top_door);
            opening_popup.add(bottom_door);
            opening_popup.show(canvas, this.getX(), this.getY());
            remove_hotcorner_listner();
        });
        left_door.addActionListener(e -> {
            Opening_MouseAdapter adapter = new Opening_MouseAdapter(this,canvas,"l",opening_type.getText());
            removeMouseListener(mouse);
            removeMouseMotionListener(mouse);
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        });
        right_door.addActionListener(e -> {
            Opening_MouseAdapter adapter = new Opening_MouseAdapter(this,canvas,"r",opening_type.getText());
            removeMouseListener(mouse);
            removeMouseMotionListener(mouse);
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        });
        top_door.addActionListener(e -> {
            Opening_MouseAdapter adapter = new Opening_MouseAdapter(this,canvas,"t",opening_type.getText());
            removeMouseListener(mouse);
            removeMouseMotionListener(mouse);
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        });
        bottom_door.addActionListener(e -> {
            Opening_MouseAdapter adapter = new Opening_MouseAdapter(this,canvas,"b",opening_type.getText());
            removeMouseListener(mouse);
            removeMouseMotionListener(mouse);
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        });





        // making the horcorners visible incase a furniture piece is blocking them.
        popup.add(resize);
        resize.addActionListener(e -> {
            setComponentZOrder(lt, 0);
            setComponentZOrder(rb,0);
            /*
            lt.addMouseListener(lt.hotcornermouse);
            lt.addMouseMotionListener(lt.hotcornermouse);
            rb.addMouseListener(rb.hotcornermouse);
            rb.addMouseMotionListener(rb.hotcornermouse);
            */
            repaint();
            //revalidate();
        });

    }
    public void setHotCorners(){
        System.out.println(getWidth()+" "+getHeight());
        //  initialising hotcorners
        lt = new HotCorner(this, "lt", color,gridSize,borderwidth);
        lt.setBounds(borderwidth, borderwidth, 10, 10);
        add(lt);
        rb = new HotCorner(this, "rb", color,gridSize,borderwidth);
        rb.setBounds(getWidth() - 10 - borderwidth, getHeight() - 10 - borderwidth, 10, 10);
        add(rb);

    }
    public void rotate() {
        setBounds(getX(), getY(), getHeight(), getWidth());
        furniture_canvas.setSize(getWidth()-borderwidth*2, getHeight()-borderwidth*2);
        rb.setLocation(getWidth() - 10 - borderwidth, getHeight() - 10 - borderwidth);
        if(room_overlap()){
            Canvas.showDialog(canvas.frame,"ROOM OVERLAP!");
            setBounds(getX(),getY(),getHeight(),getWidth());
            furniture_canvas.setSize(getWidth()-borderwidth*2, getHeight()-borderwidth*2);
            rb.setLocation(getWidth() - 10 - borderwidth, getHeight() - 10 - borderwidth);
            return;
        }
        direction+=90;
        direction %= 360;
        //canvas.update_context_manager(this);


    }
    public void setMovemouse(){
        // Mouse adapter functionality - to move the room around
        mouse = new Move(this);
        addMouseListener(mouse); // to register clicks
        addMouseMotionListener(mouse); // to register drag
    }
    public void setBasics(){
        //Basic setup
        setLayout(null);
        setSize(canvas.standard_room_width, canvas.standard_room_height);
        setPreferredSize(new Dimension(canvas.standard_room_width, canvas.standard_room_height));
        // borders
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderwidth));
        setBackground(color);
        setVisible(true);
    }


    public Room(Color color, Canvas canvass, int gridSize, int borderwidth) {
        canvas = canvass;
        this.gridSize = gridSize;
        this.borderwidth = borderwidth;
        this.color = color;
        //this.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        setBasics();
        setMovemouse();
        setHotCorners();
        setFurnitureCanvas();
        setPopupMenu();
    }
    // these functions should ideally not be overidden
    public void orientation_options(String side){
        switch(side){
            case "Left":
                orientation_popup.add(allign_top);
                orientation_popup.add(allign_centerY);
                orientation_popup.add(allign_bottom);
                canvas.room_coords[0] = this.getX()-canvas.standard_room_width;
                break;
            case "Right":
                orientation_popup.add(allign_top);
                orientation_popup.add(allign_centerY);
                orientation_popup.add(allign_bottom);
                canvas.room_coords[0] = this.getX()+this.getWidth();
                break;
            case "Top":
                orientation_popup.add(allign_left);
                orientation_popup.add(allign_centerX);
                orientation_popup.add(allign_right);
                canvas.room_coords[1] = this.getY()-canvas.standard_room_height;
                break;
            case "Bottom":
                orientation_popup.add(allign_left);
                orientation_popup.add(allign_centerX);
                orientation_popup.add(allign_right);
                canvas.room_coords[1] = this.getY()+this.getHeight();
        }
        orientation_popup.show(canvas, this.getX(), this.getY());
    } // Add side popup
    public void alignment(String type){
        switch(type){
            case "left":

                canvas.room_coords[0] = this.getX();
                break;
            case "right":
                canvas.room_coords[0] = this.getX()+this.getWidth()-canvas.standard_room_width;
                break;
            case "top":
                canvas.room_coords[1] = this.getY();
                break;
            case "bottom":
                canvas.room_coords[1] = this.getY()+this.getHeight()-canvas.standard_room_height;
                break;
            case "centerX":
                int newX = Math.floorDiv(canvas.standard_room_width/2,10)*10;
                canvas.room_coords[0] = this.getX()+Math.floorDiv(this.getWidth()/2,10)*10-newX;
                break;
            case "centerY":
                int newY = Math.floorDiv(canvas.standard_room_height/2,10)*10;
                canvas.room_coords[1]= this.getY()+Math.floorDiv(this.getHeight()/2,10)*10-newY;
                break;

        }
        //System.out.println("X,Y:"+getX()+","+getY()+" NewX,NewY:"+canvas.room_coords[0]+","+canvas.room_coords[1]);
        canvas.wrt_room = true;
        orientation_popup.removeAll();

    }           // add alignment popup
    public void remove_hotcorner_listner(){
        lt.removeMouseListener(lt.hotcornermouse);
        lt.removeMouseMotionListener(lt.hotcornermouse);
        rb.removeMouseListener(rb.hotcornermouse);
        rb.removeMouseMotionListener(rb.hotcornermouse);

    }
    public void add_hotcorner_listner(){

        lt.addMouseListener(lt.hotcornermouse);
        lt.addMouseMotionListener(lt.hotcornermouse);
        rb.addMouseListener(rb.hotcornermouse);
        rb.addMouseMotionListener(rb.hotcornermouse);
    }
    public  boolean room_overlap() {
        int lt_roomX = getX();
        int lt_roomY = getY();
        int rb_roomX = getX()+getWidth();
        int rb_roomY = getY()+getHeight();
        boolean overlap = false;
        //canvas.rooms.remove(this); // TODO : THIS WILL CAUSE PROBLEMS
        //
        for (Room room : canvas.rooms) {
            if (room == this) {
                continue;
            }
            int lt_otherX = room.getX();
            int lt_otherY = room.getY();
            int rb_otherX = room.getX() + room.getWidth();
            int rb_otherY = room.getY() + room.getHeight();

            if (overlap(lt_otherX, lt_otherY, rb_otherX, rb_otherY,
                    lt_roomX, lt_roomY, rb_roomX, rb_roomY)) {
                overlap = true;
                //(getX()+","+getY()+":"+(getX()+getWidth())+","+(getY()+getHeight()));
                //System.out.println(room.getX()+","+room.getY()+":"+(room.getX()+room.getWidth())+","+(room.getY()+room.getHeight()));
            }
        }

        return overlap;
    }
    public boolean overlap(int ltx1, int lty1, int rbx1, int rby1, int ltx2, int lty2, int rbx2, int rby2) {

        return (ltx1 < rbx2 && rbx1 > ltx2 && lty1 < rby2 && rby1 > lty2);
    }
    public String areconnected(Room room){

        // bottom                               // top
        if(room.getY()+room.getHeight()==getY() ){
            //System.out.println("tb match;");
            return "t";

        }
        if(getY()+getHeight()==room.getY()){
            return "b";
        }
        // left                            // right
        if(room.getX()==getX()+getWidth()) {
            //System.out.println("s match;"+getY());
            return "r";
        }
        if(getX()==room.getX()+room.getWidth()){
            return "l";
        }

        return null;
    }
    public boolean intersection(int lt1,int lt2, int rb1, int rb2){
        // here the variables are called lt1, rb1 not to indicate said points, but to reference that
        // variable should contain the coords of the leftmost OR the topmost coordinate of the rect
        int upper=Math.max(lt1, lt2);
        int lower=Math.min(rb1, rb2);
        return lower-upper> 0;

    } // common length between 2 nearby rooms
    public int snap(int center, int lt, int rb){
        if (center-lt>rb-center && rb-center<=20){

            return rb;
        }else if (center-lt<rb-center && rb-center<=20){
            return lt;
        }else if(2*center-(lt+rb)<10 &&2*center-(lt+rb)>-10){
            return (lt+rb)/2;
        }
        return -1;

    }    // snap to nearby panels
    public int get_grid_coords(int x){
        int newX = Math.floorDiv(x,gridSize)*gridSize;
        if(canvas.furniture_canvas){
            newX-=borderwidth;}
        return  newX;
    }    // convert standard coords to grid (grid snap)
    static class Nearby {
        String side;
        Room room;

        public Nearby(String side, Room room) {
            this.side = side;
            this.room = room;
        }
    }
    public Nearby isroomnearby() {

        for (Room room : canvas.rooms) {
            if (room == this) {
                continue;
            }
            // 4 cases l t r b (top,left,bottom,right)
            // left
            int left_dist = getX() - (room.getX() + room.getWidth());
            if ((left_dist == 10 ) || (left_dist == -20)||left_dist == -10||left_dist == 0) {
                if(intersection(
                        getY(), room.getY(), getY()+getHeight(), room.getY()+room.getHeight()
                )) {
                    return new Nearby("l", room);

                }
            }
            // top
            int top_dist = getY() - (room.getY() + room.getHeight());
            if ((top_dist == 10 ) || (top_dist == -20)||top_dist == -10|| top_dist == 0) {
                if(intersection(
                        getX(),room.getX(),getX()+getWidth(),room.getX()+room.getWidth()
                )){
                    return new Nearby("t", room);
                }

            }
            // right
            int right_dist = -(getX() + this.getWidth()) + (room.getX());
            if ((right_dist == 10 ) || (right_dist == -20)||right_dist == -10||right_dist == 0) {
                if (intersection(
                        getY(), room.getY(), getY()+getHeight(), room.getY()+room.getHeight()
                )){
                    return new Nearby("r", room);
                }


            }
            // bottom
            int bottom_dist = -(getY() + this.getHeight()) + (room.getY());
            if ((bottom_dist == 10 ) || (bottom_dist == -20)||bottom_dist == -10||bottom_dist == 0) {
                if(intersection(
                        getX(),room.getX(),getX()+getWidth(),room.getX()+room.getWidth()
                )){
                    return new Nearby("b", room);
                }

            }

        }
        return null;


    }    // check for nearby panels
    // get the intersection of the sides

    public void add_delete_listener(){}




}



/*
class HotCorner extends JPanel {
    int gridSize;
    int borderwidth;
    Room owner;
    MouseAdapter hotcornermouse;

    public HotCorner(Room owner, String corner, Color x, int gridSize, int borderwidth) {
        setBackground(Color.PINK); // TODO: DELETE THIS LATER
        this.gridSize = gridSize;
        this.borderwidth= borderwidth;
        this.owner = owner;
        // most of the work is done here:
        hotcornermouse = new MouseAdapter() {
            final int minsize = 40;

            int X;// mouse coords
            int Y;// mouse coords
            int intialx;
            int intialy;
            int initiallen;
            int initialwid;

            public void mousePressed(MouseEvent e) {
                // this function will let us know that the mouse pressed on the hotcorner and get it's coords
                X = e.getX();
                Y = e.getY();
                //System.out.println("hotcorner presssed");
                intialx = owner.getX();
                intialy = owner.getY();
                initiallen = owner.getHeight();
                initialwid = owner.getWidth();
            }

            public void mouseDragged(MouseEvent e) {

                int newWidth;
                int newHeight;
                switch (corner) {
                    // 2 cases depending on the corner
                    case "lt":
                        // figuring out new width and height
                        newWidth = owner.getWidth() + (X - e.getX());
                        newHeight = owner.getHeight() + (Y - e.getY());
                        // minimum size for room
                        if (newWidth > minsize && newHeight > minsize) {
                            // setting new width and height
                            owner.setSize(newWidth, newHeight);
                            owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);
                            // changing location of origin
                            owner.setLocation(owner.getX() + e.getX() - X, owner.getY() + e.getY() - Y);
                            // changing location of rb hotcorner so it stays on the rb corner
                            // java is cool for doing this - i don't know what the fuck this is, I'm using aggregation to
                            // call an instance of this class and set it's location ;/ mind fuck
                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);

                        } else if (newWidth > minsize) {

                            owner.setLocation(owner.getX() + e.getX() - X, owner.getY() + (owner.getHeight() - minsize));
                            owner.setSize(newWidth, minsize);
                            owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);

                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);

                        } else if (newHeight > minsize) {
                            owner.setLocation(owner.getX() + (owner.getWidth() - minsize), owner.getY() + e.getY() - Y);
                            owner.setSize(minsize, newHeight);

                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                            owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);
                        }


                        break;
                    case "rb":
                        // when rb is pulled, origin will stay fixed
                        // new width and height
                        newWidth = owner.getWidth() - (X - e.getX());
                        newHeight = owner.getHeight() - (Y - e.getY());
                        if (newWidth > minsize && newHeight > minsize) {
                            owner.setSize(newWidth, newHeight);
                            // moving rb around to make sure it stays on the corner

                        } else if (newWidth > minsize) {
                            owner.setSize(newWidth, minsize);
                        } else if (newHeight > minsize) {
                            owner.setSize(minsize, newHeight);
                        }
                        setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                        owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);
                        break;
                }


                // bringing the room in question up so it's easier to work with
                // TODO: DELETE THIS LATER
                owner.setComponentZOrder(e.getComponent(), 0);
                //owner.canvas.update_context_manager(owner);
            }

            // this function deals with snapping to the grid
            public void mouseReleased(MouseEvent e) {
                int newYcoord;
                int newXcoord;
                int newWidth;
                int newHeight;

                switch (corner) {
                    //we want the corners to snap inwards, i.e the area of the room should only get smaller
                    // as opposed to bigger because I believe it'll be easier on the overlap checker if we do this
                    // also decreases the likelihood of the overlap checker being called in the first place
                    case "lt":
                        //newYcoord = Math.floorDiv(owner.getY(), gridSize) * gridSize;
                        newYcoord = owner.get_grid_coords(owner.getY());

                        if (newYcoord < owner.getY()) {
                            newYcoord += 10;
                        }

                        //newXcoord = Math.floorDiv(owner.getX(), gridSize) * gridSize;
                        newXcoord = owner.get_grid_coords(owner.getX());
                        if (newXcoord < owner.getX()) {
                            newXcoord += 10;
                        }

                        newWidth = owner.getWidth() + (owner.getX() - newXcoord);
                        newHeight = owner.getHeight() + (owner.getY() - newYcoord);
                        // TODO :OVERLAP CHECK
                        if (newWidth > 20 && newHeight > 20) {
                            owner.setSize(newWidth, newHeight);
                            owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);
                            owner.setLocation(newXcoord, newYcoord);
                            // setting rb to the right bottom corner yet again- yes it's pretty annoying,
                            // and yes it's necessary because we have to change the width of the room when we're pulling lt
                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                        }

                        break;
                    case "rb":
                        //newWidth = Math.floorDiv(owner.getWidth(), gridSize) * gridSize;
                        newWidth = owner.get_grid_coords(owner.getWidth());
                        //newHeight = Math.floorDiv(owner.getHeight(), gridSize) * gridSize;
                        newHeight = owner.get_grid_coords(owner.getHeight());
                        owner.setSize(newWidth, newHeight);
                        owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);
                        setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                }


                if(owner.room_overlap()){
                    owner.setLocation(intialx, intialy);
                    owner.setSize(initialwid,initiallen);
                    owner.furniture_canvas.setSize(owner.getWidth()-owner.borderwidth*2, owner.getHeight()-owner.borderwidth*2);
                    owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                    Canvas.showDialog(owner.canvas.frame,"ROOM OVERLAP!");
                }

                owner.setComponentZOrder(owner.furniture_canvas, 0);
                //owner.canvas.update_context_manager(owner);
            }
        };
        addMouseListener(hotcornermouse);
        addMouseMotionListener(hotcornermouse);
    }
}
*/


