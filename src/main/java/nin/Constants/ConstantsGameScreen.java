package nin.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsGameScreen {
    // Background Constants
    public static Color gameScreenBackgroundColor = ConstantsStartScreen.startScreenBackgroundColor;

    // Viewport
    public static final double scale = 1; // px/unit
    public static final Vec2d startingViewportGameCoordinates = new Vec2d(487,275);
    public static final Vec2d viewportScreenPosition = new Vec2d(0,0);
    public static final Vec2d viewportScreenSize = new Vec2d(960, 540);

    // Level Complete PopUp
    public static final Color popUpOverlayColor = Color.rgb(141,139,139 ,0.8);
    public static final Vec2d popUpPanelPosition = new Vec2d(100,80);
    public static final Vec2d popUpPanelSize = new Vec2d(760,380);
    public static final Color popUpPanelColor = Color.rgb(243,236,224);
    public static final Vec2d popUpPanelArcSize = new Vec2d(10,10);
    public static final Vec2d popUpTextPosition = new Vec2d(195,280);
    public static final String popUpText = "Level Complete! Press SPACE to Continue";
    public static final Color popUpTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper popUpTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
}
