package helpme;

import engine.Components.*;
import engine.GameObject;
import engine.GameWorld;
import engine.Shape.AAB;
import engine.TerrainGeneration.TileType;
import engine.UI.Viewport;
import engine.support.Vec2d;
import helpme.Constants.ConstantsGameValues;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HelpMeGame {
    public static boolean doorOpen;
    public boolean levelComplete;
    public boolean needRestart;
    Viewport viewport;
    public void setViewport(Viewport viewport){
        this.viewport = viewport;
    }
    private static Integer zIndex = 1;
    static int getZIndex() {
        int toReturn = zIndex;
        zIndex += 1;
        return toReturn;
    }

    Map<TileType, GameObject> players = new HashMap<>();

    public void setWorld(GameWorld gameWorld, Map<TileType, Vec2d> spawns) {
        players.clear();
        players.put(TileType.PLAYER1, addPlayer(gameWorld, spawns.get(TileType.SPAWN1), TileType.PLAYER1));
        players.put(TileType.PLAYER2, addPlayer(gameWorld, spawns.get(TileType.SPAWN2), TileType.PLAYER2));
        doorOpen = false;
        levelComplete = false;
        needRestart = false;
        viewport.setGameWorld(gameWorld);
    }

    public GameObject addPlayer(GameWorld gameWorld, Vec2d spawnPoint, TileType t) {
        // Player Initial Position
        TransformComponent tc = new TransformComponent(spawnPoint, ConstantsGameValues.tileSize);
        GameObject player = new GameObject(tc, getZIndex());

        player.setName(t.name());
        player.addComponent(new TileComponent(t));

        // Player Sprite
        player.addComponent(new SpriteComponent(player, HelpMeImageResource.getResource(t.name()), new Vec2d(0,0)));

        // Player Respawn Component
        player.addComponent(new RespawnComponent(player, spawnPoint) {
            @Override
            public void script() {
                needRestart = true;
            }
        });

        // Player Death Component
        player.addComponent(new HealthDamageComponent(player, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.playerCollisionLayer, ConstantsGameValues.playerMaxHealth, 0) {

            @Override
            public void zeroHealthScript() {
                RespawnComponent respawnComponent = (RespawnComponent) player.getComponent("respawn");
                respawnComponent.script();
            }
        });

        // Player Gravity
        PhysicsComponent p = new PhysicsComponent(
                player,
                false,
                List.of(ConstantsGameValues.gravityUp, ConstantsGameValues.gravityDown, ConstantsGameValues.gravityLowJump),
                ConstantsGameValues.playerMass,
                ConstantsGameValues.playerRestitution,
                true,
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
                List.of(TileType.WALL1, TileType.BOX1, TileType.BOX2)
        ));

        List<KeyCode> movementKeys = new LinkedList<>();
        if (t == TileType.PLAYER1) {
            movementKeys.add(ConstantsGameValues.controls.get(0).get(0));
            movementKeys.add(ConstantsGameValues.controls.get(0).get(2));
            movementKeys.add(ConstantsGameValues.controls.get(0).get(3));
        } else if (t == TileType.PLAYER2) {
            movementKeys.add(ConstantsGameValues.controls.get(1).get(0));
            movementKeys.add(ConstantsGameValues.controls.get(1).get(2));
            movementKeys.add(ConstantsGameValues.controls.get(1).get(3));
        }

        // Player Movement and Jumping
        player.addComponent(new MovePhysicsComponent(
                player,
                movementKeys,
                ConstantsGameValues.movementVelocity,
                false));

        gameWorld.addGameObject(player);
        return player;
    }

    public Boolean isDoorOpen() {
        return doorOpen;
    }
    public void openDoor() {
        doorOpen = true;
    }

    public void levelComplete() {
        levelComplete = true;
    }
    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void restarted() {
        needRestart = false;
    }
    public boolean isNeedRestart() {
        return needRestart;
    }
    public void turnOffMovement(TileType t) {
        MovePhysicsComponent c = (MovePhysicsComponent) players.get(t).getComponent(MovePhysicsComponent.tag);
        c.turnOffMovement();
    }
}
