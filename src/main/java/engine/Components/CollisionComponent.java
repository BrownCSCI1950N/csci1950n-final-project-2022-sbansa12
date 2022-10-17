package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;
import javafx.scene.canvas.GraphicsContext;

public class CollisionComponent implements Component {
    protected final GameObject gameObject;
    protected Shape collisionShape;
    protected final boolean isStaticObject;

    protected Integer layer;

    public CollisionComponent(GameObject gameObject, Shape s, Integer layer) {
        this.gameObject = gameObject;
        this.collisionShape = s;
        this.layer = layer;
        this.isStaticObject = false;
    }

    public CollisionComponent(GameObject gameObject, Shape s, Integer layer, boolean isStaticObject) {
        this.gameObject = gameObject;
        this.collisionShape = s;
        this.layer = layer;
        this.isStaticObject = isStaticObject;
    }

    public Shape getCollisionShape(){
        collisionShape.updatePosition(gameObject.getTransform().getCurrentGameSpacePosition());
        collisionShape.setVelocity(gameObject.getTransform().getVelocity());
        return collisionShape;
    }

    public boolean isStaticObject() {
        return isStaticObject;
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

    public void onCollide(Collision collision) {
    }

    public Integer getLayer() {
        return layer;
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    @Override
    public String toString() {
        return "Collision Component: Object: " + gameObject;
    }
}
