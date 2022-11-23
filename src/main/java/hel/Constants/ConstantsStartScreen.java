package hel.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsStartScreen {
    // Background Constants
    public static Color backgroundColor = Color.rgb(255,166,0);

    // Title Constants
    public static final String title = "        HEL";
    public static final Vec2d titlePosition = new Vec2d(300, 160);
    public static final Color titleColor = Color.rgb(61, 15,26);
    public static final FontWrapper titleFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,64);

    // Specified Button Constants
    public static final String startButtonText = "Start!";
    public static final Vec2d startButtonPosition =  new Vec2d(370, 300);
    public static final Vec2d startButtonTextPosition = new Vec2d(76, 20);

    // All Button Constants
    public static final Vec2d buttonSize = new Vec2d(220, 60);
    public static final Color buttonColor = Color.rgb(255, 229, 229);
    public static final Color buttonHoverColor = Color.rgb(207,196,184);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final Color buttonTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper buttonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
}
