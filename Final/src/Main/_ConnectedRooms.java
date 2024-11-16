package Main;

import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class _ConnectedRooms{
    public ArrayList<Room> left = new ArrayList<Room>();
    public ArrayList<Room> right = new ArrayList<Room>();
    public ArrayList<Room> top = new ArrayList<Room>();
    public ArrayList<Room> bottom = new ArrayList<Room>();
    public void add(Room room,String side){
        if (side==null){
            return;
        }
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
    public void sort(){
        left.sort(Comparator.comparingInt(Room::getX));
        right.sort(Comparator.comparingInt(Room::getX));
        top.sort(Comparator.comparingInt(Room::getX));
        bottom.sort(Comparator.comparingInt(Room::getX));
    }
}