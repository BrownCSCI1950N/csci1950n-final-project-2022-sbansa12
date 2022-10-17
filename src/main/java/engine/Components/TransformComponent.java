package engine.Components;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class TransformComponent implements Component {
    private Vec2d oldGameSpacePosition;
    private Vec2d currentGameSpacePosition;
    private Vec2d currentGameSpacePositionVelocity;
    private final Vec2d currentSize;

    public TransformComponent(Vec2d gameSpacePosition, Vec2d size) {
        this.oldGameSpacePosition = gameSpacePosition;
        this.currentGameSpacePosition = gameSpacePosition;
        this.currentGameSpacePositionVelocity = gameSpacePosition;
        this.currentSize = size;
    }

    public Vec2d getCurrentGameSpacePosition() {
        return currentGameSpacePosition;
    }

    /**
     * Sets the current game space position of this component to the one entered. Also updates the old game space position, used to calculate velocity.
     * @param newCurrentGameSpacePosition - the new game space position
     */
    public void setCurrentGameSpacePosition(Vec2d newCurrentGameSpacePosition) {
        this.oldGameSpacePosition = this.currentGameSpacePosition;
        this.currentGameSpacePosition = newCurrentGameSpacePosition;
        this.currentGameSpacePositionVelocity = newCurrentGameSpacePosition;
    }

    /**
     * Sets the current game space position of this component to the one entered. Does not affect velocity.
     * @param newCurrentGameSpacePosition - the new game space position
     */
    public void setCurrentGameSpacePositionNoVelocity(Vec2d newCurrentGameSpacePosition) {
        this.currentGameSpacePosition = newCurrentGameSpacePosition;
    }

    public Vec2d getVelocity() {
        return currentGameSpacePositionVelocity.minus(oldGameSpacePosition);
    }

    public Vec2d getSize() {
        return currentSize;
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
        return "transform";
    }
}