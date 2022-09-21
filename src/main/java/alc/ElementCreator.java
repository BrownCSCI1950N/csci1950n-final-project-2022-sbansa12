package alc;

import alc.Components.ElementComponent;
import engine.Components.TransformComponent;
import engine.GameObject;

public class ElementCreator {
    public static GameObject makeElement(Element e) {
        GameObject o = new GameObject(new TransformComponent(), 1);
        o.addComponent(new ElementComponent(e));
        // ...
        return o;
    }
}
