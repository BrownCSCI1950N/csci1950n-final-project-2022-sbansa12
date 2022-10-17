package engine.Components;

import engine.GameObject;
import engine.Sprite;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class SpriteComponent implements Component {
    final GameObject gameObject;
    private final Sprite sprite;
    protected Vec2d currentSpritePosition;

    public SpriteComponent(GameObject gameObject, Sprite sprite, Vec2d currentSpritePosition) {
        this.gameObject = gameObject;
        this.sprite = sprite;
        this.currentSpritePosition = currentSpritePosition;
    }

    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {
        Vec2d gameSpacePosition = gameObject.getTransform().getCurrentGameSpacePosition();
        sprite.draw(g, gameSpacePosition, gameObject.getTransform().getSize(), this.currentSpritePosition);
    }

    @Override
    public String getTag() {
        return "sprite";
    }
}
