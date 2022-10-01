package alc;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.util.List;

public class Constants {
    // Start Screen Aesthetics
    public static Color backgroundColor = Color.rgb(219, 188, 149);
    public static final Vec2d startScreenTitlePosition = new Vec2d(300, 190);
    public static final String startScreenTitle = "   ALCHEMY";
    public static final Color titleColor = Color.rgb(61, 15,26);
    public static final FontWrapper titleFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,64);
    public static final Vec2d buttonPosition =  new Vec2d(415, 360);
    public static final Vec2d instructionsButtonPosition =  new Vec2d(750, 368);
    public static final Vec2d buttonSize = new Vec2d(120, 60);
    public static final Vec2d instructionsButtonSize =  new Vec2d(40, 40);
    public static final Color buttonColor = Color.rgb(255, 229, 229);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final Vec2d instructionsButtonArcSize =  new Vec2d(10, 10);
    public static final String startScreenButtonText = "  start";
    public static final String instructionsStartScreenButtonText = "?";
    public static final Vec2d buttonTextPosition = new Vec2d(14, 20);
    public static final Vec2d instructionsButtonTextPosition = new Vec2d(12, 10);
    public static final Color buttonTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper buttonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Color buttonHoverColor = Color.rgb(207,196,184);

    // Instruction Screen Aesthetics
    public static final Vec2d instructionsScreenTitlePosition = new Vec2d(320, 190);
    public static final String instructionsScreenTitle = "Instructions!";
    public static final String instructionsScreenButtonText = "     X";
    public static final Vec2d instructionsScreenInstructionsPosition = new Vec2d(145, 240);
    public static final String instructionsScreenInstructions = "                         W: Pan Up, A: Pan Left, S: Pan Down, D: Pan Right\n" +
                                                                "                                              Z: Zoom In, X: Zoom Out\n" +
                                                                "UP Arrow: Move Element Menu Up, DOWN Arrow: Move Element Menu Down \n";
    public static final FontWrapper instructionsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 20);

    // Game Screen Element Menu Aesthetics
    public static final Vec2d elementMenuPosition = new Vec2d(750, 0);
    public static final Vec2d elementMenuSize = new Vec2d(210, 540);
    public static final Color elementMenuColor = Color.rgb(153, 112, 69);
    public static final Vec2d elementButtonSize = new Vec2d(elementMenuSize.x, 60);
    public static final Color elementButtonColor = elementMenuColor;
    public static final Color elementButtonHoverColor = buttonHoverColor;
    public static final Vec2d elementButtonTextPosition = new Vec2d(17, 22);
    public static final Color elementButtonTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper elementButtonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);

    // Viewport
    public static final double scale = 0.5; // px/unit
    public static final Vec2d startingViewportGameCoordinates = new Vec2d(10,10);
    public static final Vec2d viewportScreenPosition = new Vec2d(0,0);
    public static final Vec2d viewportScreenSize = new Vec2d(elementMenuPosition.x, 540);
    public static final List<KeyCode> panningButtons = List.of(KeyCode.W, KeyCode.S, KeyCode.D, KeyCode.A);
    public static final List<KeyCode> zoomingButtons = List.of(KeyCode.Z, KeyCode.X);
    public static final List<Double> panSpeed = List.of(15.0,15.0,15.0,15.0);
    public static final List<Double> zoomSpeed = List.of(1.08,1.08);

    // Game Screen Aesthetics
    public static final Color backgroundColorGame = backgroundColor;
    public static final Vec2d backgroundPosition = new Vec2d(-264,-263);
    public static final Vec2d trashPosition = new Vec2d(-700,-500);

    // Game Values
    public static final Vec2d gameObjectSize = new Vec2d(100,100);
    public static final Integer totalNumberOfElements = 20;
    public static final Vec2d countElementsPosition = new Vec2d(20, 520);
    public static final Color countElementsColor = Color.rgb(0,0,0);
    public static final FontWrapper countElementsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);
}
