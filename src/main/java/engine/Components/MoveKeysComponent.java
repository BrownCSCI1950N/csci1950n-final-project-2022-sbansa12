package engine.Components;

import Pair.Pair;
import engine.Direction;
import engine.GameObject;
import engine.InputEvents;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

public class MoveKeysComponent extends KeysComponent {
    GameObject gameObject;
    List<KeyCode> movementKeys;
    List<Double> movementSpeed;
    Direction moveDirection;
    KeyCode buttonProcessing;

    /**
     * Constructor
     * @param movementKeys - keys to move the character with: [UP, DOWN, RIGHT, LEFT]
     * @param movementSpeed - speed to move the character in each direction
     */
    public MoveKeysComponent(GameObject gameObject, List<KeyCode> movementKeys, List<Double> movementSpeed) {
        assert movementKeys.size() == 4;
        assert movementSpeed.size() == 4;

        this.gameObject = gameObject;
        this.movementKeys = movementKeys;
        this.movementSpeed = movementSpeed;
        this.moveDirection = Direction.NONE;
        buttonProcessing = null;
    }

    public Direction getMoveDirection() {
        return moveDirection;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        if (moveDirection != Direction.NONE) {
            TransformComponent currentPosition = this.gameObject.getTransform();
            Vec2d newPosition;

            if (moveDirection == Direction.UP) {
                newPosition = currentPosition.getCurrentGameSpacePosition().plus(0, -movementSpeed.get(0));
                this.gameObject.getTransform().setCurrentGameSpacePosition(newPosition);
            } else if (moveDirection == Direction.DOWN) {
                newPosition = currentPosition.getCurrentGameSpacePosition().plus(0, movementSpeed.get(1));
                this.gameObject.getTransform().setCurrentGameSpacePosition(newPosition);
            } else if (moveDirection == Direction.RIGHT) {
                newPosition = currentPosition.getCurrentGameSpacePosition().plus(movementSpeed.get(2), 0);
                this.gameObject.getTransform().setCurrentGameSpacePosition(newPosition);
            } else if (moveDirection == Direction.LEFT) {
                newPosition = currentPosition.getCurrentGameSpacePosition().plus(-movementSpeed.get(3), 0);
                this.gameObject.getTransform().setCurrentGameSpacePosition(newPosition);
            }
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    public static final String tag = "moveKeys";
    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void script(Pair<InputEvents, List<KeyCode>> input) {
        if (input.getLeft() == InputEvents.ONKEYPRESSED) {
            int directionToMoveIn = -1;
            for (KeyCode pressedValue: input.getRight()) {
                directionToMoveIn = movementKeys.indexOf(pressedValue);
                if (directionToMoveIn != -1) {
                    buttonProcessing = pressedValue;
                    break;
                }
            }

            if (directionToMoveIn != -1) {

                if (directionToMoveIn == 0) {
                    this.moveDirection = Direction.UP;
                } else if (directionToMoveIn == 1) {
                    this.moveDirection = Direction.DOWN;
                } else if (directionToMoveIn == 2) {
                    this.moveDirection = Direction.RIGHT;
                } else if (directionToMoveIn == 3) {
                    this.moveDirection = Direction.LEFT;
                }
            }
        } else if (input.getLeft() == InputEvents.ONKEYRELEASED) {
            if (buttonProcessing != null) {
                if (input.getRight().contains(buttonProcessing)) {
                    this.moveDirection = Direction.NONE;
                }
            }
        }
    }
}
