package rooms;

import Main.Canvas;
import Main.Room;

import java.awt.*;

public class Bathroom extends Room {
    // furniture list here
    public Bathroom(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(8,133,161), canvas, gridSize, borderwidth);
        room_type = "bathroom";
    }
}