package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;
import javafx.scene.canvas.GraphicsContext;

public class CollisionComponent implements Component {
    protected final GameObject gameObject;
    protected Shape collisionShape;
    protected final boolean allowOverlap;
    protected final boolean IsStaticApplyMTV;
    protected Integer layer;
    protected PhysicsComponent p;

    public CollisionComponent(GameObject gameObject, Shape s, Integer layer, boolean allowOverlap, Boolean IsStaticApplyMTV) {
        this.gameObject = gameObject;
        this.collisionShape = s;
        this.layer = layer;
        this.allowOverlap = allowOverlap;
        this.IsStaticApplyMTV = IsStaticApplyMTV;
        this.p = null;
    }

    public CollisionComponent(GameObject gameObject, Shape s, Integer layer, boolean allowOverlap, boolean IsStaticApplyMTV, PhysicsComponent p) {
        this.gameObject = gameObject;
        this.collisionShape = s;
        this.layer = layer;
        this.allowOverlap = allowOverlap;
        this.IsStaticApplyMTV = IsStaticApplyMTV;
        this.p = p;
    }

    public Shape getCollisionShape(){
        collisionShape.updatePosition(gameObject.getTransform().getCurrentGameSpacePosition());
        collisionShape.setVelocity(gameObject.getTransform().getVelocity());
        return collisionShape;
    }

    public boolean isStaticApplyMTV() {
        return IsStaticApplyMTV;
    }
    public boolean allowOverlap() {
        return allowOverlap;
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

    public static final String tag = "collision";

    @Override
    public String getTag() {
        return tag;
    }

    public void onCollide(Collision collision) {
        if (p != null) {
            if (collision.getCollidedObject().hasComponentTag(PhysicsComponent.tag)) {
                if (collision.getMTV() != null) {
                    if (!(p.isStaticPhysics.get(0) && p.isStaticPhysics.get(1))) {
                        PhysicsComponent bPhysics = (PhysicsComponent) collision.getCollidedObject().getComponent(PhysicsComponent.tag);

                        double COR = Math.sqrt(p.restitution * bPhysics.restitution);

                        double u_a = p.vel.dot(collision.getMTV().normalize());
                        double u_b = bPhysics.vel.dot(collision.getMTV().normalize());

                        double m_a = p.mass;
                        double m_b = bPhysics.mass;

                        // use impulse formula
                        double I_a;
                        if (!(bPhysics.isStaticPhysics.get(0) && bPhysics.isStaticPhysics.get(1))) {
                            I_a = ((m_a * m_b) * (u_b - u_a) * (1+COR))/(m_a + m_b);
                        } else {
                            // B is a Static Object
                            I_a = m_a * (u_b - u_a) * (1+COR);
                        }

                        // apply impulse
                        p.applyImpulse(collision.getMTV().normalize().smult(I_a));
                    }
                }
            }
        }
    }

    // Called If There Is No Collision Between Two Different Game Objects
    public void onNotCollide() {
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
