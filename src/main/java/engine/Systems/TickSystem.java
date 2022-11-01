package engine.Systems;

import engine.Components.SpriteComponent;
import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;

public class TickSystem extends System {
    public TickSystem(GameWorld gameWorld, List<String> relevantTagsNew) {
        super(gameWorld, new LinkedList<>());
        relevantTags.addAll(relevantTagsNew);
        if (!relevantTags.contains(SpriteComponent.tag)) {
            relevantTags.add(SpriteComponent.tag);
        }
    }

    public void tick(long t) {
        for (GameObject g: gameObjects) {
            for (String tag: this.relevantTags) {
                if (g.hasComponentTag(tag)) {
                    g.getComponent(tag).tick(t);
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
        return "tick";
    }
}
