package helpme.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class ConstantsSettingsScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "Settings";
    public static final Vec2d titlePosition = new Vec2d(360, 120);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Back Button Constants
    public static final String backButtonText = "←";
    public static final Vec2d backButtonPosition = new Vec2d(850,50);
    public static final Vec2d backButtonSize = new Vec2d(50,35);
    public static final Vec2d backButtonTextPosition = new Vec2d(10, 8);

    // Controls Button Constants
    public static final String controlsButtonText = "controls";
    public static final Vec2d controlsButtonPosition = new Vec2d(380, 280);
    public static final Vec2d controlsButtonSize = new Vec2d(170, 50);
    public static final Vec2d controlsButtonTextPosition = new Vec2d(26, 15);

    // General Button Constants
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Vec2d buttonTextPosition = new Vec2d(15, 14);
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;
}
