package engine.Components;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class TransformComponent extends Component {

    private Vec2d gameSpacePosition;
    private final Vec2d currentSize;

    public TransformComponent(Vec2d gameSpacePosition, Vec2d size) {
        this.gameSpacePosition = gameSpacePosition;
        this.currentSize = size;
    }

    public Vec2d getGameSpacePosition() {
        return gameSpacePosition;
    }

    public void setGameSpacePosition(Vec2d newGameSpacePosition) {
       this.gameSpacePosition = newGameSpacePosition;
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