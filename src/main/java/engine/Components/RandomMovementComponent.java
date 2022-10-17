package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.math.BigDecimal;
import java.util.Random;

public class RandomMovementComponent implements Component {
    GameObject gameObject;
    Random rand;
    BigDecimal count;
    BigDecimal timer;

    public RandomMovementComponent(GameObject gameObject, Random rand, BigDecimal timer) {
        this.gameObject = gameObject;
        this.rand = rand;
        this.count = new BigDecimal("0");
        this.timer = timer;
    }

    private boolean randBoolean(){
        return rand.nextFloat() <= (float) 0.5;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        count = count.add(new BigDecimal(nanosSinceLastTick));
        if (count.compareTo(timer) < 0) {
            return;
        }
        count = new BigDecimal("0");
        Vec2d currentPosition = this.gameObject.getTransform().getCurrentGameSpacePosition();
        Vec2d currentSize = this.gameObject.getTransform().getSize();
        if (randBoolean()) {
            // x
            if (randBoolean()) {
                this.gameObject.getTransform().setCurrentGameSpacePosition(currentPosition.plus(new Vec2d(currentSize.x, 0)));
            } else {
                this.gameObject.getTransform().setCurrentGameSpacePosition(currentPosition.plus(new Vec2d(-currentSize.x, 0)));
            }
        } else {
            // y
            if (randBoolean()) {
                this.gameObject.getTransform().setCurrentGameSpacePosition(currentPosition.plus(new Vec2d(0, currentSize.y)));
            } else {
                this.gameObject.getTransform().setCurrentGameSpacePosition(currentPosition.plus(new Vec2d(0, -currentSize.y)));
            }
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "randomMovement";
    }
}
