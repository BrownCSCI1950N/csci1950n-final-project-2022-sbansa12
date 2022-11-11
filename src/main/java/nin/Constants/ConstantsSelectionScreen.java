package nin.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConstantsSelectionScreen {
    // Background Constants
    public static Color selectionScreenBackgroundColor = ConstantsStartScreen.startScreenBackgroundColor;

    // Title Constants
    public static final String selectionScreenTitle = "Level Selection!";
    public static final Vec2d selectionScreenTitlePosition = new Vec2d(280, 140);
    public static final Color selectionScreenTitleColor = ConstantsStartScreen.startScreenTitleColor;
    public static final FontWrapper selectionScreenTitleFont = ConstantsStartScreen.startScreenTitleFont;

    // Button Constants
    public static final Vec2d selectionScreenButtonSize = new Vec2d(50,50);
    public static final Color levelIncompleteButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color levelCompleteButtonColor = Color.rgb(150,140,140);
    public static final Color selectionScreenButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d selectionScreenButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d selectionScreenButtonTextPosition = new Vec2d(8, 14);
    public static final Color selectionScreenButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper selectionScreenButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;

    // Level Selection Buttons Specific
    public static final String[][] levels = new String[][]{
            {"00", "01", "02", "03"},
            {"04", "05", "06", "07"},
            {"08", "09", "10", "11"},
            {"12", "13"}
    };
    public static final Map<String, Boolean> levelComplete = new HashMap<>(){{
        for (String[] level : levels) {
            for (String s : level) {
                put(s, false);
            }
        }
    }};
    public static final Vec2d buttonStartingPosition = new Vec2d(290,200);
    public static final Vec2d buttonPadding = new Vec2d(60,30);

    // Back Button
    public static final String selectionScreenBackButtonText = "←";
    public static final Vec2d selectionScreenBackButtonPosition = new Vec2d(850,50);
    public static final Vec2d selectionScreenBackButtonSize = new Vec2d(50,35);
    public static final Color selectionScreenBackButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color selectionScreenBackButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d selectionScreenBackButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d selectionScreenBackButtonTextPosition = new Vec2d(10, 10);
    public static final Color selectionScreenBackButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper selectionScreenBackButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;

    // Save Button
    public static final String selectionScreenSaveButtonText = "save";
    public static final Vec2d selectionScreenSaveButtonPosition = new Vec2d(850,470);
    public static final Vec2d selectionScreenSaveButtonSize = new Vec2d(80,35);
    public static final Color selectionScreenSaveButtonColor = ConstantsStartScreen.startScreenButtonColor;
    public static final Color selectionScreenSaveButtonHoverColor = ConstantsStartScreen.startScreenButtonHoverColor;
    public static final Vec2d selectionScreenSaveButtonArcSize = ConstantsStartScreen.startScreenButtonArcSize;
    public static final Vec2d selectionScreenSaveButtonTextPosition = new Vec2d(10, 10);
    public static final Color selectionScreenSaveButtonTextColor = ConstantsStartScreen.startScreenButtonTextColor;
    public static final FontWrapper selectionScreenSaveButtonTextFont = ConstantsStartScreen.startScreenButtonTextFont;

    // Save Popup
    public static final Vec2d selectionScreenSaveMessagePosition = new Vec2d(850,460);
    public static final Color selectionScreenSaveMessageColor = Color.rgb(255,255,255);
    public static final FontWrapper selectionScreenSaveMessageFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,32);
    public static final BigDecimal selectionScreenSaveMessageTime = new BigDecimal("1600000000");
}
