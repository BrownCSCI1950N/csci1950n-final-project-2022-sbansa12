package engine.Systems;

import Pair.Pair;
import engine.Components.CollisionComponent;
import engine.GameObject;
import engine.GameWorld;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;

public class CollisionSystem extends System {

    private final HashSet<Pair<GameObject, GameObject>> collided;

    /**
     * Constructor for a System. Requires all game objects zIndex to be unique game object for the system.
     *
     * @param gameWorld   - game world this system is a part of
     */
    public CollisionSystem(GameWorld gameWorld) {
        super(gameWorld, "collision");
        this.collided = new HashSet<>();
    }


    @Override
    public void tick(long t) {

        // Check to See if Any Pair of Objects have Collided
        for (GameObject gameObject1: gameObjects) {
            for (GameObject gameObject2: gameObjects) {

                // Ensure you are not checking if an object collides with itself
                if (gameObject1 != gameObject2) {
                    CollisionComponent c1 = (CollisionComponent) gameObject1.getComponent("collision");
                    CollisionComponent c2 = (CollisionComponent) gameObject2.getComponent("collision");

                    // Check if the objects collide
                    if (c1.getCollisionShape().collides(c2.getCollisionShape())) {

                        // Make sure you have not already found this collision
                        if (!collided.contains(new Pair<>(gameObject1, gameObject2))) {
                            c1.onCollide(gameObject2);
                            collided.add(new Pair<>(gameObject1, gameObject2));
                            collided.add(new Pair<>(gameObject2, gameObject1));
                        }
                    }
                }
            }
        }

        collided.clear();

        super.tick(t);
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }
}
