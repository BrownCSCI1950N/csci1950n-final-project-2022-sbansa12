package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class DrawSystem extends System {
    public DrawSystem(GameWorld gameWorld, List<String> relevantTags) {
        super(gameWorld, relevantTags);
    }

    @Override
    public void tick(long t) {
        super.tick(t);
    }

    @Override
    public void lateTick() {

    }

    public void draw(GraphicsContext g) {
        for (GameObject gameObj: gameObjects) {
            for (String tag: this.relevantTags) {
                if (gameObj.hasComponentTag(tag)) {
                    gameObj.getComponent(tag).draw(g);
                }
            }
        }
    }

    @Override
    public String name() {
        return "draw";
    }
}