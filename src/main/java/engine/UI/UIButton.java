package engine.UI;

import engine.FontWrapper;
import engine.Screen;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Objects;

public class UIButton extends UIElement {

    public Vec2d originalSize;
    public Vec2d currentSize;
    public final Vec2d arcSize;
    protected final String buttonText;

    private final FontWrapper originalFont;
    public final FontWrapper currentFont;

    public final Color colorText;
    private final Vec2d originalPositionText;
    protected Vec2d currentPositionText;

    /**
     * UIButton Constructor
     * @param screen - the screen this UIButton is a part of
     * @param parent - the parent UIElement of this UIButton
     * @param position - position of this UIButton on the screen
     * @param size  - size of this UIButton on the screen
     * @param color - color of this UIButton on the screen
     * @param arcSize - how round to make the corners of this UIButton
     * @param buttonText - text written on this UIButton
     * @param positionText - position of the text relative to the bottom-left corner of this UIButton
     * @param colorText - color of the text on this UIButton
     * @param font - font of the text on this UIButton
     */
    public UIButton(Screen screen, UIElement parent, Vec2d position, Vec2d size, Color color, Vec2d arcSize, String buttonText, Vec2d positionText, Color colorText, FontWrapper font) {
        super(screen, parent, position, color);
        this.originalSize = size;
        this.currentSize = size;
        this.arcSize = new Vec2d(arcSize);
        this.originalPositionText = position.plus(0, size.y).plus(positionText.x, -positionText.y);
        this.currentPositionText = position.plus(0, size.y).plus(positionText.x, -positionText.y);
        this.buttonText = buttonText;
        this.originalFont = font.copy();
        this.currentFont = font.copy();
        this.colorText = colorText;
    }

    @Override
    public void onDraw(GraphicsContext g) {
        g.setFill(color);
        g.fillRoundRect(currentPosition.x, currentPosition.y, currentSize.x, currentSize.y, arcSize.x, arcSize.y);
        if (!Objects.equals(buttonText, "")) {
            g.setFont(this.currentFont.font());
            g.setFill(this.colorText);
            g.fillText(this.buttonText, this.currentPositionText.x, this.currentPositionText.y);
        }
        super.onDraw(g);
    }

    @Override
    public void onResize(Vec2d newSize) {
        this.currentPosition = originalPosition.pmult(newSize);
        this.currentSize = originalSize.pmult(newSize);
        if (!Objects.equals(buttonText, "")) {
            this.currentPositionText = originalPositionText.pmult(newSize);
            this.currentFont.setSize(originalFont.getSize() * newSize.x);
        }

        super.onResize(newSize);
    }
}
