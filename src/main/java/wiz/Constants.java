package wiz;

import Pair.Pair;
import engine.FontWrapper;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.math.BigDecimal;
import java.util.List;

public class Constants {
    // Start Screen Aesthetics
    public static Color backgroundColor = Color.rgb(87,173,37);
    public static final Vec2d startScreenTitlePosition = new Vec2d(300, 190);
    public static final String startScreenTitle = "    WIZARD";
    public static final Color titleColor = Color.rgb(61, 15,26);
    public static final FontWrapper titleFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,64);
    public static final Vec2d buttonPosition =  new Vec2d(415, 360);

    public static final Vec2d buttonSize = new Vec2d(120, 60);
    public static final Color buttonColor = Color.rgb(255, 229, 229);
    public static final Vec2d buttonArcSize = new Vec2d(10,10);
    public static final String startScreenButtonText = "  start";
    public static final Vec2d buttonTextPosition = new Vec2d(14, 20);

    public static final Color buttonTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper buttonTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Color buttonHoverColor = Color.rgb(207,196,184);

    // Instruction Screen Aesthetics
    public static final Vec2d instructionsScreenTitlePosition = new Vec2d(320, 140);
    public static final String instructionsScreenTitle = "Instructions!";
    public static final String instructionsScreenButtonText = "     X";
    public static final Vec2d instructionsScreenInstructionsPosition = new Vec2d(270, 220);
    public static final String instructionsScreenInstructions = "             Goal: Find the Exit Portal!\n"+
                                                                "                   Movement: WASD\n" +
                                                                "                       Shoot: SPACE\n" +
                                                                "Go Back To World Generation Screen: R\n";
    public static final FontWrapper instructionsFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 24);

    // World Generation Screen Aesthetics
    public static final Vec2d worldGenerationScreenTitlePosition = new Vec2d(100, 170);
    public static final String worldGenerationScreenTitle = "Number for World Generator:";
    public static final Vec2d worldGenerationScreenTextBoxPosition = new Vec2d(338, 240);
    public static final Vec2d worldGenerationTextBoxSize = new Vec2d(270, 55);
    public static final Color worldGenerationTextBoxHoverColor = Color.rgb(148,140,140);
    public static final Vec2d worldGenerationButtonPosition =  new Vec2d(338, 360);
    public static final Vec2d worldGenerationButtonSize = new Vec2d(270, 55);
    public static final Vec2d worldGenerationButtonTextPosition = new Vec2d(11, 20);
    public static final String worldGenerationScreenButtonText = "create new world";
    public static final FontWrapper worldGenerationFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 20);

    // Viewport
    public static final double scale = 1; // px/unit
    public static final Vec2d startingViewportGameCoordinates = new Vec2d(10,10);
    public static final Vec2d viewportScreenPosition = new Vec2d(0,0);
    public static final Vec2d viewportScreenSize =  new Vec2d(960, 540);

    // Game Aesthetics
    public static final Color backgroundColorGame = backgroundColor;
    public static final Color popUpOverlayColor = Color.rgb(141,139,139 ,0.8);
    public static final Vec2d popUpPanelPosition = new Vec2d(100,80);
    public static final Vec2d popUpPanelSize = new Vec2d(760,380);
    public static final Color popUpPanelColor = Color.rgb(243,236,224);
    public static final Vec2d popUpPanelArcSize = new Vec2d(10,10);
    public static final Vec2d popUpTextPosition = new Vec2d(320,270);
    public static final String popUpText = "look for the blue portal!";
    public static final Color popUpTextColor = Color.rgb(61, 15,26);
    public static final FontWrapper popUpTextFont = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Vec2d popUpButtonPosition = new Vec2d(790, 110);
    public static final Vec2d popUpButtonSize = new Vec2d(40,40);
    public static final Color popUpButtonColor = Color.rgb(255, 229, 229);
    public static final Vec2d popUpButtonArcSize = new Vec2d(10,10);
    public static final String popUpButtonText = "X";
    public static final Vec2d popUpButtonPositionText = new Vec2d(12, 11);
    public static final Color popUpButtonColorText = Color.rgb(61, 15,26);
    public static final FontWrapper popUpButtonFontText = new FontWrapper("Bauhaus 93", FontWeight.EXTRA_LIGHT, null, 32);
    public static final Color popUpButtonHoverColor =Color.rgb(207,196,184);
    public static final Vec2d deathsPosition = new Vec2d(16, 45);
    public static final Color deathsColor = Color.rgb(255,255,255);
    public static final FontWrapper deathsFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,52);
    public static final Vec2d hintButtonPosition =  new Vec2d(840, 460);
    public static final Vec2d hintButtonSize =  new Vec2d(75, 40);
    public static final Vec2d hintButtonArcSize =  new Vec2d(10, 10);
    public static final String hintButtonText = "hint";
    public static final Vec2d hintButtonTextPosition = new Vec2d(11, 10);
    public static final Vec2d minimapPosition = new Vec2d(20,390);
    public static final Vec2d minimapSize = new Vec2d(130,130);

    // Game Values
    public static final Vec2i mapSize = new Vec2i(40,40);
    public static final Vec2i minRoomSize = new Vec2i(4,4);
    public static final float probabilitySplitRoom = 1.0F;
    public static final int maxHallWidth = 1;
    public static final Vec2d tileSize = new Vec2d(100,100);
    public static final Vec2d playerSize = new Vec2d(50,50);
    public static final Vec2d projectileSize = playerSize;
    public static final List<KeyCode> movementKeys = List.of(KeyCode.W, KeyCode.S, KeyCode.D, KeyCode.A);
    static final Double moveSpeed = 25.0;
    public static final List<Double> movementSpeed = List.of(moveSpeed,moveSpeed,moveSpeed,moveSpeed);
    public static final KeyCode projectileKey = KeyCode.SPACE;
    public static final double projectileSpeedEnemy = 20.0;
    public static final double projectileSpeedPlayer = 30.0;
    public static final Integer mapCollisionLayer = 0;
    public static final Integer playerCollisionLayer = 1;
    public static final Integer projectilesPlayerCollisionLayer = 1;
    public static final Integer moveEnemyCollisionLayer = 2;
    public static final Integer projectilesEnemyCollisionLayer = 2;
    public static final List<Pair<Integer, Integer>> layersCollide = List.of(new Pair<Integer, Integer>(0,1), new Pair<Integer, Integer>(0,2), new Pair<Integer, Integer>(1, 2));
    public static final Integer trapsDamage = 10;
    public static final Integer trapsMaxHealth = 10;
    public static final Integer bossMaxHealth = 200;
    public static final Integer playerMaxHealth = 10;
    public static final Integer projectileDamage = 10;
    public static final BigDecimal enemyMoveTime = new BigDecimal("2100000000");
    public static final BigDecimal bossMoveTime = new BigDecimal("1800000000");
    public static final BigDecimal bossShootTime = bossMoveTime.multiply(new BigDecimal("3"));
    public static final BigDecimal playerMoveTime = new BigDecimal("180000000");
    public static final double spawnPointAdd = 25;
    public static final BigDecimal projectileLifeTime = new BigDecimal("280000000");

    // End Screen Aesthetics
    public static final Vec2d endScreenTitlePosition = new Vec2d(300, 190);
    public static final String endScreenTitleWin = "   You Win!";
    public static final String endScreenTitleTryAgain = "Wrong Portal!";
    public static final String endScreenButtonText = "restart";
    public static final Vec2d endScreenDeathsPosition = new Vec2d(380, 270);
    private static final double opacityMinimap = 0.7;
    public static Color tileToColor(TileType t) {
        switch (t) {
            case PLAYER1:
                return Color.rgb(136,82,211, opacityMinimap);
            case ROOM:
                return Color.rgb(128,181,49, opacityMinimap);
            case WALL1:
            case HIDDEN:
                return Color.rgb(87,173,37, opacityMinimap);
            case SPAWN1:
                return Color.rgb(82,169,43, opacityMinimap);
            case EXIT1:
                return Color.rgb(229,189,68,opacityMinimap);
            case STAIRS:
                return Color.rgb(26,66,187,opacityMinimap);
            case TRAP1:
            case ENEMY:
            case BOSS:
                return Color.rgb(255,0,0,opacityMinimap);
            default:
                return Color.rgb(255,255,255,opacityMinimap);
        }
    }

    public static final double distanceDiscover = 86000;
    public static final Color notDiscoveredColor = Color.rgb(0,0,0,opacityMinimap+0.3);
    public static final Vec2d bossHealthBarPosition = new Vec2d(260, 8);
    public static final Vec2d bossHealthBarSize = new Vec2d(680, 50);
    public static final double bossHealthBarBorder = 10.0;
    public static final Color bossHealthBarContainerColor = Color.rgb(229,189,68,opacityMinimap);
    public static final Color bossHealthBarMaxBarColor = Color.rgb(128,181,49, opacityMinimap);
    public static final Color bossHealthBarColor = Color.rgb(255,0,0,opacityMinimap);
    public static final FontWrapper bossHealthBarTextFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,32);
    public static final Vec2d deathMessagePosition = new Vec2d(320,500);
    public static final Color deathMessageColor = deathsColor;
    public static final FontWrapper deathMessageFont = new FontWrapper("Colonna MT", FontWeight.EXTRA_LIGHT, null,42);
}
