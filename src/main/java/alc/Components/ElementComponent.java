package alc.Components;

import alc.Element;
import engine.Components.Component;
import javafx.scene.canvas.GraphicsContext;

public class ElementComponent implements Component {

    public Element ele;

    public ElementComponent(Element e) {
        this.ele = e;
    }

    public Element getElement() {
        return ele;
    }

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
        return "element";
    }
}
