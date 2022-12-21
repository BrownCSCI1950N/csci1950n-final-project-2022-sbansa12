package helpme.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsInstructionsScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "Instructions";
    public static final Vec2d titlePosition = new Vec2d(330, 130);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Credits Constants
    public static final Vec2d instructionsPosition = new Vec2d(130, 220);
    public static final String instructions = "Have Fire and Ice work together to hit the button and reach the exits.\n" +
                                              "               (DEFAULT) Use WASD and IJKL to move each player\n" +
                                              "                          Press 1 to go back to level selection\n" +
                                              "                                    Press 2 to reset the level.";
    public static final Color instructionsColor = titleColor;
    public static final FontWrapper instructionsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);

    // Button Constants
    public static final String buttonText = "next";
    public static final Vec2d buttonPosition =  new Vec2d(370, 360);
    public static final Vec2d buttonTextPosition = new Vec2d(76, 20);

    // All Button Constants
    public static final Vec2d buttonSize = ConstantsStartScreen.buttonSize;
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;
}
