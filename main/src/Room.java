package main.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Room extends JPanel {
    Canvas canvas;
    Canvas furniture_canvas;
    HotCorner lt;
    HotCorner rb;
    int borderwidth;
    int gridSize;
    int [] new_room_coords;
    //int gridSize;
    // popup menu
    JPopupMenu popup = new JPopupMenu();
    JMenuItem rotate = new JMenuItem("Rotate");
    JMenuItem delete = new JMenuItem("Delete");
    JMenuItem add_room = new JMenuItem("Add Room");
    JMenuItem resize = new JMenuItem("Resize");
    JMenuItem furniture = new JMenuItem("Furniture");
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
    }

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
                int newX = Math.floorDiv(canvas.standard_room_width/2,10)*10;;
                canvas.room_coords[0] = this.getX()+Math.floorDiv(this.getWidth()/2,10)*10-newX;
                break;
            case "centerY":
                int newY = Math.floorDiv(canvas.standard_room_height/2,10)*10;;
                canvas.room_coords[1]= this.getY()+Math.floorDiv(this.getHeight()/2,10)*10-newY;
                break;

        }
        System.out.println("X,Y:"+getX()+","+getY()+" NewX,NewY:"+canvas.room_coords[0]+","+canvas.room_coords[1]);
        canvas.wrt_room = true;
        orientation_popup.removeAll();

    }


    public Room(Color x, Canvas canvass, int gridSize, int borderwidth) {
        canvas = canvass;
        this.gridSize = gridSize;
        this.borderwidth = borderwidth;
        //Basic setup
        setLayout(null);
        setSize(100, 50);
        setPreferredSize(new Dimension(100, 50));
        setBackground(x);
        setVisible(true);
        furniture_canvas = new Canvas();
        furniture_canvas.set_color(x);
        furniture_canvas.setGridsize(5);


        // borders

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        // initialising side popup
        side_popup.add(left);
        side_popup.add(right);
        side_popup.add(top);
        side_popup.add(bottom);

        // initialising rightclick popup:
        // rotate option
        popup.add(rotate);
        rotate.addActionListener(e -> {
            this.rotate();
        });
        // delete option
        popup.add(delete);

        delete.addActionListener(e -> {
            canvas.remove(this);
            canvas.revalidate();
            canvas.repaint();
        });
        popup.add(add_room);

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
        // furniture pane TODO
        popup.add(furniture);

        popup.add(resize);
        resize.addActionListener(e -> {
            setComponentZOrder(lt, 0);
            setComponentZOrder(rb,0);
            repaint();
            revalidate();
        });

        // initialising the orientation popup will be done once side option is selected



        // hotcorners
        //  lt
        lt = new HotCorner(this, "lt", x,gridSize,borderwidth);
        lt.setBounds(borderwidth, borderwidth, 10, 10);
        add(lt);
        rb = new HotCorner(this, "rb", x,gridSize,borderwidth);
        rb.setBounds(getWidth() - 10 - borderwidth, getHeight() - 10 - borderwidth, 10, 10);
        add(rb);

        add(furniture_canvas);
        setComponentZOrder(furniture_canvas,0);
        setComponentZOrder(lt,1);
        setComponentZOrder(rb,1);

        // Mouse adapter functionality
        // pressed
        MouseAdapter mouse = new MouseAdapter() {

            boolean connected = false;
            int X;
            int Y;
            int NewX;
            int newY;
            int initialX;
            int initialY;

            int connectionX;
            int connectionY;
            // get initial room coordinates and register mouse press coords

            public void mousePressed(MouseEvent e) {
                X = e.getX();
                Y = e.getY();
                initialX = getX(); // for overlap check
                initialY = getY(); // for overlap check
                //System.out.println("mouse pressed on"+e.getComponent());
                //canvas.update_context_manager((Room)e.getComponent());
                System.out.println(e.getComponent().getBackground());
            }

            public void mouseDragged(MouseEvent e) {

                // set location
                Nearby nearbyroom = isroomnearby(); // check if a room is nearby or not

                // if there is a room nearby
                if (nearbyroom != null) {
                    // if the room is NOT connected to another room
                    if (!connected) {
                        switch(nearbyroom.side){
                            case "l":
                                NewX=nearbyroom.room.getX()+nearbyroom.room.getWidth();
                                // change this
                                newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                //snap = snap((2*getY()+getHeight())/2,nearbyroom.room.getY(),nearbyroom.room.getY()+nearbyroom.room.getHeight());
                                //if (snap!=-1){
                                //    newY = snap;}
                                connected = true;
                                connectionX = e.getX();
                                break;

                            case "t":
                                newY = nearbyroom.room.getY()+nearbyroom.room.getHeight();
                                // change this
                                NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                //snap = snap((2*getX()+getWidth())/2,nearbyroom.room.getX(),nearbyroom.room.getX()+nearbyroom.room.getWidth());
                                //if (snap!=-1){
                                //    NewX = snap;}
                                connected = true;
                                connectionY = e.getY();
                                break;
                            case "r":
                                NewX = nearbyroom.room.getX()-getWidth();
                                // change this
                                newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                //snap = snap((2*getY()+getHeight())/2,nearbyroom.room.getY(),nearbyroom.room.getY()+nearbyroom.room.getHeight());
                                //if (snap!=-1){
                                //    newY = snap;}
                                connected = true;
                                connectionX = e.getX();
                                break;
                            case "b":
                                newY = nearbyroom.room.getY()-getHeight();
                                // change this
                                NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                //snap = snap((2*getX()+getWidth())/2,nearbyroom.room.getX(),nearbyroom.room.getX()+nearbyroom.room.getWidth());
                                //if (snap!=-1){
                                //    NewX = snap;}
                                connected = true;
                                connectionY = e.getY();
                                break;
                        }
                    }
                    // the room is connected to another room
                    else {
                        String side = areconnected(nearbyroom.room);
                        if (side == null) { // making sure there is room connected at some side (tb or s) top_bottom  or side
                            System.out.println("\n\n\n\nwhat's happening here");
                            connected = false; // we need to figure out why this line is holding back a weird bug
                        } else {
                            // checks to see that the mouse has moved enough to indicate that the user doesn't want to snap.
                            // in which case we disengage the snap function
                            switch (side) {
                                case "l":
                                    if (e.getX() - connectionX > 20 || e.getX() - connectionX < -20) {
                                        // not connected anymore
                                        connected = false;
                                        // regular coords mechanism
                                        // find new x coord wrt grid size
                                        NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                        // find new y coord wrt grid size
                                        newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                    } else {
                                        newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                    }
                                    break;
                                case "r":
                                    if (e.getX() - connectionX > 20 || e.getX() - connectionX < -20) {
                                        // not connected anymore
                                        connected = false;
                                        // regular coords mechanism
                                        // find new x coord wrt grid size
                                        NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                        // find new y coord wrt grid size
                                        newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                    } else {
                                        newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                    }
                                    break;
                                case "t":
                                    if (e.getY() - connectionY > 20 || e.getY() - connectionY < -20) {
                                        // not connected anymore
                                        connected = false;
                                        // regular coords mechanism
                                        // find new x coord wrt grid size
                                        NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                        // find new y coord wrt grid size
                                        newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                    } else {
                                        NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                    }
                                    break;
                                case "b":
                                    if (e.getY() - connectionY > 20 || e.getY() - connectionY < -20) {
                                        // not connected anymore
                                        connected = false;
                                        // regular coords mechanism
                                        // find new x coord wrt grid size
                                        NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                        // find new y coord wrt grid size
                                        newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                                    } else {
                                        NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                                    }
                                    break;

                            }

                        }
                    }

                    //System.out.println("nearbyroom,"+nearbyroom.side+" "+e.getX()+" "+e.getY());

                }
                // there is no room nearby
                else{
                    // find new x coord wrt grid size
                    NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                    // find new y coord wrt grid size
                    newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;

                }


                setLocation(NewX, newY);
                //canvas.update_context_manager((Room)e.getComponent());
                //int moveX = NewX - getX();
                //int moveY = newY-getY();

                // makes sure the object being moved around is on top. (z axis wise)
                canvas.setComponentZOrder(e.getComponent(), 0);
            }

            @Override
            // overlap check goes here
            public void mouseReleased(MouseEvent e) {
                if (room_overlap()) {
                    System.out.println("room overlap");
                    setLocation(initialX, initialY);
                    Canvas.showDialog(canvas.frame,"ROOM OVERLAP!");
                    //canvas.update_context_manager((Room)e.getComponent());
                } else {
                    //canvas.update_context_manager((Room)e.getComponent());
                    System.out.println("room not overlap");
                }
            }

            // rightclick to get options pane // this or have a panel on the left side of the screen (discuss with hanes and varun)
            public void mouseClicked(MouseEvent e) {
                // check for right click
                if (SwingUtilities.isRightMouseButton(e)) {
                    popup.show(canvas, e.getComponent().getX(), e.getComponent().getY());


                    //System.out.println("RIGHT CLICKKKK");
                }

            }
        };
        addMouseListener(mouse); // to register clicks
        addMouseMotionListener(mouse); // to register drag
    }
    // rotate room - toDO incomplete
    public void rotate() {
        setBounds(getX(), getY(), getHeight(), getWidth());
        rb.setLocation(getWidth() - 10 - borderwidth, getHeight() - 10 - borderwidth);
        if(room_overlap()){
            canvas.showDialog(canvas.frame,"ROOM OVERLAP!");
            setBounds(getX(),getY(),getHeight(),getWidth());
            rb.setLocation(getWidth() - 10 - borderwidth, getHeight() - 10 - borderwidth);
        }
        //canvas.update_context_manager(this);


    }

    // room overlap checker
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
                System.out.println(getX()+","+getY()+":"+(getX()+getWidth())+","+(getY()+getHeight()));
                System.out.println(room.getX()+","+room.getY()+":"+(room.getX()+room.getWidth())+","+(room.getY()+room.getHeight()));
            }
        }

        return overlap;
    }

    // generic overlap
    public boolean overlap(int ltx1, int lty1, int rbx1, int rby1,
                           int ltx2, int lty2, int rbx2, int rby2) {
        /*
        boolean overlap = false;
        if (ltx1 < rbx2 && rbx1 > ltx2) {
            if (rby1 > lty2 && lty1 < rby2) {
                overlap = true;
            }

        }
        return overlap;

         */
        return (ltx1 < rbx2 && rbx1 > ltx2 && lty1 < rby2 && rby1 > lty2);
    }

    public String areconnected(Room room){
        // bottom                               // top
        if(room.getY()+room.getHeight()==getY() ){
            //System.out.println("tb match;");
            return "b";

        }
        if(getY()+getHeight()==room.getY()){
            return "t";
        }
        // left                            // right
        if(room.getX()==getX()+getWidth()) {
            //System.out.println("s match;"+getY());
            return "l";
        }
        if(getX()==room.getX()+room.getWidth()){
            return "r";
        }

        return null;
    }
    // check for nearby rooms
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


    }

    public boolean intersection(int lt1,int lt2, int rb1, int rb2){
        // here the variables are called lt1, rb1 not to indicate said points, but to reference that
        // variable should contain the coords of the leftmost OR the topmost coordinate of the rect
        int upper=Math.max(lt1, lt2);
        int lower=Math.min(rb1, rb2);
        return lower-upper> 0;

    }
    // snap to nearby panels
    public int snap(int center, int lt, int rb){
        if (center-lt>rb-center && rb-center<=20){

            return rb;
        }else if (center-lt<rb-center && rb-center<=20){
            return lt;
        }else if(2*center-(lt+rb)<10 &&2*center-(lt+rb)>-10){
            return (lt+rb)/2;
        }
        return -1;

    }
    // check for nearby panels
    static class Nearby {
        String side;
        Room room;

        public Nearby(String side, Room room) {
            this.side = side;
            this.room = room;
        }
    }

}

class HotCorner extends JPanel {
    int gridSize;
    int borderwidth;

    public HotCorner(Room owner, String corner, Color x, int gridSize, int borderwidth) {
        setBackground(Color.PINK); // TODO: DELETE THIS LATER
        this.gridSize = gridSize;
        this.borderwidth= borderwidth;
        // most of the work is done here:
        MouseAdapter hotcornermouse = new MouseAdapter() {
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
                            // changing location of origin
                            owner.setLocation(owner.getX() + e.getX() - X, owner.getY() + e.getY() - Y);
                            // changing location of rb hotcorner so it stays on the rb corner
                            // java is cool for doing this - i don't know what the fuck this is, I'm using aggregation to
                            // call an instance of this class and set it's location ;/ mind fuck
                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);

                        } else if (newWidth > minsize) {

                            owner.setLocation(owner.getX() + e.getX() - X, owner.getY() + (owner.getHeight() - minsize));
                            owner.setSize(newWidth, minsize);

                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);

                        } else if (newHeight > minsize) {
                            owner.setLocation(owner.getX() + (owner.getWidth() - minsize), owner.getY() + e.getY() - Y);
                            owner.setSize(minsize, newHeight);

                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
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
                        newYcoord = Math.floorDiv(owner.getY(), gridSize) * gridSize;

                        if (newYcoord < owner.getY()) {
                            newYcoord += 10;
                        }

                        newXcoord = Math.floorDiv(owner.getX(), gridSize) * gridSize;
                        if (newXcoord < owner.getX()) {
                            newXcoord += 10;
                        }

                        newWidth = owner.getWidth() + (owner.getX() - newXcoord);
                        newHeight = owner.getHeight() + (owner.getY() - newYcoord);
                        // TODO :OVERLAP CHECK
                        if (newWidth > 20 && newHeight > 20) {
                            owner.setSize(newWidth, newHeight);
                            owner.setLocation(newXcoord, newYcoord);
                            // setting rb to the right bottom corner yet again- yes it's pretty annoying,
                            // and yes it's necessary because we have to change the width of the room when we're pulling lt
                            owner.rb.setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                        }

                        break;
                    case "rb":
                        newWidth = Math.floorDiv(owner.getWidth(), gridSize) * gridSize;
                        newHeight = Math.floorDiv(owner.getHeight(), gridSize) * gridSize;
                        owner.setSize(newWidth, newHeight);
                        setLocation(owner.getWidth() - 10 - borderwidth, owner.getHeight() - 10 - borderwidth);
                }


                if(owner.room_overlap()){
                    owner.setLocation(intialx, intialy);
                    owner.setSize(initialwid,initiallen);
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

