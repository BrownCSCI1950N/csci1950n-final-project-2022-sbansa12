package engine.Components;

import javafx.scene.canvas.GraphicsContext;

public abstract class AIComponent implements Component {

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
        return null;
    }
}
