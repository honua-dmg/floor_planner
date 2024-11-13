package rooms.src;

import main.src.Canvas;
import main.src.Room;

import java.awt.*;

public class Drawing extends Room {
    // furniture list here
    public Drawing(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(224,163,46), canvas, gridSize, borderwidth);
    }
}