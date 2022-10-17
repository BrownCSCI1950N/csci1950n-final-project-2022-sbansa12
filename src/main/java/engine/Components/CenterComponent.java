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
        viewport.setCurrentGamePointCenter(gameObject.getTransform().getCurrentGameSpacePosition());
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "center";
    }
}
