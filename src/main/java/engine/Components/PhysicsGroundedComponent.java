package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;
import engine.TerrainGeneration.TileType;

import java.util.List;

public class PhysicsGroundedComponent extends CollisionComponent {
    PhysicsComponent p;
    List<TileType> grounds;
    public PhysicsGroundedComponent(GameObject gameObject, Shape s, Integer layer, PhysicsComponent p, List<TileType> grounds) {
        super(gameObject, s, layer, true, false);
        this.p = p;
        this.grounds = grounds;
    }

//    @Override
//    public Shape getCollisionShape() {
//        collisionShape.updatePosition(gameObject.getTransform().getCurrentGameSpacePosition().plus(new Vec2d(0, gameObject.getTransform().getSize().y)));
//        return collisionShape;
//    }

    @Override
    public void onCollide(Collision collision) {
        // Colliding with Ground
        if (collision.getCollidedObject().hasComponentTag("tile")) {
            TileComponent t = (TileComponent) collision.getCollidedObject().getComponent("tile");
            if (grounds.contains(t.getTileType())) {
                p.setIsGrounded(true);
            }
        }
    }

    @Override
    public void onNotCollide() {
        p.setIsGrounded(false);
    }

    public static final String tag = "physicsGrounded";

    @Override
    public String getTag() {
        return tag;
    }
}
