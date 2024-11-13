package rooms.src;

import main.src.Canvas;
import main.src.Room;

import java.awt.*;

public class Kitchen extends Room {
    // furniture list here
    public Kitchen(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(193,90,99), canvas, gridSize, borderwidth);
    }
}
