package engine.UI;

import engine.FontWrapper;
import engine.Screen;
import engine.support.FontMetrics;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class UIText extends UIElement{

    protected String text;
    private final FontWrapper originalFont;
    protected final FontWrapper currentFont;
    public UIText(Screen screen, UIElement parent, Vec2d position, String text, Color color, FontWrapper font) {
        super(screen, parent, position, color);
        this.text = text;
        this.originalFont = font.copy();
        this.currentFont = font.copy();
    }

    @Override
    public void onDraw(GraphicsContext g) {
        g.setFont(currentFont.font());
        g.setFill(this.color);
        g.fillText(this.text, this.currentPosition.x, this.currentPosition.y);
    }

    @Override
    public void onResize(Vec2d newSize) {
        this.currentPosition = originalPosition.pmult(newSize);
        this.currentFont.setSize(originalFont.getSize() * newSize.x);

        super.onResize(newSize);
    }
}
