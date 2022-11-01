package nin.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsStartScreen {
    // Background Constants
    public static Color startScreenBackgroundColor = Color.rgb(196,196,196);

    // Title Constants
    public static final String startScreenTitle = "       NIN";
    public static final Vec2d startScreenTitlePosition = new Vec2d(300, 190);
    public static final Color startScreenTitleColor = Color.rgb(61, 15,26);
    public static final FontWrapper startScreenTitleFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,64);

    // Button Constants
    public static final String startScreenButtonText = "  start";
    public static final Vec2d startScreenButtonPosition =  new Vec2d(415, 360);
    public static final Vec2d startScreenButtonSize = new Vec2d(120, 60);
    public static final Color startScreenButtonColor = Color.rgb(255, 229, 229);
    public static final Color startScreenButtonHoverColor = Color.rgb(207,196,184);
    public static final Vec2d startScreenButtonArcSize = new Vec2d(10,10);
    public static final Vec2d startScreenButtonTextPosition = new Vec2d(14, 20);
    public static final Color startScreenButtonTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper startScreenButtonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
}

