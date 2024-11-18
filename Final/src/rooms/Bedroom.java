package rooms;

import Main.Canvas;
import Main.Room;


import java.awt.*;

/*
* Bedroom: green,
* Bathroom: blue,
* Kitchen:red,
* Drawing room/dining space: yellow/orange,
*  walls: black,
*  outside: light gray.*/
public class Bedroom extends Room{
    // furniture list here
    public Bedroom(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(103,189,170), canvas, gridSize, borderwidth);
        room_type = "bedroom";
    }
}
