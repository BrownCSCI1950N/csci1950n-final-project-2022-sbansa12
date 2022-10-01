package engine;

import engine.Systems.DrawSystem;
import engine.Systems.System;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a game world. Comes preloaded with the Draw System.
 */
public class GameWorld {

    private final List<System> systems;
    private final Map<InputEvents, Vec2d> inputEvents;

    public GameWorld() {
        this.systems = new ArrayList<>();
        this.inputEvents = new HashMap<>();
        systems.add(new DrawSystem(this));
    }

    public void addGameObject(GameObject gameObject) {
        for (System s: systems) {
            s.addGameObject(gameObject);
        }
    }

    public void removeGameObject(GameObject gameObject) {
        for (System s: systems) {
            s.removeGameObject(gameObject);
        }
    }

    public void addSystem(System system) {
        systems.add(system);
    }

    public Vec2d findInputEvent(InputEvents i) {
        return inputEvents.get(i);
    }

    public void onTick(long nanosSinceLastTick) {
        for (System s: systems) {
            s.tick(nanosSinceLastTick);
        }
    }

    public void onLateTick() {

    }

    private List<String> systemsDrawTags = List.of("sprite");

    public void setSystemsTagDraw(List<String> input) {
        this.systemsDrawTags = input;
    }

    public void draw(GraphicsContext g) {
        for (System s: systems) {
            if (systemsDrawTags.contains(s.getRelevantTag())) {
                s.draw(g);
            }
        }
    }

    public void reset() {}

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyTyped(KeyEvent e) {

    }

    public void onKeyPressed(KeyEvent e) {

    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyReleased(KeyEvent e) {

    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseClicked(MouseEvent e) {
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMousePressed(Vec2d e) {
        inputEvents.remove(InputEvents.ONMOUSERELEASED);
        inputEvents.remove(InputEvents.ONMOUSEDRAGGED);
        inputEvents.put(InputEvents.ONMOUSEPRESSED, e);
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseReleased(Vec2d e) {
        inputEvents.remove(InputEvents.ONMOUSEPRESSED);
        inputEvents.remove(InputEvents.ONMOUSEDRAGGED);
        inputEvents.put(InputEvents.ONMOUSERELEASED, e);
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseDragged(Vec2d e) {
        inputEvents.put(InputEvents.ONMOUSEDRAGGED, e);
    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseMoved(MouseEvent e){

    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    public void onMouseWheelMoved(ScrollEvent e) {

    }

    /**
     * Called when the window's focus is changed.
     * @param newVal	a boolean representing the new focus state
     */
    public void onFocusChanged(boolean newVal) {

    }

    public void onResize(Vec2d newSize) {

    }

    public void onShutdown() {

    }

    public void onStartup() {

    }
}
