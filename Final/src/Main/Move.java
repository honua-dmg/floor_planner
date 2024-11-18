package Main;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Move extends MouseAdapter {

boolean connected = false;
int X;
int Y;
int NewX;
int newY;
int initialX;
int initialY;
Room room;
int connectionX;
int connectionY;
String side;
// get initial room coordinates and register mouse press coords
    public Move(Room room){
        this.room = room;
    }

public void mousePressed(MouseEvent e) {
    X = e.getX();
    Y = e.getY();
    initialX = room.getX(); // for overlap check
    initialY = room.getY(); // for overlap check
    //System.out.println("mouse pressed on"+e.getComponent());
    //canvas.update_context_manager((Room)e.getComponent());
    //System.out.println(e.getComponent().getBackground());
}

public void mouseDragged(MouseEvent e) {
    // set location
    Room.Nearby nearbyroom = room.isroomnearby(); // check if a room is nearby or not

    // if there is a room nearby
    if (nearbyroom != null) {
        side = nearbyroom.side;
        // if the room is NOT connected to another room
        if (!connected) {
            switch(side){
                case "l":
                    NewX=nearbyroom.room.getX()+nearbyroom.room.getWidth();
                    // change this
                    //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                    newY = room.get_grid_coords(room.getY()+e.getY()-Y);
                    //snap = snap((2*getY()+getHeight())/2,nearbyroom.room.getY(),nearbyroom.room.getY()+nearbyroom.room.getHeight());
                    //if (snap!=-1){
                    //    newY = snap;}
                    connected = true;
                    connectionX = e.getX();
                    break;

                case "t":
                    newY = nearbyroom.room.getY()+nearbyroom.room.getHeight();
                    // change this
                    //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                    NewX = room.get_grid_coords(room.getX()+e.getX()-X);
                    //snap = snap((2*getX()+getWidth())/2,nearbyroom.room.getX(),nearbyroom.room.getX()+nearbyroom.room.getWidth());
                    //if (snap!=-1){
                    //    NewX = snap;}
                    connected = true;
                    connectionY = e.getY();
                    break;
                case "r":
                    NewX = nearbyroom.room.getX()-room.getWidth();
                    // change this
                    //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                    newY = room.get_grid_coords(room.getY()+e.getY()-Y);
                    //snap = snap((2*getY()+getHeight())/2,nearbyroom.room.getY(),nearbyroom.room.getY()+nearbyroom.room.getHeight());
                    //if (snap!=-1){
                    //    newY = snap;}
                    connected = true;
                    connectionX = e.getX();
                    break;
                case "b":
                    newY = nearbyroom.room.getY()-room.getHeight();
                    // change this
                    //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                    NewX = room.get_grid_coords(room.getX()+e.getX()-X);
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
            //String side = room.areconnected(nearbyroom.room);
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
                            //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                            NewX = room.get_grid_coords(room.getX() + e.getX() - X);
                            // find new y coord wrt grid size
                            //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                            newY = room.get_grid_coords(room.getY() + e.getY() - Y);
                        } else {
                            //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                            newY = room.get_grid_coords(room.getY() + e.getY() - Y);
                        }
                        break;
                    case "r":
                        if (e.getX() - connectionX > 20 || e.getX() - connectionX < -20) {
                            // not connected anymore
                            connected = false;
                            // regular coords mechanism
                            // find new x coord wrt grid size
                            //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                            NewX = room.get_grid_coords(room.getX() + e.getX() - X);
                            // find new y coord wrt grid size
                            //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                            newY = room.get_grid_coords(room.getY() + e.getY() - Y);
                        } else {
                            //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                            newY = room.get_grid_coords(room.getY() + e.getY() - Y);
                        }
                        break;
                    case "t":
                        if (e.getY() - connectionY > 20 || e.getY() - connectionY < -20) {
                            // not connected anymore
                            connected = false;
                            // regular coords mechanism
                            // find new x coord wrt grid size
                            //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                            NewX = room.get_grid_coords(room.getX() + e.getX() - X);
                            // find new y coord wrt grid size
                            //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                            newY = room.get_grid_coords(room.getY() + e.getY() - Y);
                        } else {
                            //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                            NewX = room.get_grid_coords(room.getX() + e.getX() - X);
                        }
                        break;
                    case "b":
                        if (e.getY() - connectionY > 20 || e.getY() - connectionY < -20) {
                            // not connected anymore
                            connected = false;
                            // regular coords mechanism
                            // find new x coord wrt grid size
                            //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                            NewX = room.get_grid_coords(room.getX() + e.getX() - X);
                            // find new y coord wrt grid size
                            //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
                            newY = room.get_grid_coords(room.getY() + e.getY() - Y);
                        } else {
                            //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
                            NewX = room.get_grid_coords(room.getX() + e.getX() - X);
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
        //NewX = Math.floorDiv(getX() + e.getX() - X, gridSize) * gridSize;
        NewX = room.get_grid_coords(room.getX() + e.getX() - X);
        // find new y coord wrt grid size
        //newY = Math.floorDiv(getY() + e.getY() - Y, gridSize) * gridSize;
        newY = room.get_grid_coords(room.getY() + e.getY() - Y);

    }


    room.setLocation(NewX, newY);
    if(NewX!=initialX|| newY!=initialY){
        for(int k=room.openings.size()-1;k>=0;k--){
            room.openings.get(k).remove();
        }}



    //canvas.update_context_manager((Room)e.getComponent());
    //int moveX = NewX - getX();
    //int moveY = newY-getY();

    // makes sure the object being moved around is on top. (z axis wise)
    room.canvas.setComponentZOrder(e.getComponent(), 0);
}

@Override
// overlap check goes here
public void mouseReleased(MouseEvent e) {
    if (room.room_overlap()) {
        //System.out.println("room overlap");
        room.setLocation(initialX, initialY);
        Canvas.showDialog(room.canvas.frame,"ROOM OVERLAP!");
        //canvas.update_context_manager((Room)e.getComponent());
    } else {
        //canvas.update_context_manager((Room)e.getComponent());
        //System.out.println("room not overlap");
    }
}

// rightclick to get options pane // this or have a panel on the left side of the screen (discuss with hanes and varun)
public void mouseClicked(MouseEvent e) {
    // check for right click

    if (SwingUtilities.isRightMouseButton(e)) {
        room.popup.show(room.canvas, e.getComponent().getX(), e.getComponent().getY());


        //System.out.println("RIGHT CLICKKKK");
    }

}
        };
