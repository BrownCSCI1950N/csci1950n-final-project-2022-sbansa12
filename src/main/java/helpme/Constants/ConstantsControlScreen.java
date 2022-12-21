package helpme.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsControlScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "Controls";
    public static final Vec2d titlePosition = new Vec2d(370, 100);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Back Button Constants
    public static final String backButtonText = "←";
    public static final Vec2d backButtonPosition = new Vec2d(850,50);
    public static final Vec2d backButtonSize = new Vec2d(70,35);
    public static final Vec2d backButtonTextPosition = new Vec2d(20, 8);

    // Save Button Constants
    public static final String saveButtonText = "save";
    public static final Vec2d saveButtonPosition = new Vec2d(850,480);
    public static final Vec2d saveButtonSize = new Vec2d(70,35);
    public static final Vec2d saveButtonTextPosition = new Vec2d(6, 10);

    // General Button Constants
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Vec2d buttonTextPosition = new Vec2d(15, 14);
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;

    // Explanation Constants
    public static final Color textColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper textFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,32);

    // Textbox Constants
    public static final Vec2d textboxSize = new Vec2d(40, 40);
    public static final Vec2d textboxTextPosition = new Vec2d(10,8);

    // Positions of Explanations and Textboxes

    public static final Vec2d startPositionExplanation = new Vec2d(80, 170);
    public static final Vec2d explanationPadding = new Vec2d(470, 70);
    public static final Vec2d startPositionTextboxes = new Vec2d(400, 150);

    // Controls Incorrect
    public static final String popUpTextControlsError = "            Control Invalid. Try Again";

    // Controls Saved
    public static final String popUpTextControlsSaved = "                     Controls Saved!";


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
}
