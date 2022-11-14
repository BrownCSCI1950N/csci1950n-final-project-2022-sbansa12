package engine.Components;

import Pair.Pair;
import engine.InputEvents;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

public class MouseClickComponent implements Component {
    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    public static final String tag = null;

    @Override
    public String getTag() {
        return tag;
    }

    public void script(Pair<InputEvents, Vec2d> input) {}
}
