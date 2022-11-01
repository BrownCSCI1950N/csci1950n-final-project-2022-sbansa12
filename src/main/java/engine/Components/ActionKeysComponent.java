package engine.Components;

import Pair.Pair;
import engine.GameObject;
import engine.InputEvents;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

public class ActionKeysComponent extends KeysComponent {

    protected GameObject gameObject;
    KeyCode key;
    boolean isAction;
    boolean isOncePerPress;
    boolean onceHappened;

    /**
     * Constructor
     * @param key - the key of interest
     * @param isOncePerPress - if set to true, key action only carried once per button click, else occurs on every tick until button released
     */
    public ActionKeysComponent(GameObject gameObject, KeyCode key, boolean isOncePerPress) {
        this.gameObject = gameObject;
        this.key = key;
        this.isAction = false;
        this.isOncePerPress = isOncePerPress;
        this.onceHappened = false;
    }

    public boolean isOnceHappened() {
        return onceHappened;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        if (isAction) {
            action();
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    public static final String tag = "actionKeys";

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void script(Pair<InputEvents, List<KeyCode>> input) {
        if (input.getLeft() == InputEvents.ONKEYPRESSED) {
            if (input.getRight().contains(key)) {
                if (isOncePerPress) {
                    if (!onceHappened) {
                        action();
                        this.onceHappened = true;
                    }
                } else {
                    this.isAction = true;
                }
            }
        } else if (input.getLeft() == InputEvents.ONKEYRELEASED) {
            if (input.getRight().contains(key)) {
                this.isAction = false;
                this.onceHappened = false;
            }
        }
    }

    public void action() {

    }
}
