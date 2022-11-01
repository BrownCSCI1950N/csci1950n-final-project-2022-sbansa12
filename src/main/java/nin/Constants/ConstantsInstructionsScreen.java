package nin.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsInstructionsScreen {
    // Background Constants
    public static Color instructionsScreenBackgroundColor = ConstantsStartScreen.startScreenBackgroundColor;

    // Title Constants
    public static final String instructionsScreenTitle = "Instructions!";
    public static final Vec2d instructionsScreenTitlePosition = new Vec2d(320, 140);
    public static final Color instructionsScreenTitleColor = ConstantsStartScreen.startScreenTitleColor;
    public static final FontWrapper instructionsScreenTitleFont = ConstantsStartScreen.startScreenTitleFont;

    // Instructions Constants
    public static final Vec2d instructionsScreenInstructionsPosition = new Vec2d(270, 220);
    public static final String instructionsScreenInstructions = "               Goal: Get to The Door!\n"+
            "               Movement: Arrow Keys\n" +
            "                   Restart a Level: K\n" +
            "       Go Back To Selection Screen: Q\n";
    public static final FontWrapper instructionsScreenInstructionsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);

    // Button Constants
    public static final String instructionsScreenButtonText = "     X";
    public static final Vec2d instructionsScreenButtonPosition =  ConstantsStartScreen.startScreenButtonPosition;
    public static final Vec2d instructionsScreenButtonSize = ConstantsStartScreen.startScreenButtonSize;
    public static final Color instructionsScreenButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color instructionsScreenButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d instructionsScreenButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d instructionsScreenButtonTextPosition = ConstantsStartScreen.startScreenButtonTextPosition;
    public static final Color instructionsScreenButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper instructionsScreenButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;
}
