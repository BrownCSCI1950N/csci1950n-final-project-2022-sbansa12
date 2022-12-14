package engine;

import Pair.Pair;
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
    private final Map<InputEvents, Pair<Integer, Vec2d>> inputEventsMouse;
    private final Map<InputEvents, Integer> inputEventsMouseNumber;
    private final Map<InputEvents, List<KeyCode>> inputEventsKeys;

    public GameWorld() {
        this.systems = new ArrayList<>();
        this.inputEventsMouse = new HashMap<>();
        this.inputEventsMouseNumber = new HashMap<>();
        this.inputEventsKeys = new HashMap<>();
        this.inputEventsKeys.put(InputEvents.ONKEYRELEASED, new LinkedList<>());
        this.inputEventsKeys.put(InputEvents.ONKEYPRESSED, new LinkedList<>());
        systems.add(new DrawSystem(this, List.of("sprite")));
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

    public Pair<Integer, Vec2d> findInputEventMouse(InputEvents i) {
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
        for (System s: systems) {
            s.lateTick();
        }
    }

    public void draw(GraphicsContext g) {
        for (System s: systems) {
            s.draw(g);
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
        inputEventsKeys.put(InputEvents.ONKEYRELEASED, keyCodesToRemove);

        List<KeyCode> keyCodesToAdd = inputEventsKeys.get(InputEvents.ONKEYPRESSED);
        if (!keyCodesToAdd.contains(e.getCode())) {
            keyCodesToAdd.add(e.getCode());
        }
        inputEventsKeys.put(InputEvents.ONKEYPRESSED, keyCodesToAdd);
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    public void onKeyReleased(KeyEvent e) {
        List<KeyCode> keyCodesToRemove = inputEventsKeys.get(InputEvents.ONKEYPRESSED);
        keyCodesToRemove.remove(e.getCode());
        inputEventsKeys.put(InputEvents.ONKEYPRESSED, keyCodesToRemove);

        List<KeyCode> keyCodesToAdd = inputEventsKeys.get(InputEvents.ONKEYRELEASED);
        if (!keyCodesToAdd.contains(e.getCode())) {
            keyCodesToAdd.add(e.getCode());
        }
        inputEventsKeys.put(InputEvents.ONKEYRELEASED, keyCodesToAdd);
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseClicked(Vec2d e) {
        Integer n = inputEventsMouseNumber.get(InputEvents.ONMOUSECLICKED);
        if (n == null) {
            n = 0;
        }
        n += 1;
        inputEventsMouse.put(InputEvents.ONMOUSECLICKED, new Pair<>(n, e));
        inputEventsMouseNumber.put(InputEvents.ONMOUSECLICKED, n);
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMousePressed(Vec2d e) {
        inputEventsMouse.remove(InputEvents.ONMOUSERELEASED);
        inputEventsMouse.remove(InputEvents.ONMOUSEDRAGGED);

        // Ignore Integer as Not Refactored
        inputEventsMouse.put(InputEvents.ONMOUSEPRESSED, new Pair<>(0, e));
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseReleased(Vec2d e) {
        inputEventsMouse.remove(InputEvents.ONMOUSEPRESSED);
        inputEventsMouse.remove(InputEvents.ONMOUSEDRAGGED);

        // Ignore Integer as Not Refactored
        inputEventsMouse.put(InputEvents.ONMOUSERELEASED, new Pair<>(0, e));
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    public void onMouseDragged(Vec2d e) {
        // Ignore Integer as Not Refactored
        inputEventsMouse.put(InputEvents.ONMOUSEDRAGGED, new Pair<>(0, e));
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
