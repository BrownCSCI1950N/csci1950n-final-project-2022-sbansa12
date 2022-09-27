package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

public class TickSystem extends System {
    public TickSystem(GameWorld gameWorld) {
        super(gameWorld,"tick");
    }

    public void tick(long t) {
        for (GameObject g: gameObjects) {
            g.tick(t);
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }
}
