package Main;

import java.util.ArrayList;

public class _ConnectedRooms{
    ArrayList<Room> left = new ArrayList<Room>();
    ArrayList<Room> right = new ArrayList<Room>();
    ArrayList<Room> top = new ArrayList<Room>();
    ArrayList<Room> bottom = new ArrayList<Room>();
    public void add(Room room,String side){
        switch(side){
            case "l":
                left.add(room);
                break;
            case "r":
                right.add(room);
                break;
            case "t":
                top.add(room);
                break;
            case "b":
                bottom.add(room);
                break;
        }
    }

}