package engine.Components;

import javafx.scene.canvas.GraphicsContext;

public abstract class Component<T> {
    public abstract void tick(long nanosSinceLastTick);
    public abstract void lateTick();
    public abstract void draw(GraphicsContext g);
    public abstract String getTag();
    public abstract void script(T input);
}
