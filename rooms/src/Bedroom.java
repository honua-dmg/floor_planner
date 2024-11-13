package rooms.src;
import main.src.Room;
import main.src.Canvas;
import java.awt.Color;
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
    }
}
