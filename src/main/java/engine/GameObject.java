package engine;

import engine.Components.Component;
import engine.Components.TransformComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private final List<Component> _components;
    private TransformComponent transformComponent;

    Integer zIndex;

    public GameObject(TransformComponent t,  Integer zIndex) {
        this._components = new ArrayList<>();
        this.transformComponent = t;
        this.zIndex = zIndex;
    }

    public void addComponent(Component c) {
        _components.add(c);
    }

    public void removeComponent(Component c) {
        _components.remove(c);
    }

    /**
     * Function to find the component in a game object using the component's tag
     * @param tag - a string which is the tag for the component
     * @return - component if found, otherwise null
     */
    public Component getComponent(String tag) {
        for (Component c: _components) {
            if (c.getTag().equals(tag)) {
                return c;
            }
        }
        return null;
    }

    public TransformComponent getTransform() {
        return transformComponent;
    }

    public void setTransform(TransformComponent t) {
        transformComponent = t;
    }

    public void tick(long t) {
        for (Component c: _components) {
            c.tick(t);
        }
    }
    public void lateTick() {
        for (Component c: _components) {
            c.lateTick();
        }
    }

    public void draw(GraphicsContext g) {
        for (Component c: _components) {
            c.draw(g);
        }
    }
}
