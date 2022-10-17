package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.Collections;

public class DrawSystem extends System {
    public DrawSystem(GameWorld gameWorld) {
        super(gameWorld, Collections.singletonList("sprite"));
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
            gameObj.draw(g);
        }
    }

    @Override
    public String name() {
        return "draw";
    }
}
