package engine.Systems;

import Pair.Pair;
import engine.Components.Collision;
import engine.Components.CollisionComponent;
import engine.Components.TransformComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.Shape.Ray;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollisionSystem extends System {

    private final Set<Pair<CollisionComponent, CollisionComponent>> collided = new HashSet<>();

    private final Map<Integer, List<CollisionComponent>> gameObjects = new HashMap<>();

    private List<Pair<Integer, Integer>> layersCollide;

    static boolean bothObjectCollisionScript;

    static List<Pair<String,String>> tagsCollide;

    /**
     * Constructor for a System. Requires all game objects zIndex to be unique game object for the system. Assumes we want to check collisions between same tags. To add collisions across tags, use method.
     *
     * @param gameWorld   - game world this system is a part of
     * @param relevantTags - list of strings of relevant component tags to check collisions between.
     * @param bothObjectCollisionScript - boolean representing if both collided objects should have their scripts called or only one of them
     */
    public CollisionSystem(GameWorld gameWorld, List<String> relevantTags, boolean bothObjectCollisionScript) {
        super(gameWorld, relevantTags);
        this.layersCollide = new LinkedList<>();
        CollisionSystem.bothObjectCollisionScript = bothObjectCollisionScript;
        tagsCollide = new LinkedList<>();
        for (String tag: relevantTags) {
            tagsCollide.add(new Pair<>(tag, tag));
        }
    }

    /**
     * List of pairs of layers we want to check game objects collisions across.
     * @param layersCollide - list of pairs of layers.
     */
    public void setLayersCollide(List<Pair<Integer, Integer>> layersCollide) {
        this.layersCollide = layersCollide;
    }

    public void removeTagsCollide(String s1, String s2) {
        tagsCollide.remove(new Pair<>(s1, s2));
        tagsCollide.remove(new Pair<>(s2, s1));
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

        Set<CollisionComponent> notCollided = new HashSet<>();

        for (Integer keys : gameObjects.keySet()) {
            notCollided.addAll(gameObjects.get(keys));
        }

        for (Pair<CollisionComponent, CollisionComponent> col : collided) {
            if (col.getRight().getGameObject() != col.getLeft().getGameObject()) {
                notCollided.remove(col.getRight());
                notCollided.remove(col.getLeft());
            }
        }

        for (CollisionComponent c : notCollided) {
            c.onNotCollide();
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
//        Collision toReturn = null;
        List<CollisionComponent> layer2 = gameObjects.get(layer);
        if (layer2 == null) {
            assert false;
            return;
//            return null;
        }
        for (CollisionComponent gameObject2: layer2) {

            if (!tagsCollide.contains(new Pair<>(gameObject1.getTag(), gameObject2.getTag()))) {
                continue;
            }

            // Ensure you are not checking if an object collides with itself
            if (gameObject1 != gameObject2) {

                // Check if the objects collide
                Vec2d MTV2 = gameObject1.getCollisionShape().MTV(gameObject2.getCollisionShape(), true);
                if (MTV2 != null) {

                    // Make sure you have not already found this collision
                    if (!collided.contains(new Pair<>(gameObject1, gameObject2))) {

//                        toReturn = new Collision(gameObject2.getGameObject(), null);

                        // Check if Overlap Is Allowed
                        if (gameObject1.allowOverlap() || gameObject2.allowOverlap()) {
                            gameObject1.onCollide(new Collision(gameObject2.getGameObject(), null));
                            if(bothObjectCollisionScript) {
                                gameObject2.onCollide(new Collision(gameObject1.getGameObject(), null));
                            }
                        } else if (!gameObject1.allowOverlap() && !gameObject2.allowOverlap()) {
                            // If Overlap is not allowed, based on static-ness shift position by MTV
                            Vec2d MTV1 = gameObject2.getCollisionShape().MTV(gameObject1.getCollisionShape(), true);


                            if (!gameObject1.isStaticApplyMTV() && gameObject2.isStaticApplyMTV()) {
                                TransformComponent currentPosition = gameObject1.getGameObject().getTransform();
                                gameObject1.getGameObject().getTransform().setCurrentGameSpacePositionNoVelocity(currentPosition.getCurrentGameSpacePosition().plus(MTV1));
                            } else if (gameObject1.isStaticApplyMTV() && !gameObject2.isStaticApplyMTV()) {
                                TransformComponent currentPosition = gameObject2.getGameObject().getTransform();
                                gameObject2.getGameObject().getTransform().setCurrentGameSpacePositionNoVelocity(currentPosition.getCurrentGameSpacePosition().plus(MTV2));
                            } else if (!gameObject1.isStaticApplyMTV() && !gameObject2.isStaticApplyMTV()) {
                                TransformComponent currentPosition1 = gameObject1.getGameObject().getTransform();
                                gameObject1.getGameObject().getTransform().setCurrentGameSpacePositionNoVelocity(currentPosition1.getCurrentGameSpacePosition().plus(MTV1.sdiv(2)));

                                TransformComponent currentPosition2 = gameObject2.getGameObject().getTransform();
                                gameObject2.getGameObject().getTransform().setCurrentGameSpacePositionNoVelocity(currentPosition2.getCurrentGameSpacePosition().plus(MTV2.sdiv(2)));
                            } else {
                                // Two Static Objects Should Not Collide
                                assert false;
                            }

                            gameObject1.onCollide(new Collision(gameObject2.getGameObject(), MTV1));
                            if(bothObjectCollisionScript) {
                                gameObject2.onCollide(new Collision(gameObject1.getGameObject(), MTV2));
                            }
                        }

                        // Save That We Have Found This Collision
                        collided.add(new Pair<>(gameObject1, gameObject2));
                        collided.add(new Pair<>(gameObject2, gameObject1));
                    }
                }
            }
        }

//        return toReturn;
    }

    public Collision checkRayCollision(Ray ray, List<Integer> layers) {
        double minT = Double.POSITIVE_INFINITY;
        CollisionComponent c = null;

        for (Integer layer : layers) {
            List<CollisionComponent> layer2 = gameObjects.get(layer);
            if (layer2 == null) {
                assert false;
                return null;
            }

            for (CollisionComponent gameObject2: layer2) {
                double t = gameObject2.getCollisionShape().raycast(ray);
                if (t != -1) {
                    if (t > 0 && t < minT) {
                        minT = t;
                        c = gameObject2;
                    }
                }
            }
        }

        if (c == null) {
            return null;
        } else {
            return new Collision(c.getGameObject(), minT);
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
