package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class RespawnComponent implements Component{
    GameObject thisGameObject;
    Vec2d spawnPoint;

    public RespawnComponent(GameObject thisGameObject, Vec2d spawnPoint) {
        this.thisGameObject = thisGameObject;
        this.spawnPoint = spawnPoint;
    }

    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "respawn";
    }

    public void script() {
        thisGameObject.getTransform().setCurrentGameSpacePosition(spawnPoint);
    }
}
