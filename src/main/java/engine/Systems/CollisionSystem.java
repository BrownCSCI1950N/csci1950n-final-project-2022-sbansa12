package engine.Systems;

import Pair.Pair;
import engine.Components.Collision;
import engine.Components.CollisionComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollisionSystem extends System {

    private final Set<Pair<CollisionComponent, CollisionComponent>> collided;

    private final Map<Integer, List<CollisionComponent>> gameObjects;

    private List<Pair<Integer, Integer>> layersCollide;

    boolean bothObjectCollisionScript;

    List<Pair<String,String>> tagsCollide;

    /**
     * Constructor for a System. Requires all game objects zIndex to be unique game object for the system. Assumes we want to check collisions between same tags. To add collisions across tags, use method.
     *
     * @param gameWorld   - game world this system is a part of
     * @param relevantTags - list of strings of relevant component tags to check collisions between.
     * @param bothObjectCollisionScript - boolean representing if both collided objects should have their scripts called or only one of them
     */
    public CollisionSystem(GameWorld gameWorld, List<String> relevantTags, boolean bothObjectCollisionScript) {
        super(gameWorld, relevantTags);
        this.collided = new HashSet<>();
        this.gameObjects = new HashMap<>();
        this.layersCollide = new LinkedList<>();
        this.bothObjectCollisionScript = bothObjectCollisionScript;
        this.tagsCollide = new LinkedList<>();
        for (String relevantTag: relevantTags) {
            addTagsCollide(relevantTag, relevantTag);
        }
    }

    /**
     * List of pairs of layers we want to check game objects collisions across.
     * @param layersCollide - list of pairs of layers.
     */
    public void setLayersCollide(List<Pair<Integer, Integer>> layersCollide) {
        this.layersCollide = layersCollide;
    }

    public void addTagsCollide(String s1, String s2) {
        tagsCollide.add(new Pair<>(s1, s2));
        tagsCollide.add(new Pair<>(s2, s1));
    }

    @Override
    public void tick(long t) {

        // Check to See if Any Pair of Objects have Collided
        for (Pair<Integer, Integer> layerCollideCheck: layersCollide) {
            List<CollisionComponent> layer1 = gameObjects.get(layerCollideCheck.getLeft());
            if (layer1 != null) {
                for (CollisionComponent gameObject1: layer1) {
                    checkCollision(gameObject1, layerCollideCheck.getRight());
                }
            }
        }

        collided.clear();

        if (toAdd.size() != 0) {
            for (GameObject g: toAdd) {
                for (String relevantTag: relevantTags) {
                    if (g.hasComponentTag(relevantTag)) {
                        CollisionComponent c = (CollisionComponent) g.getComponent(relevantTag);
                        List<CollisionComponent> layer = gameObjects.get(c.getLayer());
                        if (layer == null) {
                            List<CollisionComponent> newList = new LinkedList<>();
                            newList.add(c);
                            gameObjects.put(c.getLayer(), newList);
                        } else {
                            layer.add(c);
                        }

                    }
                }
            }

            toAdd.clear();
        }

        if (toRemove.size() != 0) {
            for (GameObject g: toRemove) {
                for (String relevantTag: relevantTags) {
                    if (g.hasComponentTag(relevantTag)) {
                        CollisionComponent c = (CollisionComponent) g.getComponent(relevantTag);
                        List<CollisionComponent> layer = gameObjects.get(c.getLayer());
                        layer.remove(c);
                    }
                }
            }
            toRemove.clear();
        }
    }

    public void checkCollision(CollisionComponent gameObject1, Integer layer) {
        List<CollisionComponent> layer2 = gameObjects.get(layer);
        if (layer2 == null) {
            return;
        }
        for (CollisionComponent gameObject2: layer2) {

            if (!tagsCollide.contains(new Pair<>(gameObject1.getTag(), gameObject2.getTag()))) {
                continue;
            }

            // Ensure you are not checking if an object collides with itself
            if (gameObject1 != gameObject2) {

                // Check if the objects collide
                if (gameObject1.getCollisionShape().isColliding(gameObject2.getCollisionShape())) {

                    // Make sure you have not already found this collision
                    if (!collided.contains(new Pair<>(gameObject1, gameObject2))) {
                        Vec2d MTV1 = gameObject2.getCollisionShape().MTV(gameObject1.getCollisionShape(), true);
                        Vec2d MTV2 = gameObject1.getCollisionShape().MTV(gameObject2.getCollisionShape(), true);

                        gameObject1.onCollide(new Collision(gameObject2.getGameObject(), MTV1));
                        if(bothObjectCollisionScript) {
                            gameObject2.onCollide(new Collision(gameObject1.getGameObject(), MTV2));
                        }
                        collided.add(new Pair<>(gameObject1, gameObject2));
                        collided.add(new Pair<>(gameObject2, gameObject1));
                    }
                }
            }
        }
    }

    public List<String> getRelevantTags(){
        return relevantTags;
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String name() {
        return "collision";
    }
}
