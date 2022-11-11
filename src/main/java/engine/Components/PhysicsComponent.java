package engine.Components;

import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.math.BigDecimal;
import java.util.List;

public class PhysicsComponent implements Component {
    public final double restitution;
    final double mass;
    public Vec2d vel;
    Vec2d impulse;
    Vec2d force;
    boolean isGrounded;
    boolean permanentGravityOff;
    Vec2d gravityUp;
    Vec2d gravityDown;
    Vec2d gravityLowJump;
    BigDecimal count;
    GameObject gameObject;
    boolean isStaticPhysics;
//    boolean startCount;

    public PhysicsComponent(GameObject gameObject, boolean permanentGravityOff, List<Vec2d> gravity, double mass, double restitution, boolean isStaticPhysics) {
        this.gameObject = gameObject;
        this.isGrounded = true;
        this.permanentGravityOff = permanentGravityOff;
        // [UP, DOWN, LowJump]
        if (permanentGravityOff) {
            assert gravity == null;
        } else {
            assert gravity.size() > 0;
            assert gravity.size() < 4;
            this.gravityUp = gravity.get(0);
            this.gravityDown = gravity.get(1);
            this.gravityLowJump = gravity.get(2);
        }
        this.count = new BigDecimal("0");

        this.vel = new Vec2d(0,0);
        this.force = new Vec2d(0,0);
        this.impulse = new Vec2d(0,0);
        this.mass = mass;
        assert restitution >= 0.0;
        assert restitution <= 1.0;
        this.restitution = restitution;
        this.isStaticPhysics = isStaticPhysics;

//        this.startCount = false;
    }

    private double convertSeconds(long nano) {
        return new BigDecimal(nano).divide(new BigDecimal("1000000000")).doubleValue();
    }

    @Override
    public void tick(long nanosSinceLastTick) {
//        if (startCount) {
//            count = count.add(new BigDecimal(nanosSinceLastTick));
//            if (count.compareTo(accTimer) >= 0) {
//                acc = new Vec2d(0,0);
//                count = new BigDecimal("0");
//                startCount = false;
//            }
//        }

        physicsTick(nanosSinceLastTick);
//        while ((count = count.subtract(physicsTickTimer)).compareTo(physicsTickTimer) >= 0) {
//            physicsTick();
//        }
//        count = new BigDecimal("0");
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    public static final String tag = "physics";

    @Override
    public String getTag() {
        return tag;
    }

    void physicsTick(long nano) {
        if (!permanentGravityOff) {
            if (!isGrounded) {
                Vec2d gravity = gravityUp;
                if (gravityDown != null) {
                    if (vel.y < 0) {
//                        gravity = gravityUp;
                        if (gravityLowJump != null) {
                            if (gameObject.hasComponentTag(MovePhysicsComponent.tag)) {
                                MovePhysicsComponent c = (MovePhysicsComponent) gameObject.getComponent(MovePhysicsComponent.tag);
                                if (!c.getOnceHappenedJump()) {
                                    gravity = gravityLowJump;
                                }
                            }
                        }
                    } else {
                        gravity = gravityDown;
                    }
                }
                applyForce(gravity);
            } else {
                vel = new Vec2d(vel.x, 0);
            }
        }

        vel = vel.plus(force.sdiv(mass).smult(convertSeconds(nano)).plus(impulse.sdiv(mass)));
        if(gameObject.hasComponentTag("tile")) {
            if (((TileComponent) gameObject.getComponent("tile")).getTileType().equals(TileType.BOX0)) {
                System.out.println("Force: " + force);
                System.out.println("Impulse: " + impulse);
                System.out.println("Velocity: " + vel);
            }
        }
        Vec2d pos = gameObject.getTransform().getCurrentGameSpacePosition();
        gameObject.getTransform().setCurrentGameSpacePositionNoVelocity(pos.plus(vel.smult(convertSeconds(nano))));
        force = new Vec2d(0, 0);
        impulse = new Vec2d(0, 0);
    }

    public void setIsGrounded(boolean gr) {
        this.isGrounded = gr;
    }

    void applyForce(Vec2d f) {
        if (!isStaticPhysics) {
            force = force.plus(f);
        }
    }

    void applyImpulse(Vec2d p) {
        if (!isStaticPhysics) {
            impulse = impulse.plus(p);
        }
    }
}
