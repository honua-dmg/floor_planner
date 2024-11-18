package furnitures;
import Main.HotCorner;
import Main.Move;
import Main.Room;
import Main.Canvas;
import openings.Opening;
import openings.Opening_MouseAdapter;

import javax.swing.*;
import java.awt.*;


public class Furniture extends Room{

    public Furniture(Canvas furnitureclass){
        super(Color.WHITE,furnitureclass,furnitureclass.gridsize,furnitureclass.borderwidth);






            //Basic setup

            setSize(20, 20);
            setPreferredSize(new Dimension(20, 20));
            setBackground(Color.WHITE);
            setVisible(true);






            // borders
            this.setBorder(null);


        // we don't want to resize furniture elements.

    }

}
