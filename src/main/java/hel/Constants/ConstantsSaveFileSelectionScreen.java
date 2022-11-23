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
}
