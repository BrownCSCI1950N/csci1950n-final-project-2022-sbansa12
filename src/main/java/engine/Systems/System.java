package engine.Systems;

import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public abstract class System {
    GameWorld gameWorld;
    String relevantTag;
    TreeSet<GameObject> gameObjects;
    List<GameObject> toAdd;
    List<GameObject> toRemove;

    /**
     * Constructor for a System. Requires all game objects zIndex to be unique game object for the system.
     * @param gameWorld - game world this system is a part of
     * @param relevantTag - the component tag this system is relevant for.
     */
    public System(GameWorld gameWorld, String relevantTag) {
        this.gameWorld = gameWorld;
        this.relevantTag = relevantTag;
        gameObjects = new TreeSet<>(Comparator.comparing(s -> s.zIndex));
        this.toAdd = new LinkedList<>();
        this.toRemove = new LinkedList<>();
    }

    /**
     * Adds game object to the system if game object contains tag relevant for the system
     * @param g - game object to potentially add
     */
    public void addGameObject(GameObject g) {
        List<String> gameObjectTags = g.getComponentTags();

        if (gameObjectTags.contains(relevantTag)) {
            toAdd.add(g);
        }
    }

    public void removeGameObject(GameObject g) {
        toRemove.add(g);
    }

    public String getRelevantTag(){
        return relevantTag;
    }

    public void tick(long t) {
        if (toAdd.size() != 0) {
            gameObjects.addAll(toAdd);
            toAdd.clear();
        }
        if (toRemove.size() != 0) {
            for (GameObject g: toRemove) {
                gameObjects.remove(g);
            }
            toRemove.clear();
        }
    }
    public abstract void lateTick();
    public abstract void draw(GraphicsContext g);
}
