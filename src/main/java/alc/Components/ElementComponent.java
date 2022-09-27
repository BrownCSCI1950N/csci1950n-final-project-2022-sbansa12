package alc.Components;

import alc.Element;
import engine.Components.Component;
import javafx.scene.canvas.GraphicsContext;

public class ElementComponent extends Component {

    public Element e;

    public ElementComponent(Element e) {
        this.e = e;
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
