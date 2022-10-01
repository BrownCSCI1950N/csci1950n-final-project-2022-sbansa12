package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;
import javafx.scene.canvas.GraphicsContext;

public class CollisionComponent implements Component {
    protected final GameObject gameObject;
    protected Shape collisionShape;

    public CollisionComponent(GameObject gameObject, Shape s) {
        this.gameObject = gameObject;
        this.collisionShape = s;
    }

    public Shape getCollisionShape(){
        collisionShape.updatePosition(gameObject.getTransform().getGameSpacePosition());
        return collisionShape;
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

    @Override
    public String getTag() {
        return "collision";
    }

    public void onCollide(GameObject collidedObject) {

    }
}
