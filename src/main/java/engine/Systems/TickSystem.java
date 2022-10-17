package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class TickSystem extends System {
    public TickSystem(GameWorld gameWorld, List<String> relevantTags) {
        super(gameWorld, relevantTags);
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
