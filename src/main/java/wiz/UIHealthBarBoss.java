package wiz;

import engine.FontWrapper;
import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

public class UIHealthBarBoss extends UIElement {
    public UIHealthBarBoss(Screen screen, UIElement parent, Vec2d position, Vec2d size, double border, Color containerColor, Color maxBarColor, Color barColor, Vec2d positionText, String text, Color textColor, FontWrapper textFont) {
        super(screen, parent, position, null);

        UIElement container = new UIRectangle(screen, this, position, size, containerColor);
        UIElement maxBar = new UIRectangle(screen, this, new Vec2d(position.x + border, position.y + border), new Vec2d(size.x - (border * 2), size.y - (border * 2)), maxBarColor);
        UIElement bar = new UIRectangle(screen, this, new Vec2d(position.x + border, position.y + border), new Vec2d(size.x - (border * 2), size.y - (border * 2)), barColor) {

            private final double originalBarSize = size.x - (2 * border);
            private double currentBarSize = size.x - (2 * border);
            @Override
            public void onTick(long nanosSincePreviousTick) {
                double scale = getScale();
                this.currentSize = new Vec2d(currentBarSize * scale, currentSize.y);


                super.onTick(nanosSincePreviousTick);
            }

            @Override
            public void onResize(Vec2d newSize) {
                currentBarSize = originalBarSize * newSize.x;

                super.onResize(newSize);
            }
        };
        UIElement textBar = new UIText(screen, this, positionText, text, textColor, textFont);

                this.addChildren(container);
        this.addChildren(maxBar);
        this.addChildren(bar);
        this.addChildren(textBar);
    }

    public double getScale() {
        return 1;
    }
}
