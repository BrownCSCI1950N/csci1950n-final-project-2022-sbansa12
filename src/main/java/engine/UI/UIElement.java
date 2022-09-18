package engine.UI;

import engine.Screen;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class UIElement {

    // Screen a UIElement is Associated With
    private final Screen screen;
    private final UIElement parent;
    private final List<UIElement> children;
    protected Vec2d originalPosition;
    protected Vec2d currentPosition;

    protected Color color;

    public UIElement(Screen screen, UIElement parent, Vec2d position, Color color) {
        this.screen = screen;
        this.parent = parent;
        this.originalPosition = new Vec2d(position);
        this.currentPosition = new Vec2d(position);
        this.color = color;
        this.children = new ArrayList<>();
    }

    /**
     * Called to add children to the UIElement
     * @param e - single UIElement to add as child
     */
    public void addChildren(UIElement e) {
        children.add(e);
    }

    public void reset() {
        for (UIElement uiElement : children) {
            uiElement.reset();
        }
    }

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    public void onTick(long nanosSincePreviousTick) {
        for (UIElement child: children) {
            child.onTick(nanosSincePreviousTick);
        }
    }

    /**
     * Called after onTick().
     */
    protected void onLateTick() {
        // Don't worry about this method until you need it. (It'll be covered in class.)
    }

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    public void onDraw(GraphicsContext g) {
        for (UIElement child: children) {
            child.onDraw(g);
        }
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyTyped(KeyEvent e) {
        for (UIElement child: children) {
            child.onKeyTyped(e);
        }
    }

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyPressed(KeyEvent e) {

    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyReleased(KeyEvent e) {

    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseClicked(MouseEvent e) {
        for (UIElement child: children) {
            child.onMouseClicked(e);
        }
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMousePressed(MouseEvent e) {

    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseReleased(MouseEvent e) {

    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseDragged(MouseEvent e) {

    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseMoved(MouseEvent e) {
        for (UIElement child: children) {
            child.onMouseMoved(e);
        }
    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    protected void onMouseWheelMoved(ScrollEvent e) {

    }

    /**
     * Called when the window's focus is changed.
     * @param newVal	a boolean representing the new focus state
     */
    protected void onFocusChanged(boolean newVal) {

    }

    /**
     * Called when the window is resized.
     * @param newSize	the new size of the drawing area. (scale factor)
     */
    public void onResize(Vec2d newSize) {
        for (UIElement child: children) {
            child.onResize(newSize);
        }
    }

    /**
     * Called when the app is shutdown.
     */
    protected void onShutdown() {

    }

    /**
     * Called when the app is starting up.s
     */
    protected void onStartup() {

    }
}
