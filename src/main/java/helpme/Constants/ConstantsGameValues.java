package helpme.Constants;

import Pair.Pair;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static engine.TerrainGeneration.TileType.*;

public class ConstantsGameValues {
    // Controls
    public static List<List<KeyCode>> controls = new LinkedList<>();
    public static final List<List<KeyCode>> defaultControls = new LinkedList<>() {{
        add(new LinkedList<>() {{
            add(KeyCode.W);
            add(KeyCode.S);
            add(KeyCode.D);
            add(KeyCode.A);
            add(KeyCode.C);
        }});
        add(new LinkedList<>() {{
            add(KeyCode.I);
            add(KeyCode.K);
            add(KeyCode.L);
            add(KeyCode.J);
            add(KeyCode.N);
        }});
    }};

    public static final List<KeyCode> validControls = List.of(
            KeyCode.DIGIT1,
            KeyCode.DIGIT2,
            KeyCode.DIGIT3,
            KeyCode.DIGIT4,
            KeyCode.DIGIT5,
            KeyCode.DIGIT6,
            KeyCode.DIGIT7,
            KeyCode.DIGIT8,
            KeyCode.DIGIT9,
            KeyCode.DIGIT0,
            KeyCode.Q,
            KeyCode.W,
            KeyCode.E,
            KeyCode.R,
            KeyCode.T,
            KeyCode.Y,
            KeyCode.U,
            KeyCode.I,
            KeyCode.O,
            KeyCode.P,
            KeyCode.A,
            KeyCode.S,
            KeyCode.D,
            KeyCode.F,
            KeyCode.G,
            KeyCode.H,
            KeyCode.J,
            KeyCode.K,
            KeyCode.L,
            KeyCode.Z,
            KeyCode.X,
            KeyCode.C,
            KeyCode.V,
            KeyCode.B,
            KeyCode.N,
            KeyCode.M,
            KeyCode.UP,
            KeyCode.DOWN,
            KeyCode.LEFT,
            KeyCode.RIGHT
            );

    public static final KeyCode clickButtonLevelQuit = KeyCode.DIGIT1;
    public static final KeyCode clickButtonLevelReset = KeyCode.DIGIT2;

    // Levels
    public static final List<String> levels = new LinkedList<>() {{
        add("00");
        add("01");
        add("02");
        add("03");
        add("04");
    }};
    public static final Integer numberOfLevelsAcross = 4;
    public static final Map<String, Boolean> levelComplete = new HashMap<>();

    // Maps
    public static String mapsPathway = ".\\src\\main\\java\\helpme\\Maps";
    public static List<TileType> mapMustInclude = List.of(TileType.BUTTON, SPAWN1, SPAWN2, TileType.EXIT1, TileType.EXIT2);
    public static List<TileType> mapTiles = List.of(ROOM, SPAWN1, SPAWN2, WALL1, EXIT1, EXIT2, BUTTON);
    public static List<TileType> specialTiles = List.of(BOX1, TRAP1, TRAP2, PLAYER1, PLAYER2);
    public static List<TileType> spawns = List.of(SPAWN1, SPAWN2);
    public static final Vec2i mapSize = new Vec2i(39, 22);
    public static final Vec2d tileSize = new Vec2d(25,25);

    // Resources
    public static String resourcesPathway = "file:.\\src\\main\\java\\helpme\\Images\\";

    // Collision
    public static final Integer wallCollisionLayer = 0;
    public static final Integer boxCollisionLayer = 4;
    public static final Integer buttonCollisionLayer = 1;
    public static final Integer exitCollisionLayer = buttonCollisionLayer;
    public static final Integer playerCollisionLayer = 2;
    public static final Integer physicsGroundedCollisionLayer = 3;
    public static final List<Pair<Integer, Integer>> layersCollide =
            List.of(
                    new Pair<Integer, Integer>(0,2),
                    new Pair<Integer, Integer>(1,2),
                    new Pair<Integer, Integer>(0,3),
                    new Pair<Integer, Integer>(4,2),
                    new Pair<Integer, Integer>(4,0),
                    new Pair<Integer, Integer>(4,3),
                    new Pair<Integer, Integer>(4,4));

    // Physics Gravity
    public static final Vec2d gravityUp = new Vec2d(0, 9.81*16);
    public static final Vec2d gravityDown = new Vec2d(0, 9.81*16*2.5);
    public static final Vec2d gravityLowJump = gravityDown;
    public static final Vec2d gravityRest = new Vec2d(0, 9.81*16*1.75);
    public static final Vec2d playerGroundedShapeSizeAdd = new Vec2d(0,1);

    // Physics Player
    public static final double playerMass = 1.0;
    public static final double playerRestitution = 0.0;
    public static final List<Double> movementVelocity = List.of(150.0, 100.0);

    // Physics Box
    public static final double boxMass = 5.0;
    public static final double boxRestitution = 0.6;
    public static final double boxFriction = 0.5;

    // Health and Damage System: Traps
    public static final Integer playerMaxHealth = 10;
    public static final Integer trapsDamage = 10;
    public static final Vec2d trapPositionAdd = new Vec2d(0,20);
    public static final Vec2d trapShapeAdd = new Vec2d(0,20);
}
