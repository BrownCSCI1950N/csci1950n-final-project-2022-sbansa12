package engine.UI;

import engine.Screen;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class UIViewport extends UIElement {
    public UIViewport(Screen screen, UIElement parent, Vec2d position, Color color) {
        super(screen, parent, position, color);
    }
}
