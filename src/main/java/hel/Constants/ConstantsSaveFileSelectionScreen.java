package hel.Constants;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class ConstantsSaveFileSelectionScreen {
    // Background Constants
    public static Color backgroundColor = ConstantsStartScreen.backgroundColor;

    // Title Constants
    public static final String title = "Select Save File";
    public static final Vec2d titlePosition = new Vec2d(300, 160);
    public static final Color titleColor = ConstantsStartScreen.titleColor;
    public static final FontWrapper titleFont = ConstantsStartScreen.titleFont;

    // Save Files Pathway
    public static String savefilesPathway = ".\\src\\main\\java\\hel\\SaveFiles";

    // Number of Possible Save Files
    public static Integer numberOfPossibleSavefiles = 3;

    // Load Button Placements
    public static final Vec2d buttonStartingPosition = new Vec2d(340,200);
    public static final Vec2d buttonPadding = new Vec2d(0,30);

    // Load Button Constants
    public static final Vec2d loadButtonSize = new Vec2d(200,50);

    // Delete Button Placements
    public static final Vec2d deleteButtonRelativePosition = new Vec2d(220, 0);

    // Delete Button Constants
    public static final String deleteButtonText = "X";
    public static final Vec2d deleteButtonSize = new Vec2d(50,50);

    // General Button Constants
    public static final Color buttonColor = ConstantsStartScreen.buttonColor;
    public static final Color buttonHoverColor = ConstantsStartScreen.buttonHoverColor;
    public static final Vec2d buttonArcSize = ConstantsStartScreen.buttonArcSize;
    public static final Vec2d buttonTextPosition = new Vec2d(15, 14);
    public static final Color buttonTextColor = ConstantsStartScreen.buttonTextColor;
    public static final FontWrapper buttonTextFont = ConstantsStartScreen.buttonTextFont;

    // PopUp Constants
    public static final Color popUpOverlayColor = Color.rgb(141,139,139 ,0.8);
    public static final Vec2d popUpPanelPosition = new Vec2d(100,80);
    public static final Vec2d popUpPanelSize = new Vec2d(760,380);
    public static final Color popUpPanelColor = Color.rgb(243,236,224);
    public static final Vec2d popUpPanelArcSize = new Vec2d(10,10);
    public static final Vec2d popUpTextPosition = new Vec2d(195,280);
    public static final String popUpText = "Save File Corrupted... Deleting Save File";
    public static final Color popUpTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper popUpTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Vec2d popUpButtonPosition = new Vec2d(790, 110);
    public static final Vec2d popUpButtonSize = new Vec2d(40,40);
    public static final String popUpButtonText = "X";
}
