package nin.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsWinScreen {
    // Background Constants
    public static Color winScreenBackgroundColor = ConstantsStartScreen.startScreenBackgroundColor;

    // Title Constants
    public static final String winScreenTitle = " You Won!";
    public static final Vec2d winScreenTitlePosition = new Vec2d(320, 140);
    public static final Color winScreenTitleColor = ConstantsStartScreen.startScreenTitleColor;
    public static final FontWrapper winScreenTitleFont = ConstantsStartScreen.startScreenTitleFont;

    // Instructions Constants
    public static final Vec2d winScreenCreditsPosition = new Vec2d(270, 220);
    public static final String winScreenCredits = "                 Code: Sahil Bansal\n" +
                                                  "                   Art: Emily Zhang";
    public static final FontWrapper winScreenCreditsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);

    // Button Constants
    public static final String winScreenButtonText = "restart";
    public static final Vec2d winScreenButtonPosition =  ConstantsStartScreen.startScreenButtonPosition;
    public static final Vec2d winScreenButtonSize = ConstantsStartScreen.startScreenButtonSize;
    public static final Color winScreenButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color winScreenButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d winScreenButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d winScreenButtonTextPosition = ConstantsStartScreen.startScreenButtonTextPosition;
    public static final Color winScreenButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper winScreenButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;
}
