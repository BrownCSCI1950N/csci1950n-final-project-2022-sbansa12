package engine.Systems;

import Pair.Pair;
import engine.Components.MouseClickComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.InputEvents;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.Objects;

public class MouseClickSystem extends System {

    Integer lastServiced;

    public MouseClickSystem(GameWorld gameWorld, List<String> relevantTags) {
        super(gameWorld, relevantTags);
        lastServiced = 0;
    }

    @Override
    public void tick(long t) {
        Pair<Integer, Vec2d> onMouseClicked = gameWorld.findInputEventMouse(InputEvents.ONMOUSECLICKED);
        if (onMouseClicked != null) {
            if (!Objects.equals(onMouseClicked.getLeft(), lastServiced)) {
                lastServiced = onMouseClicked.getLeft();
                for (GameObject gameObject: gameObjects) {
                    for (String tag: this.relevantTags) {
                        if (gameObject.hasComponentTag(tag)) {
                            MouseClickComponent c = (MouseClickComponent) gameObject.getComponent(tag);
                            c.script(new Pair<>(InputEvents.ONMOUSECLICKED, onMouseClicked.getRight()));
                        }
                    }
                }
            }
        }

        for (GameObject gameObject: gameObjects) {
            for (String tag: this.relevantTags) {
                if (gameObject.hasComponentTag(tag)) {
                    MouseClickComponent c = (MouseClickComponent) gameObject.getComponent(tag);
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
        return "mouseClick";
    }
}
