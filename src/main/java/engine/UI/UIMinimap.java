package engine.UI;

import engine.Screen;
import engine.support.Vec2d;

public class UIMinimap extends UIElement {
    protected final Vec2d originalSize;
    protected Vec2d currentSize;
    protected Vec2d arcSize;

    public UIMinimap(Screen screen, UIElement parent, Vec2d position, Vec2d size) {
        super(screen, parent, position, null);
        this.originalSize = new Vec2d(size);
        this.currentSize = new Vec2d(size);
        this.arcSize = null;
    }

    @Override
    public void onResize(Vec2d newSize) {
        this.currentPosition = originalPosition.pmult(newSize);
        this.currentSize = originalSize.pmult(newSize);

        super.onResize(newSize);
    }
}
