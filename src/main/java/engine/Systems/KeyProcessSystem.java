package engine.Systems;

import Pair.Pair;
import engine.Components.KeysComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.InputEvents;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;

public class KeyProcessSystem extends System {
    public KeyProcessSystem(GameWorld gameWorld, List<String> relevantTags) {
        super(gameWorld, relevantTags);
    }

    @Override
    public void tick(long t) {
        List<KeyCode> onKeyPressed = gameWorld.findInputEventKeys(InputEvents.ONKEYPRESSED);
        List<KeyCode> onKeyReleased = gameWorld.findInputEventKeys(InputEvents.ONKEYRELEASED);
        if (onKeyPressed.size() != 0) {
            for (GameObject gameObject: gameObjects) {
                for (String tag: this.relevantTags) {
                    if (gameObject.hasComponentTag(tag)) {
                        KeysComponent c = (KeysComponent) gameObject.getComponent(tag);
                        c.script(new Pair<>(InputEvents.ONKEYPRESSED, onKeyPressed));
                    }
                }
            }
        }

        if (onKeyReleased.size() != 0) {
            for (GameObject gameObject: gameObjects) {
                for (String tag: this.relevantTags) {
                    if (gameObject.hasComponentTag(tag)) {
                        KeysComponent c = (KeysComponent) gameObject.getComponent(tag);
                        c.script(new Pair<>(InputEvents.ONKEYRELEASED, onKeyReleased));
                    }
                }
            }
        }

        for (GameObject gameObject: gameObjects) {
            for (String tag: this.relevantTags) {
                if (gameObject.hasComponentTag(tag)) {
                    KeysComponent c = (KeysComponent) gameObject.getComponent(tag);
                    c.tick(t);
                }
            }
        }

        super.tick(t);
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String name() {
        return "keyProcess";
    }
}
