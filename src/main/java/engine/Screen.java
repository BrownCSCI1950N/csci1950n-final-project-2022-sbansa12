package engine;

import engine.UI.UIElement;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {

    protected List<UIElement> uiElements;
    protected final Application engine;

    public Screen(Application engine) {
        this.engine = engine;
        uiElements = new ArrayList<>();
    }

    public void setActiveScreen(String name) {
        engine.setActiveScreen(name);
    }

    protected void reset() {
        for (UIElement uiElement : uiElements) {
            uiElement.reset();
        }
    }

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    protected void onTick(long nanosSincePreviousTick) {
        for (UIElement uiElement : uiElements) {
            uiElement.onTick(nanosSincePreviousTick);
        }
    }

    /**
     * Called after onTick().
     */
    protected void onLateTick() {
        for (UIElement uiElement : uiElements) {
            uiElement.onLateTick();
        }
    }

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    protected void onDraw(GraphicsContext g) {
        for (UIElement uiElement : uiElements) {
            uiElement.onDraw(g);
        }
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyTyped(KeyEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onKeyTyped(e);
        }
    }

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyPressed(KeyEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onKeyPressed(e);
        }
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyReleased(KeyEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onKeyReleased(e);
        }
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseClicked(MouseEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onMouseClicked(e);
        }
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMousePressed(MouseEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onMousePressed(e);
        }
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseReleased(MouseEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onMouseReleased(e);
        }
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseDragged(MouseEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onMouseDragged(e);
        }
    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseMoved(MouseEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onMouseMoved(e);
        }
    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    protected void onMouseWheelMoved(ScrollEvent e) {
        for (UIElement uiElement : uiElements) {
            uiElement.onMouseWheelMoved(e);
        }
    }

    /**
     * Called when the window's focus is changed.
     * @param newVal	a boolean representing the new focus state
     */
    protected void onFocusChanged(boolean newVal) {
        for (UIElement uiElement : uiElements) {
            uiElement.onFocusChanged(newVal);
        }
    }

    /**
     * Called when the window is resized.
     * @param newSize	the new size of the drawing area. (scale factor)
     */
    protected void onResize(Vec2d newSize) {
        for (UIElement uiElement : uiElements) {
            uiElement.onResize(newSize);
        }
    }

    /**
     * Called when the app is shutdown.
     */
    protected void onShutdown() {
        for (UIElement uiElement : uiElements) {
            uiElement.onShutdown();
        }
    }

    /**
     * Called when the app is starting up.s
     */
    protected void onStartup() {
        for (UIElement uiElement : uiElements) {
            uiElement.onStartup();
        }
    }
}
