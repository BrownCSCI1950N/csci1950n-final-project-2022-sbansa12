package engine.Components;

import Pair.Pair;
import engine.GameObject;
import engine.InputEvents;
import engine.Shape.Ray;
import engine.Systems.CollisionSystem;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.util.List;

public class ShootRayComponent extends MouseClickComponent {

    CollisionSystem cs;
    GameObject gO;
    List<Integer> layers;
    Boolean rayShot;
    BigDecimal timer;
    BigDecimal count = new BigDecimal("0");
    Boolean timerStarted;
    double t;
    Ray rCurrent;
    double lineWidth;
    Color lineColor;

    public ShootRayComponent(CollisionSystem cs, GameObject gO, List<Integer> layers, BigDecimal timer, double lineWidth, Color lineColor) {
        this.cs = cs;
        this.gO = gO;
        this.layers = layers;
        this.rayShot = false;
        this.timerStarted = false;
        this.timer = timer;
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        if (rayShot) {
            count = new BigDecimal("0");
            rayShot = false;
            timerStarted = true;
        }

        if (timerStarted) {
            count = count.add(new BigDecimal(nanosSinceLastTick));
            if (count.compareTo(timer) < 0) {
                return;
            }
            count = new BigDecimal("0");
            timerStarted = false;
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {
        if (timerStarted) {
            Vec2d start = rCurrent.src;
            Vec2d end = rCurrent.src.plus(rCurrent.dir.smult(t));
            g.setLineWidth(lineWidth);
            g.setStroke(lineColor);
            g.strokeLine(start.x, start.y, end.x, end.y);
        }
    }

    public static final String tag = "shootRay";
    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void script(Pair<InputEvents, Vec2d> input) {
        CollisionComponent c = (CollisionComponent) gO.getComponent("collision");

        Vec2d src = c.getCollisionShape().getCenter();
        Vec2d dir = input.getRight().minus(src).normalize();
        Ray r = new Ray(src, dir);

        Collision collision = cs.checkRayCollision(r, this.layers);
        if (collision != null) {
            rayShot = true;
            t = collision.getT();
            rCurrent = r;
            action(r, collision.getCollidedObject());
        }
    }

    public void action(Ray r, GameObject gO) {}
}
