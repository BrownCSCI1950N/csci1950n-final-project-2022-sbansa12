package engine.UI;

import engine.Screen;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UIRectangle extends UIElement {

    private final Vec2d originalSize;
    protected Vec2d currentSize;

    public UIRectangle(Screen screen, UIElement parent, Vec2d position, Vec2d size, Color color) {
        super(screen, parent, position, color);
        this.originalSize = size;
        this.currentSize = size;
    }

    @Override
    public void onDraw(GraphicsContext g) {
        g.setFill(color);
        g.fillRect(currentPosition.x, currentPosition.y, currentSize.x, currentSize.y);
        super.onDraw(g);
    }

    @Override
    public void onResize(Vec2d newSize) {
        this.currentPosition = originalPosition.pmult(newSize);
        this.currentSize = originalSize.pmult(newSize);

        super.onResize(newSize);
    }
}
