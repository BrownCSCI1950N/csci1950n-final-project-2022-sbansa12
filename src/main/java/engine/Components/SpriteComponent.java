package engine.Components;

import engine.GameObject;
import engine.Sprite;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class SpriteComponent implements Component {
    private final GameObject gameObject;
    private final Sprite sprite;

    private final Vec2d spritePosition;

    public SpriteComponent(GameObject gameObject, Sprite sprite, Vec2d spritePosition) {
        this.gameObject = gameObject;
        this.sprite = sprite;
        this.spritePosition = spritePosition;
    }

    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {
        Vec2d gameSpacePosition = gameObject.getTransform().getGameSpacePosition();
        sprite.draw(g, gameSpacePosition, gameObject.getTransform().getSize(), this.spritePosition);
    }

    @Override
    public String getTag() {
        return "sprite";
    }
}
