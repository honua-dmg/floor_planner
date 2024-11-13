package rooms.src;

import main.src.Canvas;
import main.src.Room;

import java.awt.*;

public class Dining extends Room {
    // furniture list here
    public Dining(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(231,199,31), canvas, gridSize, borderwidth);
    }
}