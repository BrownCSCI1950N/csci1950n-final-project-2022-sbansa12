package tic;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Control Board and Bar Sizing
    public static final Integer DIVISION_FACTOR = 14;
    public static final Integer SQUARE_SIZE = 4;
    public static final Integer BAR_SIZE = 1;

    // Initial Board Values
    public static final Vec2d initialBoardTopRight =  new Vec2d(340,120);
    public static final double initialBoardSize = 300;

    // Control Timer
    public static final BigDecimal TIMER_VALUE = new BigDecimal("8000000000"); // 8 seconds
    public static final Vec2d timerTextPosition = new Vec2d(134, 40);
    public static final Vec2d timerPosition = new Vec2d(170, 60);
    public static final Vec2d timerSize = new Vec2d(40, 440);
    public static final double timerBorder = 10;
    public static final int timerTextDecimalPlaces = 1;
    public static final int timerBarSmoothness = 5;
    public static final Color timerContainerColor = Color.rgb(255, 206,206);
    public static final Color timerMaxColor = Color.rgb(255, 151,151);
    public static final Color timerColor = Color.rgb(255, 255,255);

    // Control Background Color
    public static final Color backgroundColor = Color.rgb(255, 151,151);

    // Control Board Aesthetics
    public static final Color barsColor =  Color.rgb(255, 206,206);

    // Control Game Aesthetics
    public static final Vec2d gamePiecesLocation = new Vec2d(24,-21);
    public static final FontWrapper gamePieceFont = new FontWrapper("Arial",FontWeight.EXTRA_LIGHT, null, 80) ;
    public static final Vec2d turnTextPosition = new Vec2d(440, 500);
    public static final Color turnTextColor =  Color.rgb(255, 233,233);
    public static final FontWrapper turnTextFont = new FontWrapper("Bauhaus 93", null, null,34);

    // Control Title and Win Screen Title and Button
    public static final Color titleColor = Color.rgb(255, 229, 229);
    public static final FontWrapper titleFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null,64);
    public static final Vec2d buttonPosition =  new Vec2d(415, 360);
    public static final Vec2d buttonSize = new Vec2d(120, 60);
    public static final Vec2d buttonTextPosition = new Vec2d(14, 20);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final Color buttonColor = Color.rgb(255, 229, 229);
    public static final Color buttonHoverColor = Color.rgb(255,255, 255);
    public static final Color buttonTextColor = Color.rgb(248, 110,110);
    public static final FontWrapper buttonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final String startScreenTitle = "TIC-TAC-TOE";
    public static final Vec2d startScreenTitlePosition = new Vec2d(300, 190);
    public static final String startScreenButtonText = "  start";
    public static final Vec2d winScreenTitlePosition = new Vec2d(390, 190);

    // Control Pieces Aesthetics
    public static final Map<String, Color> placedColors = new HashMap<>(){{
        // Setup Piece Colors
        put("x", Color.rgb(154,11,11));
        put("o", Color.rgb(255, 255, 255));

    }};
    public static final Map<String, Color> hoverColors = new HashMap<>(){{
        put("x", Color.rgb(237, 95, 95));
        put("o", Color.rgb(255, 198, 198));
    }};

    public static double intialBoardSquaresAndBars(double size, int numberOfSquare, int numberOfBars) {
        return ((size/DIVISION_FACTOR) * ((numberOfSquare * SQUARE_SIZE) + (numberOfBars * BAR_SIZE)));
    }
}
