package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

public class DrawSystem extends System {
    public DrawSystem(GameWorld gameWorld) {
        super(gameWorld,"sprite");
    }

    @Override
    public void tick(long t) {

    }

    @Override
    public void lateTick() {

    }

    public void draw(GraphicsContext g) {
        for (GameObject gameObj: gameObjects) {
            gameObj.draw(g);
        }
    }
}
