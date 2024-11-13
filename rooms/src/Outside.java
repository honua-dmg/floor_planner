package rooms.src;

import main.src.Canvas;
import main.src.Room;

import java.awt.*;

public class Outside extends Room {
    // furniture list here
    public Outside(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(122,122,121), canvas, gridSize, borderwidth);
    }
}