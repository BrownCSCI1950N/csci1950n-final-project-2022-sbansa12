package nin;

import engine.Components.*;
import engine.GameObject;
import engine.GameWorld;
import engine.Shape.AAB;
import engine.Shape.Ray;
import engine.Systems.CollisionSystem;
import engine.TerrainGeneration.TileType;
import engine.UI.Viewport;
import engine.support.Vec2d;
import nin.Constants.ConstantsGameValues;

import java.util.List;

public class NinGame {
    CollisionSystem c;
    Viewport viewport;
    public static Boolean doorOpen;
    private Boolean levelComplete;
    public void setCollisionSystem(CollisionSystem c) {
        this.c = c;
    }
    public void setWorld(GameWorld gameWorld, Vec2d spawnPoint) {
        addPlayer(gameWorld, spawnPoint);
        doorOpen = false;
        levelComplete = false;
        viewport.setGameWorld(gameWorld);
    }

    public void addPlayer(GameWorld gameWorld, Vec2d spawnPoint) {
        TransformComponent tc = new TransformComponent(spawnPoint, ConstantsGameValues.tileSize);
        GameObject player = new GameObject(tc, getZIndex());

        player.setName("PLAYER");

        // Player Components
        player.addComponent(new TileComponent(TileType.PLAYER));
        player.addComponent(new SpriteComponent(player, NinImageResource.getResource("PLAYER"), new Vec2d(0,0)));
        player.addComponent(new RespawnComponent(player, spawnPoint));
        player.addComponent(new HealthDamageComponent(player, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.playerCollisionLayer, ConstantsGameValues.playerMaxHealth, 0){
            @Override
            public void zeroHealthScript() {
                doorOpen = false;
                RespawnComponent respawnComponent = (RespawnComponent) player.getComponent("respawn");
                respawnComponent.script();
            }
        });

        // Gravity
        PhysicsComponent p = new PhysicsComponent(
                player,
                false,
                List.of(ConstantsGameValues.gravityUp, ConstantsGameValues.gravityDown, ConstantsGameValues.gravityLowJump),
                ConstantsGameValues.playerMass,
                ConstantsGameValues.playerRestitution,
                false);
        player.addComponent(p);

        // Prevent From Colliding Into Walls (Side to Side)
        player.addComponent(new CollisionComponent(
                player,
                new AAB(tc.getCurrentGameSpacePosition(),
                        tc.getSize()),
                ConstantsGameValues.playerCollisionLayer,
                false,
                false,
                p));

        // Deal with Grounding and Physics
        player.addComponent(new PhysicsGroundedComponent(
                player,
                new AAB(tc.getCurrentGameSpacePosition(),
                        tc.getSize().plus(ConstantsGameValues.playerGroundedShapeSizeAdd)),
                ConstantsGameValues.physicsGroundedCollisionLayer,
                p,
                List.of(TileType.WALL0, TileType.BOX0, TileType.BOX1)
        ));

        // Jumping
        player.addComponent(new MovePhysicsComponent(
                player,
                ConstantsGameValues.movementKeys,
                ConstantsGameValues.movementVelocity));

        // Projectile
        player.addComponent(new ShootRayComponent(c, player, List.of(ConstantsGameValues.wallCollisionLayer, ConstantsGameValues.boxCollisionLayer), ConstantsGameValues.rayDuration, ConstantsGameValues.rayWidth, ConstantsGameValues.rayColor){
            @Override
            public void action(Ray r, GameObject gO) {
                if (gO.hasComponentTag("tile")) {
                    if (((TileComponent) gO.getComponent("tile")).getTileType().equals(TileType.WALL0) || ((TileComponent) gO.getComponent("tile")).getTileType().equals(TileType.WALL1)) {
                        return;
                    }
                    if (((TileComponent) gO.getComponent("tile")).getTileType().equals(TileType.BOX0) || ((TileComponent) gO.getComponent("tile")).getTileType().equals(TileType.BOX1)) {
                        PhysicsComponent p = (PhysicsComponent) gO.getComponent("physics");
                        p.applyImpulse(r.dir.smult(ConstantsGameValues.rayImpulse));
                    }
                }
            }
        });

        gameWorld.addGameObject(player);
    }

    public Boolean levelComplete() {
        return levelComplete;
    }
    public void completeLevel() {
        levelComplete = true;
    }
    public Boolean isDoorOpen() {
        return doorOpen;
    }
    public void openDoor() {
        doorOpen = true;
    }
    public void setViewport(Viewport viewport){
        this.viewport = viewport;
    }
    private static Integer zIndex = 1;
    static int getZIndex() {
        int toReturn = zIndex;
        zIndex += 1;
        return toReturn;
    }
}
