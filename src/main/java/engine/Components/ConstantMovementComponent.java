package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class ConstantMovementComponent implements Component {

    GameObject gameObject;
    Vec2d velocity;

    public ConstantMovementComponent(GameObject gameObject, Vec2d velocity) {
        this.gameObject = gameObject;
        this.velocity = velocity;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        Vec2d currentPosition = this.gameObject.getTransform().getCurrentGameSpacePosition();
        this.gameObject.getTransform().setCurrentGameSpacePosition(currentPosition.plus(this.velocity));
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "constantMovement";
    }
}
