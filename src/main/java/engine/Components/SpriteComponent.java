package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteComponent extends Component {
    private final GameObject gameObject;
    private final Image image;

    public SpriteComponent(GameObject gameObject, Image image) {
        this.gameObject = gameObject;
        this.image = image;
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
        g.drawImage(image, gameSpacePosition.x, gameSpacePosition.y);
    }

    @Override
    public String getTag() {
        return "sprite";
    }
}
