package alc;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class Constants {
    // Start and End Screen Aesthetics
    public static Color backgroundColor = Color.rgb(255, 204, 153);
    public static final Vec2d startScreenTitlePosition = new Vec2d(300, 190);
    public static final Vec2d endScreenTitlePosition = new Vec2d(300, 190);
    public static final String startScreenTitle = "ALCHEMY";
    public static final String endScreenTitle = "Game Completed!";
    public static final Color titleColor = Color.rgb(255, 229, 229);
    public static final FontWrapper titleFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null,64);
    public static final Vec2d buttonPosition =  new Vec2d(415, 360);
    public static final Vec2d buttonSize = new Vec2d(120, 60);
    public static final Color buttonColor = Color.rgb(255, 229, 229);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final String startScreenButtonText = "  start";
    public static final String endScreenButtonText = "restart";
    public static final Vec2d buttonTextPosition = new Vec2d(14, 20);
    public static final Color buttonTextColor = Color.rgb(248, 110,110);
    public static final FontWrapper buttonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Color buttonHoverColor = Color.rgb(255,255, 255);
}
