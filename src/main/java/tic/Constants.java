package tic;

import engine.FontWrapper;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Control Board and Bar Sizing
    public static final Integer DIVISION_FACTOR = 11;
    public static final Integer SQUARE_SIZE = 3;
    public static final Integer BAR_SIZE = 1;

    // Initial Board Values
    public static final Vec2d initialBoardTopRight =  new Vec2d(340,120);
    public static final double initialBoardSize = 300;

    // Control Timer
    public static final BigDecimal TIMER_VALUE = new BigDecimal("6000000000"); // 6 seconds
    public static final Vec2d timerPosition = new Vec2d(170, 60);
    public static final Vec2d timerSize = new Vec2d(40, 440);
    public static final double timerBorder = 10;

    public static final Color timerContainerColor = Color.rgb(255, 206,206);
    public static final Color timerMaxColor = Color.rgb(255, 151,151);
    public static final Color timerColor = Color.rgb(255, 206,206);

    // Control Background Color
    public static final Color backgroundColor = Color.rgb(255, 151,151);

    // Control Board Aesthetics
    public static final Color barsColor =  Color.rgb(255, 206,206);

    // Control Game Aesthetics
    public static final Vec2d gamePiecesLocation = new Vec2d(23,-15);
    public static final Vec2d turnTextPosition = new Vec2d(420, 500);
    public static final Color turnTextColor =  Color.rgb(255, 206,206);
    public static final FontWrapper turnTextFont = new FontWrapper("Arial Narrow", null, null,34);

    // Control Title and Win Screen Title and Button
    public static final Color titleColor = Color.rgb(255, 206, 206);
    public static final FontWrapper titleFont = new FontWrapper("Arial Narrow", null, null,64);
    public static final Vec2d buttonPosition =  new Vec2d(440, 360);
    public static final Vec2d buttonSize = new Vec2d(60, 30);

    public static final Vec2d buttonTextPosition = new Vec2d(10, 10);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final Color buttonColor = Color.rgb(255, 206, 206);
    public static final Color buttonHoverColor = Color.rgb(255,255, 255);
    public static final Color buttonTextColor = Color.rgb(0, 0,0);
    public static final FontWrapper buttonTextFont = new FontWrapper("Arial Narrow",null, null, 14);
    public static final String startScreenTitle = "TIC-TAC-TOE";
    public static final Vec2d startScreenTitlePosition = new Vec2d(320, 125);
    public static final String startScreenButtonText = "Start!";
    public static final Vec2d winScreenTitlePosition = new Vec2d(390, 125);

    // Control Pieces Aesthetics
    public static final Map<String, Color> placedColors = new HashMap<>(){{
        // Setup Piece Colors
        put("X", Color.rgb(154,11,11));
        put("O", Color.rgb(255, 255, 255));

    }};
    public static final Map<String, Color> hoverColors = new HashMap<>(){{
        put("X", Color.rgb(237, 95, 95));
        put("O", Color.rgb(255, 198, 198));
    }};

    public static double intialBoardSquaresAndBars(double size, int numberOfSquare, int numberOfBars) {
        return ((size/DIVISION_FACTOR) * ((numberOfSquare * SQUARE_SIZE) + (numberOfBars * BAR_SIZE)));
    }
}
