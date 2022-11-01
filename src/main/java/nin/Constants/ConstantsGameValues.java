package nin.Constants;

import Pair.Pair;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;

public class ConstantsGameValues {
    // Tile System
    public static final Vec2i mapSize = new Vec2i(39, 22);
    public static final Vec2d tileSize = new Vec2d(25,25);

    // Controls
    public static final List<KeyCode> movementKeys = new LinkedList<>(){{
        add(KeyCode.UP);
        add(KeyCode.RIGHT);
        add(KeyCode.LEFT);
    }};
    public static final List<Double> movementVelocity = List.of(150.0, 100.0);

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

    // Player
    public static final Integer playerMaxHealth = 10;

    // Physics
    public static final Vec2d gravityUp = new Vec2d(0, 9.81*16);
    public static final Vec2d gravityDown = new Vec2d(0, 9.81*16*2.5);
    public static final Vec2d gravityLowJump = gravityDown;
    public static final Vec2d playerGroundedShapeSizeAdd = new Vec2d(0,1);
    public static final double playerMass = 1.0;
    public static final double playerRestitution = 0.0;
    public static final double wallMass = 1.0;
    public static final double wallRestitution = 0.0;
    public static final double boxMass = 5.0;
    public static final double boxRestitution = 0.6;
    public static final double halfboxMass = 2.5;
    public static final double halfboxRestitution = 0.3;
}
