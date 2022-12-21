package helpme.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsGameScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Viewport
    public static final double scale = 1; // px/unit
    public static final Vec2d startingViewportGameCoordinates = new Vec2d(487,275);
    public static final Vec2d viewportScreenPosition = new Vec2d(0,0);
    public static final Vec2d viewportScreenSize = new Vec2d(960, 540);

    // Popups

    public static final String popUpTextLevelComplete = "                     Level Complete!";
    public static final String popUpTextRestartLevel = "    You Died. Close Popup To Restart";


    // General PopUp Constants
    public static final Color popUpOverlayColor = Color.rgb(141,139,139 ,0.8);
    public static final Vec2d popUpPanelPosition = new Vec2d(100,80);
    public static final Vec2d popUpPanelSize = new Vec2d(760,380);
    public static final Color popUpPanelColor = Color.rgb(243,236,224);
    public static final Vec2d popUpPanelArcSize = new Vec2d(10,10);
    public static final Vec2d popUpTextPosition = new Vec2d(195,280);
    public static final Color popUpTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper popUpTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Vec2d popUpButtonPosition = new Vec2d(790, 110);
    public static final Vec2d popUpButtonSize = new Vec2d(40,40);
    public static final Vec2d popUpButtonTextPosition = new Vec2d(12, 10);
    public static final String popUpButtonText = "X";

    // General Button Constants
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Vec2d buttonTextPosition = new Vec2d(15, 14);
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;
}
