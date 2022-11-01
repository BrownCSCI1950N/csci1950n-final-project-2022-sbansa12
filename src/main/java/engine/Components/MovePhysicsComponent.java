package engine.Components;

import Pair.Pair;
import engine.GameObject;
import engine.InputEvents;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

public class MovePhysicsComponent extends KeysComponent {
    protected GameObject gameObject;
    List<KeyCode> keys;
    boolean[] onceHappened;
    List<Double> movementVelocity;
    Integer directionProcessing;
    public MovePhysicsComponent(GameObject gameObject, List<KeyCode> keys, List<Double> movementVelocity) {
        // [UP, RIGHT, LEFT]
        assert keys.size() == 3;
        assert movementVelocity.size() == 2;

        this.gameObject = gameObject;
        this.keys = keys;
        onceHappened = new boolean[] {false, false, false};
        this.movementVelocity = movementVelocity;
        this.directionProcessing = 0;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    public static final String tag = "movePhysics";

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void script(Pair<InputEvents, List<KeyCode>> input) {
        assert gameObject.hasComponentTag("physics");
        PhysicsComponent p = (PhysicsComponent) gameObject.getComponent("physics");

        if (input.getLeft() == InputEvents.ONKEYPRESSED) {
            for (int i = 0; i < keys.size(); i++) {
                if (input.getRight().contains(keys.get(i))) {
                    if (i == 0) {
                        if (!onceHappened[0]) {
                            actionJump(p);
                            this.onceHappened[0] = true;
                        }
                    } else if (i == 1) {
                        actionSide(1, p);
                        directionProcessing = 1;
                    } else {
                        // i == 2
                        actionSide(-1, p);
                        directionProcessing = -1;
                    }
                }
            }
        } else if (input.getLeft() == InputEvents.ONKEYRELEASED) {
            for (int i = 0; i < keys.size(); i++) {
                if (input.getRight().contains(keys.get(i))) {
                    if (i == 0) {
                        this.onceHappened[0] = false;
                    } else if (i == 1) {
                        if (directionProcessing == 1) {
                            p.vel = new Vec2d(0, p.vel.y);
                            directionProcessing = 0;
                        }
                    } else {
                        // i == 2
                        if (directionProcessing == -1) {
                            p.vel = new Vec2d(0, p.vel.y);
                            directionProcessing = 0;
                        }
                    }
                }
            }
        }
    }

    public void actionSide(Integer direction, PhysicsComponent p) {
        p.vel = new Vec2d(direction * movementVelocity.get(1), p.vel.y);
    }

    public void actionJump(PhysicsComponent p) {
        if (p.isGrounded) {
            p.applyImpulse(new Vec2d(0, -movementVelocity.get(0)));
        }
    }

    public boolean getOnceHappenedJump() {
        return onceHappened[0];
    }
}
