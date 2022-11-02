package nin;

import engine.Components.*;
import engine.GameObject;
import engine.GameWorld;
import engine.Shape.AAB;
import engine.Shape.Polygon;
import engine.Systems.CollisionSystem;
import engine.Systems.KeyProcessSystem;
import engine.Systems.TickSystem;
import engine.TerrainGeneration.LevelParseException;
import engine.TerrainGeneration.MapReader.MapReader;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import nin.Animations.button.UnClickedButton;
import nin.Animations.exit.ClosedExit;
import nin.Constants.ConstantsGameValues;
import nin.Constants.ConstantsSelectionScreen;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static engine.TerrainGeneration.TileType.*;

public class NinGameLevel {
    String currentLevel = "00";
    NinGame ninGame;
    public NinGameLevel(NinGame ninGame) {
        this.ninGame = ninGame;
    }

    public void setCurrentLevel(String newLevel) {
        this.currentLevel = newLevel;
        try {
            TileType[][] map = MapReader.createTerrain(".\\src\\main\\java\\nin\\Maps\\" + newLevel + ".txt", List.of(TileType.BUTTON));

            MapReader.checkMapSize(map, ConstantsGameValues.mapSize, newLevel);

            GameWorld gameWorld = createWorld();
            Vec2d spawnPoint = createMapInWorld(gameWorld, map);
            populateSpecialTiles(gameWorld, map);
            ninGame.setWorld(gameWorld, spawnPoint);
        } catch (IOException | LevelParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void resetCurrentLevel() {
        try {
            TileType[][] map = MapReader.createTerrain(".\\src\\main\\java\\nin\\Maps\\" + this.currentLevel + ".txt", List.of(TileType.BUTTON));

            MapReader.checkMapSize(map, ConstantsGameValues.mapSize, this.currentLevel);

            GameWorld gameWorld = createWorld();
            Vec2d spawnPoint = createMapInWorld(gameWorld, map);
            populateSpecialTiles(gameWorld, map);
            ninGame.setWorld(gameWorld, spawnPoint);
        } catch (IOException | LevelParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private GameWorld createWorld() {
        GameWorld toReturn = new GameWorld();

        // Systems: [Keys, Tick, Collision, Draw]
        CollisionSystem c = new CollisionSystem(toReturn,  List.of(CollisionComponent.tag, HealthDamageComponent.tag, PhysicsGroundedComponent.tag), true);
        c.setLayersCollide(ConstantsGameValues.layersCollide);
        c.removeTagsCollide(PhysicsGroundedComponent.tag, PhysicsGroundedComponent.tag);
        c.addTagsCollide(CollisionComponent.tag, PhysicsGroundedComponent.tag);
        toReturn.prependSystem(c);
        toReturn.prependSystem(new TickSystem(toReturn, List.of(PhysicsComponent.tag, StateMachineComponent.tag)));
        toReturn.prependSystem(new KeyProcessSystem(toReturn, List.of(MoveKeysComponent.tag, ActionKeysComponent.tag, MovePhysicsComponent.tag)));

        return toReturn;
    }

    private Vec2d createMapInWorld(GameWorld gameWorld, TileType[][] map) {
        List<TileType> tiles = List.of(ROOM, SPAWN, WALL0, EXIT, BUTTON, STAIRS, WALL1, CORNERTOPLEFT, CORNERTOPRIGHT, CORNERBOTTOMRIGHT, CORNERBOTTOMLEFT);
        Vec2d spawnPoint = null;
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i ++) {
                Vec2d position = new Vec2d(ConstantsGameValues.tileSize.x * i, ConstantsGameValues.tileSize.y * j);
                TileType t = map[j][i];
                if (!tiles.contains(t)) {
                    continue;
                }
                if (t == TileType.SPAWN) {
                    spawnPoint = position;
                }
                GameObject g = tileToGameObject(t, position);
                gameWorld.addGameObject(g);
            }
        }

        assert spawnPoint != null;
        return spawnPoint;
    }

    private void populateSpecialTiles(GameWorld gameWorld, TileType[][] map) {
        List<TileType> tiles = List.of(BOX1, BOX0, TRAPS, ENEMY, BOSS, HIDDEN, PLAYER);
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i ++) {
                Vec2d position = new Vec2d(ConstantsGameValues.tileSize.x * i, ConstantsGameValues.tileSize.y * j);
                TileType t = map[j][i];
                if (!tiles.contains(t)) {
                    continue;
                }
                GameObject g = tileToGameObject(t, position);
                gameWorld.addGameObject(g);
            }
        }
    }

    private GameObject tileToGameObject(TileType t, Vec2d position) {
        TransformComponent tc = new TransformComponent(position, ConstantsGameValues.tileSize);
        GameObject toRet = new GameObject(tc, NinGame.getZIndex());

        toRet.setName(t.name());
        toRet.addComponent(new TileComponent(t));
        toRet.addComponent(new SpriteComponent(toRet, NinImageResource.getResource(t.name()), new Vec2d(0,0)));

        switch (t) {
            case ROOM:
            case SPAWN:
                break;
            case WALL0:
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.wallCollisionLayer, false, true));
                break;
            case WALL1:
                PhysicsComponent pWall = new PhysicsComponent(toRet, true, null, ConstantsGameValues.wallMass, ConstantsGameValues.wallRestitution, true);
                toRet.addComponent(pWall);
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.wallCollisionLayer, false, true, pWall));
                break;
            case EXIT:
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.exitCollisionLayer, true, true){
                    @Override
                    public void onCollide(Collision collision) {
                        if (collision.getCollidedObject().hasComponentTag("tile")) {
                            TileComponent t = (TileComponent) collision.getCollidedObject().getComponent("tile");
                            if (t.isPlayer()) {
                                if (ninGame.isDoorOpen()) {
                                    ConstantsSelectionScreen.levelComplete.put(currentLevel, true);
                                    ninGame.completeLevel();
                                }
                            }
                        }
                    }
                });

                StateMachineComponent smExit = new StateMachineComponent();
                smExit.setCurrentState(new ClosedExit(smExit, toRet));
                toRet.addComponent(smExit);
                break;
            case BUTTON:
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.buttonCollisionLayer, true, true){
                    @Override
                    public void onCollide(Collision collision) {
                        if (collision.getCollidedObject().hasComponentTag("tile")) {
                            TileComponent t = (TileComponent) collision.getCollidedObject().getComponent("tile");
                            if (t.isPlayer()) {
                                ninGame.openDoor();
                            }
                        }
                    }
                });

                StateMachineComponent smButton = new StateMachineComponent();
                smButton.setCurrentState(new UnClickedButton(smButton, toRet));
                toRet.addComponent(smButton);
                break;
            case BOX0:
                PhysicsComponent pBox = new PhysicsComponent(toRet, false, Arrays.asList(ConstantsGameValues.gravityDown, null, null), ConstantsGameValues.boxMass, ConstantsGameValues.boxRestitution, false);
                toRet.addComponent(pBox);
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.boxCollisionLayer, false, false, pBox));
                // Deal with Grounding and Physics
                toRet.addComponent(new PhysicsGroundedComponent(
                        toRet,
                        new AAB(tc.getCurrentGameSpacePosition(),
                                tc.getSize().plus(ConstantsGameValues.playerGroundedShapeSizeAdd)),
                        ConstantsGameValues.physicsGroundedCollisionLayer,
                        pBox,
                        List.of(TileType.WALL0, TileType.BOX0, TileType.BOX1)
                ));
                break;
            case BOX1:
                PhysicsComponent pHalfBox = new PhysicsComponent(toRet, false, Arrays.asList(ConstantsGameValues.gravityDown, null, null), ConstantsGameValues.halfboxMass, ConstantsGameValues.halfboxRestitution, false);
                toRet.addComponent(pHalfBox);
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.boxCollisionLayer, false, false, pHalfBox));
                // Deal with Grounding and Physics
                toRet.addComponent(new PhysicsGroundedComponent(
                        toRet,
                        new AAB(tc.getCurrentGameSpacePosition(),
                                tc.getSize().plus(ConstantsGameValues.playerGroundedShapeSizeAdd)),
                        ConstantsGameValues.physicsGroundedCollisionLayer,
                        pHalfBox,
                        List.of(TileType.WALL0, TileType.BOX0, TileType.BOX1)
                ));
                break;
            case CORNERBOTTOMRIGHT:
//                PhysicsComponent pWallBottomRight = new PhysicsComponent(toRet, true, null, ConstantsGameValues.wallMass, ConstantsGameValues.wallRestitution, true);
//                toRet.addComponent(pWallBottomRight);

                Vec2d topLeftBottomRight = tc.getCurrentGameSpacePosition();
                List<Vec2d> pointsBottomRight = new LinkedList<>();
                pointsBottomRight.add(topLeftBottomRight.plus(new Vec2d(0, ConstantsGameValues.tileSize.y)));
                pointsBottomRight.add(topLeftBottomRight.plus(ConstantsGameValues.tileSize));
                pointsBottomRight.add(topLeftBottomRight.plus(new Vec2d(ConstantsGameValues.tileSize.x, 0)));

//                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftBottomRight, pointsBottomRight), ConstantsGameValues.wallCollisionLayer, false, true, pWallBottomRight));

                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftBottomRight, pointsBottomRight), ConstantsGameValues.wallCollisionLayer, false, true));
                break;
            case CORNERBOTTOMLEFT:
//                PhysicsComponent pWallBottomLeft = new PhysicsComponent(toRet, true, null, ConstantsGameValues.wallMass, ConstantsGameValues.wallRestitution, true);
//                toRet.addComponent(pWallBottomLeft);

                Vec2d topLeftBottomLeft = tc.getCurrentGameSpacePosition();
                List<Vec2d> pointsBottomLeft = new LinkedList<>();
                pointsBottomLeft.add(topLeftBottomLeft);
                pointsBottomLeft.add(topLeftBottomLeft.plus(new Vec2d(0, ConstantsGameValues.tileSize.y)));
                pointsBottomLeft.add(topLeftBottomLeft.plus(ConstantsGameValues.tileSize));

//                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftBottomLeft, pointsBottomLeft), ConstantsGameValues.wallCollisionLayer, false, true, pWallBottomLeft));
                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftBottomLeft, pointsBottomLeft), ConstantsGameValues.wallCollisionLayer, false, true));
                break;
            case CORNERTOPRIGHT:
//                PhysicsComponent pWallTopRight = new PhysicsComponent(toRet, true, null, ConstantsGameValues.wallMass, ConstantsGameValues.wallRestitution, true);
//                toRet.addComponent(pWallTopRight);

                Vec2d topLeftTopRight = tc.getCurrentGameSpacePosition();
                List<Vec2d> pointsTopRight = new LinkedList<>();
                pointsTopRight.add(topLeftTopRight);
                pointsTopRight.add(topLeftTopRight.plus(ConstantsGameValues.tileSize));
                pointsTopRight.add(topLeftTopRight.plus(new Vec2d(0, ConstantsGameValues.tileSize.y)));

//                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftTopRight, pointsTopRight), ConstantsGameValues.wallCollisionLayer, false, true, pWallTopRight));

                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftTopRight, pointsTopRight), ConstantsGameValues.wallCollisionLayer, false, true));
                break;
            case CORNERTOPLEFT:
//                PhysicsComponent pWallTopLeft = new PhysicsComponent(toRet, true, null, ConstantsGameValues.wallMass, ConstantsGameValues.wallRestitution, true);
//                toRet.addComponent(pWallTopLeft);

                Vec2d topLeftTopLeft = tc.getCurrentGameSpacePosition();
                List<Vec2d> pointsTopLeft = new LinkedList<>();
                pointsTopLeft.add(topLeftTopLeft);
                pointsTopLeft.add(topLeftTopLeft.plus(new Vec2d(0, ConstantsGameValues.tileSize.y)));
                pointsTopLeft.add(topLeftTopLeft.plus(new Vec2d( ConstantsGameValues.tileSize.x, 0)));

//                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftTopLeft, pointsTopLeft), ConstantsGameValues.wallCollisionLayer, false, true, pWallTopLeft));

                toRet.addComponent(new CollisionComponent(toRet, new Polygon(topLeftTopLeft, pointsTopLeft), ConstantsGameValues.wallCollisionLayer, false, true));
                break;
            case STAIRS:
            case TRAPS:
            case ENEMY:
            case BOSS:
            case HIDDEN:
            case PLAYER:
            default:
                assert false;
                break;
        }

        return toRet;
    }
}
