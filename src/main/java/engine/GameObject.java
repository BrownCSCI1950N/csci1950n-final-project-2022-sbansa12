package engine;

import engine.Components.Component;
import engine.Components.TransformComponent;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class GameObject {
    private final Map<String, Component> _components;
    private final TransformComponent transformComponent;
    public Integer zIndex;
    String name;
    public GameObject(TransformComponent t,  Integer zIndex) {
        this.name = "";
        this._components = new HashMap<>();
        this.transformComponent = t;
        this.zIndex = zIndex;
    }

    public GameObject(GameObject g,  Integer zIndex) {
        this.name = g.name;
        this._components = g._components;
        this.transformComponent = g.transformComponent;
        this.zIndex = zIndex;
    }

    public void addComponent(Component c) {
        _components.put(c.getTag(), c);
    }
    public void removeComponent(Component c) {
        _components.remove(c.getTag());
    }
    public void removeComponent(String c) {
        _components.remove(c);
    }

    /**
     * Function to find the component in a game object using the component's tag
     * @param tag - a string which is the tag for the component
     * @return - component if found, otherwise null
     */
    public Component getComponent(String tag) {
        return _components.get(tag);
    }

    public TransformComponent getTransform() {
        return transformComponent;
    }

    public boolean hasComponentTag(String tag) {
        return _components.containsKey(tag);
    }

    public void tick(long t) {
        for (Component c: _components.values()) {
            c.tick(t);
        }
    }
    public void lateTick() {
        for (Component c: _components.values()) {
            c.lateTick();
        }
    }

    public void draw(GraphicsContext g) {
        for (Component c: _components.values()) {
            c.draw(g);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameObject) {
            GameObject gO = (GameObject) obj;
            return gO._components.equals(this._components)
                    && gO.transformComponent.equals(this.transformComponent);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(_components, transformComponent);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (name.equals("")) {
            return super.toString();
        } else {
            return "(Name: " + name + " Position: " + transformComponent.getCurrentGameSpacePosition() + ")";
        }
    }
}
