package engine.Components;

import Pair.Pair;
import engine.InputEvents;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

/**
 * Class that acts as a general type for all components that will do things with keys
 */
public abstract class KeysComponent implements Component {
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

    public void script(Pair<InputEvents, List<KeyCode>> input) {}
}
