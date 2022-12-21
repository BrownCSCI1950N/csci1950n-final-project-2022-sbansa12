package helpme.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsWinScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "    You Won!";
    public static final Vec2d titlePosition = new Vec2d(300, 160);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Credits Constants
    public static final Vec2d creditsPosition = new Vec2d(275, 220);
    public static final String credits = "                 Code: Sahil Bansal\n" +
                                         "                   Art: Emily Zhang";
    public static final Color creditsColor = titleColor;
    public static final FontWrapper creditsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);

    // Button Constants
    public static final String buttonText = "restart";
    public static final Vec2d buttonPosition =  new Vec2d(370, 300);
    public static final Vec2d buttonTextPosition = new Vec2d(70, 20);

    // All Button Constants
    public static final Vec2d buttonSize = ConstantsStartScreen.buttonSize;
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;
}
