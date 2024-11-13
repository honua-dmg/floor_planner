package rooms.src;

import main.src.Canvas;
import main.src.Room;

import java.awt.*;

public class Bathroom extends Room {
    // furniture list here
    public Bathroom(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(8,133,161), canvas, gridSize, borderwidth);
    }
}