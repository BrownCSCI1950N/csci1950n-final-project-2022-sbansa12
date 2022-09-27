package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public abstract class System {
    GameWorld gameWorld;
    String relevantTag;
    TreeSet<GameObject> gameObjects;

    /**
     * Constructor for a System. Requires all game objects zIndex to be unique game object for the system.
     * @param gameWorld - game world this system is a part of
     * @param relevantTag - the component tag this system is relevant for.
     */
    public System(GameWorld gameWorld, String relevantTag) {
        this.gameWorld = gameWorld;
        this.relevantTag = relevantTag;
        gameObjects = new TreeSet<>(Comparator.comparing(s -> s.zIndex));
    }

    /**
     * Adds game object to the system if game object contains tag relevant for the system
     * @param g - game object to potentially add
     */
    public void addGameObject(GameObject g) {
        List<String> gameObjectTags = g.getComponentTags();

        if (gameObjectTags.contains(relevantTag)) {
            gameObjects.add(g);
        }
    }

    public void removeGameObject(GameObject g) {
        gameObjects.remove(g);
    }

    public String getRelevantTag(){
        return relevantTag;
    }

    public abstract void tick(long t);
    public abstract void lateTick();
    public abstract void draw(GraphicsContext g);
}
