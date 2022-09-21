package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.Map;

public class GameWorld {

    Map<String, System> systems;
    Map<InputEvents, Boolean> inputEvents;

    // draw system
    // tick system

    public GameWorld() {

    }

    public void tick(long nanosSinceLastTick) {

    }
    public void draw(GraphicsContext g) {

    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    protected void onKeyTyped(KeyEvent e) {

    }

    public void onKeyPressed(KeyEvent e) {

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
    protected void onMouseClicked(MouseEvent e) {
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
    public void onMouseDragged(MouseEvent e) {

    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    protected void onMouseMoved(MouseEvent e){

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
}
