package engine;

import engine.Systems.DrawSystem;
import engine.Systems.System;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.util.*;

/**
 * Class that represents a game world. Comes preloaded with the Draw System.
 */
public class GameWorld {

    private final List<System> systems;
    private final Map<InputEvents, Vec2d> inputEventsMouse;
    private final Map<InputEvents, List<KeyCode>> inputEventsKeys;

    public GameWorld() {
        this.systems = new ArrayList<>();
        this.inputEventsMouse = new HashMap<>();
        this.inputEventsKeys = new HashMap<>();
        this.inputEventsKeys.put(InputEvents.ONKEYRELEASED, new LinkedList<>());
        this.inputEventsKeys.put(InputEvents.ONKEYPRESSED, new LinkedList<>());
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

    public void appendSystem(System system) {
        systems.add(system);
    }

    public void prependSystem(System system) {
        systems.add(0,system);
    }

    public System getSystem(String name) {
        for (System s: systems) {
            if (s.name().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public Vec2d findInputEventMouse(InputEvents i) {
        return inputEventsMouse.get(i);
    }

    public List<KeyCode> findInputEventKeys(InputEvents i) {
        return inputEventsKeys.get(i);
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
            for (String relevantTag: s.getRelevantTags()) {
                if (systemsDrawTags.contains(relevantTag)) {
                    s.draw(g);
                    break;
                }
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
        List<KeyCode> keyCodesToRemove = inputEventsKeys.get(InputEvents.ONKEYRELEASED);
        keyCodesToRemove.remove(e.getCode());

        List<KeyCode> keyCodesToAdd = inputEventsKeys.get(InputEvents.ONKEYPRESSED);
        if (!keyCodesToAdd.contains(e.getCode())) {
            keyCodesToAdd.add(e.getCode());
        }
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyReleased(KeyEvent e) {
        List<KeyCode> keyCodesToRemove = inputEventsKeys.get(InputEvents.ONKEYPRESSED);
        keyCodesToRemove.remove(e.getCode());

        List<KeyCode> keyCodesToAdd = inputEventsKeys.get(InputEvents.ONKEYRELEASED);
        if (!keyCodesToAdd.contains(e.getCode())) {
            keyCodesToAdd.add(e.getCode());
        }
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseClicked(Vec2d e) {

    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMousePressed(Vec2d e) {
        inputEventsMouse.remove(InputEvents.ONMOUSERELEASED);
        inputEventsMouse.remove(InputEvents.ONMOUSEDRAGGED);

        inputEventsMouse.put(InputEvents.ONMOUSEPRESSED, e);
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseReleased(Vec2d e) {
        inputEventsMouse.remove(InputEvents.ONMOUSEPRESSED);
        inputEventsMouse.remove(InputEvents.ONMOUSEDRAGGED);

        inputEventsMouse.put(InputEvents.ONMOUSERELEASED, e);
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseDragged(Vec2d e) {
        inputEventsMouse.put(InputEvents.ONMOUSEDRAGGED, e);
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
