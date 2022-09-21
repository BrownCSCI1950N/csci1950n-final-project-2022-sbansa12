package engine;

import javafx.scene.canvas.GraphicsContext;

import java.util.Comparator;
import java.util.TreeSet;

public class System {
    TreeSet<GameObject> gameObjects;

    public System() {
        gameObjects = new TreeSet<>(Comparator.comparing(s -> s.zIndex));
    }

    public void addGameObject(GameObject g) {
        gameObjects.add(g);
    }

    public void removeGameObject(GameObject g) {
        gameObjects.remove(g);
    }

    public void tick(long t) {
        for (GameObject g: gameObjects) {
            g.tick(t);
        }
    }
    public void lateTick() {
        for (GameObject gameObj: gameObjects) {
            gameObj.lateTick();
        }
    }

    public void draw(GraphicsContext g) {
        for (GameObject gameObj: gameObjects) {
            gameObj.draw(g);
        }
    }
}
