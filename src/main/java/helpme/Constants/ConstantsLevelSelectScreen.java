package helpme.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.math.BigDecimal;

public class ConstantsLevelSelectScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "Level Selection";
    public static final Vec2d titlePosition = new Vec2d(300, 140);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Back Button Constants
    public static final String backButtonText = "←";
    public static final Vec2d backButtonPosition = new Vec2d(850,50);
    public static final Vec2d backButtonSize = new Vec2d(50,35);
    public static final Vec2d backButtonTextPosition = new Vec2d(10, 8);

    // Level Button Constants
    public static final Vec2d buttonStartingPosition = new Vec2d(300,200);
    public static final Vec2d buttonPadding = new Vec2d(30,30);
    public static final Vec2d levelButtonSize = new Vec2d(60,60);
    public static final Color levelButtonlockedColor = Color.rgb(0,0,0);
    public static final Vec2d levelButtonTextPosition = new Vec2d(12, 19);

    // Settings Button Constants
    public static final String settingsButtonText = "⛭";
    public static final Vec2d settingsButtonPosition = new Vec2d(870,460);
    public static final Vec2d settingsButtonSize = new Vec2d(50,50);
    public static final Vec2d settingsButtonTextPosition = new Vec2d(9, 13);

    // General Button Constants
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Vec2d buttonTextPosition = new Vec2d(15, 14);
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;
}
