package nin.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class ConstantsSaveLoadScreen {
    // Background Constants
    public static Color saveLoadScreenBackgroundColor = ConstantsStartScreen.startScreenBackgroundColor;

    // Title Constants
    public static final String saveLoadScreenTitle = "New/Load Game!";
    public static final Vec2d saveLoadScreenTitlePosition = new Vec2d(240, 140);
    public static final Color saveLoadScreenTitleColor = ConstantsStartScreen.startScreenTitleColor;
    public static final FontWrapper saveLoadScreenTitleFont = ConstantsStartScreen.startScreenTitleFont;

    // Save Load Buttons Specific
    public static final Integer numberOfButtons = 4;
    public static final Vec2d buttonStartingPosition = new Vec2d(360,200);
    public static final Vec2d buttonPadding = new Vec2d(0,30);

    // Button Constants
    public static final Vec2d saveLoadScreenButtonSize = new Vec2d(200,50);
    public static final Color saveLoadScreenButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color saveLoadScreenButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d saveLoadScreenButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d saveLoadScreenButtonTextPosition = new Vec2d(15, 14);
    public static final Color saveLoadScreenButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper saveLoadScreenButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;

    // Delete Button Constants
    public static final Vec2d saveLoadScreenDeleteButtonRelativePosition = new Vec2d(220, 0);
    public static final Vec2d saveLoadScreenDeleteButtonSize = new Vec2d(50,50);
    public static final Color saveLoadScreenDeleteButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color saveLoadScreenDeleteButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d saveLoadScreenDeleteButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d saveLoadScreenDeleteButtonTextPosition = new Vec2d(18, 14);
    public static final Color saveLoadScreenDeleteButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper saveLoadScreenDeleteButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;

    // Back Button
    public static final String saveLoadScreenBackButtonText = "←";
    public static final Vec2d saveLoadScreenBackButtonPosition = new Vec2d(850,50);
    public static final Vec2d saveLoadScreenBackButtonSize = new Vec2d(50,35);
    public static final Color saveLoadScreenBackButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color saveLoadScreenBackButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d saveLoadScreenBackButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d saveLoadScreenBackButtonTextPosition = new Vec2d(10, 10);
    public static final Color saveLoadScreenBackButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper saveLoadScreenBackButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;
}
