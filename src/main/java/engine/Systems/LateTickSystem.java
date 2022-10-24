package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class LateTickSystem extends System {

    public LateTickSystem(GameWorld gameWorld, List<String> relevantTags) {
        super(gameWorld, relevantTags);
    }

    @Override
    public void lateTick() {
        for (GameObject g: gameObjects) {
            for (String tag: this.relevantTags) {
                if (g.hasComponentTag(tag)) {
                    g.getComponent(tag).lateTick();
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String name() {
        return null;
    }
}
