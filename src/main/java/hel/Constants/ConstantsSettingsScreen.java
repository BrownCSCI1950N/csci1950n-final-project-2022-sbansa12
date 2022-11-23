package hel.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsSettingsScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "Key Bindings";
    public static final Vec2d titlePosition = new Vec2d(300, 120);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Back Button Constants
    public static final String backButtonText = "←";
    public static final Vec2d backButtonPosition = new Vec2d(850,50);
    public static final Vec2d backButtonSize = new Vec2d(50,35);
    public static final Color backButtonColor = nin.Constants.ConstantsStartScreen.startScreenButtonColor;
    public static final Color backButtonHoverColor = nin.Constants.ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d backButtonArcSize = nin.Constants.ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d backButtonTextPosition = new Vec2d(10, 10);
    public static final Color backButtonTextColor = nin.Constants.ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper backButtonTextFont = nin.Constants.ConstantsStartScreen.startScreenButtonTextFont;
}
