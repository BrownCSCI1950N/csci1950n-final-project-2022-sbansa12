package engine.UI;

import engine.Screen;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UIRectangle extends UIElement {

    protected final Vec2d originalSize;
    protected Vec2d currentSize;
    protected Vec2d arcSize;

    public UIRectangle(Screen screen, UIElement parent, Vec2d position, Vec2d size, Color color) {
        super(screen, parent, position, color);
        this.originalSize = new Vec2d(size);
        this.currentSize = new Vec2d(size);
        this.arcSize = null;
    }

    public UIRectangle(Screen screen, UIElement parent, Vec2d position, Vec2d size, Color color, Vec2d arcSize) {
        super(screen, parent, position, color);
        this.originalSize = new Vec2d(size);
        this.currentSize = new Vec2d(size);
        this.arcSize = new Vec2d(arcSize);
    }

    public Vec2d getCurrentSize() {
        return currentSize;
    }

    @Override
    public void onDraw(GraphicsContext g) {
        g.setFill(color);
        if (this.arcSize == null) {
            g.fillRect(currentPosition.x, currentPosition.y, currentSize.x, currentSize.y);
        } else {
            g.fillRoundRect(currentPosition.x, currentPosition.y, currentSize.x, currentSize.y, arcSize.x, arcSize.y);
        }
        super.onDraw(g);
    }

    @Override
    public void onResize(Vec2d newSize) {
        this.currentPosition = originalPosition.pmult(newSize);
        this.currentSize = originalSize.pmult(newSize);

        super.onResize(newSize);
    }
}
