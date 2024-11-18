package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HotCorner extends JPanel {
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