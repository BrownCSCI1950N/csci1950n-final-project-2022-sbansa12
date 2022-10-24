package engine.Components;

import engine.GameObject;
import engine.UI.Viewport;
import javafx.scene.canvas.GraphicsContext;

public class CenterComponent implements Component {

    private final GameObject gameObject;
    private final Viewport viewport;

    public CenterComponent(GameObject gameObject, Viewport viewport) {
        this.gameObject = gameObject;
        this.viewport = viewport;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
    }

    @Override
    public void lateTick() {
        viewport.setCurrentGamePointCenter(gameObject.getTransform().getCurrentGameSpacePosition().plus(gameObject.getTransform().getSize().sdiv(2)));
    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "center";
    }
}
