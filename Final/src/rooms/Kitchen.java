package rooms;

import Main.Canvas;
import Main.Room;

import java.awt.*;

public class Kitchen extends Room {
    // furniture list here
    public Kitchen(Canvas canvas, int gridSize, int borderwidth) {
        super(new Color(193,90,99), canvas, gridSize, borderwidth);
        room_type = "Kitchen";
    }
}
