package rooms;

import Main.Canvas;
import Main.Room;

import java.awt.*;

public class Drawing extends Room {
    // furniture list here
    public Drawing(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(224,163,46), canvas, gridSize, borderwidth);
    }
}