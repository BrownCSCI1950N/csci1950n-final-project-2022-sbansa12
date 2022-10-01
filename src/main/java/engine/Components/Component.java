package engine.Components;

import javafx.scene.canvas.GraphicsContext;

public interface Component {
    void tick(long nanosSinceLastTick);
    void lateTick();
    void draw(GraphicsContext g);
    String getTag();
}
