package alc;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.util.List;

public class Constants {
    // Start and End Screen Aesthetics
    public static Color backgroundColor = Color.rgb(255, 204, 153);
    public static final Vec2d startScreenTitlePosition = new Vec2d(300, 190);
    public static final Vec2d endScreenTitlePosition = new Vec2d(300, 190);
    public static final String startScreenTitle = "   ALCHEMY";
    public static final String endScreenTitle = "Game Completed!";
    public static final Color titleColor = Color.rgb(255, 229, 229);
    public static final FontWrapper titleFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null,64);
    public static final Vec2d buttonPosition =  new Vec2d(415, 360);
    public static final Vec2d instructionsButtonPosition =  new Vec2d(750, 368);
    public static final Vec2d buttonSize = new Vec2d(120, 60);
    public static final Vec2d instructionsButtonSize =  new Vec2d(40, 40);
    public static final Color buttonColor = Color.rgb(255, 229, 229);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final Vec2d instructionsButtonArcSize =  new Vec2d(10, 10);
    public static final String startScreenButtonText = "  start";
    public static final String instructionsStartScreenButtonText = "?";
    public static final String endScreenButtonText = "restart";
    public static final Vec2d buttonTextPosition = new Vec2d(14, 20);
    public static final Vec2d instructionsButtonTextPosition = new Vec2d(12, 10);
    public static final Color buttonTextColor = Color.rgb(248, 110,110);
    public static final FontWrapper buttonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Color buttonHoverColor = Color.rgb(255,255, 255);

    // Instruction Screen Aesthetics
    public static final Vec2d instructionsScreenTitlePosition = new Vec2d(300, 190);
    public static final String instructionsScreenTitle = "Instructions!";
    public static final String instructionsScreenButtonText = "     X";
    public static final Vec2d instructionsScreenInstructionsPosition = new Vec2d(330, 240);
    public static final String instructionsScreenInstructions = "W: Pan Up                       Z: Zoom In\n" +
                                                                "A: Pan Left                      X: Zoom Out\n" +
                                                                "S: Pan Down\n" +
                                                                "D: Pan Right\n";
    public static final FontWrapper instructionsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 20);

    // Game Screen Aesthetics
    public static final Color backgroundColorGame = Color.rgb(255, 204, 153);
    public static final Vec2d backgroundPosition = new Vec2d(-264,-263);

    // Game Screen Element Menu Aesthetics
    public static final Vec2d elementMenuPosition = new Vec2d(750, 0);
    public static final Vec2d elementMenuSize = new Vec2d(360, 540);
    public static final Color elementMenuColor = Color.rgb(255, 182, 136);
    public static final Vec2d elementButtonSize = new Vec2d(elementMenuSize.x, 100);
    public static final Color elementButtonColor = elementMenuColor;
    public static final Color elementButtonHoverColor = Color.rgb(255,234,211);
    public static final Vec2d elementButtonTextPosition = new Vec2d(30, 38);
    public static final Color elementButtonTextColor = Color.rgb(200, 56,56);
    public static final FontWrapper elementButtonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);

    // Viewport
    public static final double scale = 0.5; // px/unit
    public static final Vec2d startingViewportGameCoordinates = new Vec2d(10,10);
    public static final Vec2d viewportScreenPosition = new Vec2d(0,0);
    public static final Vec2d viewportScreenSize = new Vec2d(elementMenuPosition.x, 540);
    public static final List<KeyCode> panningButtons = List.of(KeyCode.W, KeyCode.S, KeyCode.D, KeyCode.A);
    public static final List<KeyCode> zoomingButtons = List.of(KeyCode.Z, KeyCode.X);
    public static final List<Double> panSpeed = List.of(15.0,15.0,15.0,15.0);
    public static final List<Double> zoomSpeed = List.of(1.08,1.08);
}
