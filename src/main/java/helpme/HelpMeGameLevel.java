package helpme;

import engine.Components.*;
import engine.GameObject;
import engine.GameWorld;
import engine.Shape.AAB;
import engine.Sprite;
import engine.Systems.CollisionSystem;
import engine.Systems.KeyProcessSystem;
import engine.Systems.TickSystem;
import engine.TerrainGeneration.LevelParseException;
import engine.TerrainGeneration.MapReader.MapReader;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import helpme.Animations.button.UnClickedButton;
import helpme.Animations.exit.ClosedExit;
import helpme.Constants.ConstantsGameValues;

import java.io.IOException;
import java.util.*;

public class HelpMeGameLevel {
    String currentLevel = "00";
    HelpMeGame hel;
    Boolean exit1Complete;
    Boolean exit2Complete;
    public HelpMeGameLevel(HelpMeGame hel) {
        this.hel = hel;
    }

    public void setCurrentLevel(String newLevel) throws LevelParseException, IOException {
        this.currentLevel = newLevel;
        TileType[][] map = MapReader.createTerrain(ConstantsGameValues.mapsPathway + "\\" + newLevel + ".txt", ConstantsGameValues.mapMustInclude);

        MapReader.checkMapSize(map, ConstantsGameValues.mapSize, newLevel);

        GameWorld gameWorld = createWorld();
        Map<TileType, Vec2d> spawns = createMapInWorld(gameWorld, map);
        populateSpecialTiles(gameWorld, map);
        hel.setWorld(gameWorld, spawns);
    }

    public void resetCurrentLevel() throws LevelParseException, IOException {
        TileType[][] map = MapReader.createTerrain(ConstantsGameValues.mapsPathway + "\\" + this.currentLevel + ".txt", ConstantsGameValues.mapMustInclude);

        MapReader.checkMapSize(map, ConstantsGameValues.mapSize, this.currentLevel);

        GameWorld gameWorld = createWorld();
        Map<TileType, Vec2d> spawns = createMapInWorld(gameWorld, map);
        populateSpecialTiles(gameWorld, map);
        hel.setWorld(gameWorld, spawns);
    }

    private GameWorld createWorld() {
        GameWorld toReturn = new GameWorld();

        // Systems: [Keys, Mouse, Tick, Collision, Draw]
        Map<String, Boolean> accountVel = new HashMap<>();
        accountVel.put(CollisionComponent.tag, true);
        accountVel.put(HealthDamageComponent.tag, false);
        accountVel.put(PhysicsGroundedComponent.tag, true);
        CollisionSystem c = new CollisionSystem(toReturn,  List.of(CollisionComponent.tag, HealthDamageComponent.tag, PhysicsGroundedComponent.tag), true, accountVel);
        c.setLayersCollide(ConstantsGameValues.layersCollide);
        c.removeTagsCollide(PhysicsGroundedComponent.tag, PhysicsGroundedComponent.tag);
        c.addTagsCollide(CollisionComponent.tag, PhysicsGroundedComponent.tag);
        toReturn.prependSystem(c);
        toReturn.prependSystem(new TickSystem(toReturn, List.of(PhysicsComponent.tag, StateMachineComponent.tag)));
        toReturn.prependSystem(new KeyProcessSystem(toReturn, List.of(MoveKeysComponent.tag, ActionKeysComponent.tag, MovePhysicsComponent.tag)));

        return toReturn;
    }

    private Map<TileType, Vec2d> createMapInWorld(GameWorld gameWorld, TileType[][] map) throws LevelParseException {
        List<TileType> tiles = ConstantsGameValues.mapTiles;
        Map<TileType, Vec2d> toReturn = new HashMap<>();
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i ++) {
                Vec2d position = new Vec2d(ConstantsGameValues.tileSize.x * i, ConstantsGameValues.tileSize.y * j);
                TileType t = map[j][i];
                if (!tiles.contains(t)) {
                    if (ConstantsGameValues.backWall.contains(t)) {
                        t = TileType.WALL1;
                    } else {
                        t = TileType.ROOM;
                    }
                }
                GameObject g = tileToGameObject(t, position);

                gameWorld.addGameObject(g);

                if (ConstantsGameValues.spawns.contains(t)) {
                    toReturn.put(t, position);
                }
            }
        }

        return toReturn;
    }

    private void populateSpecialTiles(GameWorld gameWorld, TileType[][] map) throws LevelParseException {
        List<TileType> tiles = ConstantsGameValues.specialTiles;
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i ++) {
                Vec2d position = new Vec2d(ConstantsGameValues.tileSize.x * i, ConstantsGameValues.tileSize.y * j);
                TileType t = map[j][i];
                GameObject g = tileToGameObject(t, position);
                if (!tiles.contains(t)) {
                    continue;
                }
                gameWorld.addGameObject(g);
            }
        }
    }

    private GameObject tileToGameObject(TileType t, Vec2d position) throws LevelParseException {
        // Tile Position
        TransformComponent tc = new TransformComponent(position, ConstantsGameValues.tileSize);
        GameObject toRet = new GameObject(tc, HelpMeGame.getZIndex());

        // Tile Sprite
        toRet.setName(t.name());
        toRet.addComponent(new TileComponent(t));
        Sprite s = HelpMeImageResource.getResource(t.name());
        if (s == null) {
            throw new LevelParseException(t.name() + " resource not found.");
        }
        toRet.addComponent(new SpriteComponent(toRet, s, new Vec2d(0,0)));

        switch (t) {
            case ROOM:
            case SPAWN1:
            case SPAWN2:
                break;
            case WALL1:
                // REQUIREMENTS:
                //      you cannot bounce off a wall
                //	    you cannot walk through a wall

                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.wallCollisionLayer, false, true));
                break;
            case EXIT1:
                // REQUIREMENTS:
                //      if open, and correct player exits, exit complete
                //	    else not
                //      once both exits complete level complete

                exit1Complete = false;

                // EXIT Collision Component
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.exitCollisionLayer, true, false){
                    @Override
                    public void onCollide(Collision collision) {
                        if (collision.getCollidedObject().hasComponentTag("tile")) {
                            TileComponent t = (TileComponent) collision.getCollidedObject().getComponent("tile");
                            if (t.isTile(TileType.PLAYER1)) {
                                if (hel.isDoorOpen()) {
                                    exit1Complete = true;
                                    hel.turnOffMovement(TileType.PLAYER1);
                                    checkLevelComplete();
                                }
                            }
                        }
                    }
                });

                // EXIT Animation Component
                StateMachineComponent smExit1 = new StateMachineComponent();
                smExit1.setCurrentState(new ClosedExit(smExit1, toRet, t));
                toRet.addComponent(smExit1);
                break;
            case EXIT2:
                // REQUIREMENTS:
                //      if open, and correct player exits, exit complete
                //	    else not
                //      once both exits complete level complete

                exit2Complete = false;

                // EXIT Collision Component
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.exitCollisionLayer, true, false){
                    @Override
                    public void onCollide(Collision collision) {
                        if (collision.getCollidedObject().hasComponentTag("tile")) {
                            TileComponent t = (TileComponent) collision.getCollidedObject().getComponent("tile");
                            if (t.isTile(TileType.PLAYER2)) {
                                if (hel.isDoorOpen()) {
                                    exit2Complete = true;
                                    hel.turnOffMovement(TileType.PLAYER2);
                                    checkLevelComplete();
                                }
                            }
                        }
                    }
                });

                // EXIT Animation Component
                StateMachineComponent smExit2 = new StateMachineComponent();
                smExit2.setCurrentState(new ClosedExit(smExit2, toRet, t));
                toRet.addComponent(smExit2);
                break;
            case BUTTON:
                // REQUIREMENTS:
                //      if collided and not clicked, clicked
                //	    else nothing

                // Button Collision Component
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.buttonCollisionLayer, true, false){
                    @Override
                    public void onCollide(Collision collision) {
                        if (collision.getCollidedObject().hasComponentTag("tile")) {
                            TileComponent t = (TileComponent) collision.getCollidedObject().getComponent("tile");
                            if (t.isPlayer()) {
                                hel.openDoor();
                            }
                        }
                    }
                });

                // Button Animation Component
                StateMachineComponent smButton = new StateMachineComponent();
                smButton.setCurrentState(new UnClickedButton(smButton, toRet));
                toRet.addComponent(smButton);
                break;
            case BOX1:
                PhysicsComponent pBox = new PhysicsComponent(toRet, false, Arrays.asList(ConstantsGameValues.gravityDown, null, null, ConstantsGameValues.gravityRest), ConstantsGameValues.boxMass, ConstantsGameValues.boxRestitution, false, ConstantsGameValues.boxFriction);
                toRet.addComponent(pBox);
                toRet.addComponent(new CollisionComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize()), ConstantsGameValues.boxCollisionLayer, false, false, pBox));
                // Deal with Grounding and Physics
                toRet.addComponent(new PhysicsGroundedComponent(
                        toRet,
                        new AAB(tc.getCurrentGameSpacePosition(),
                                tc.getSize().plus(ConstantsGameValues.playerGroundedShapeSizeAdd)),
                        ConstantsGameValues.physicsGroundedCollisionLayer,
                        pBox,
                        List.of(TileType.WALL1, TileType.BOX1, TileType.BOX2)
                ));
                break;
            case TRAP1:
                tc.setCurrentGameSpacePositionNoVelocity(tc.getCurrentGameSpacePosition().plus(ConstantsGameValues.trapPositionAdd));
                toRet.addComponent(new HealthDamageComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize().plus(ConstantsGameValues.trapShapeAdd)), ConstantsGameValues.wallCollisionLayer, 0, ConstantsGameValues.trapsDamage) {
                    @Override
                    public void onCollide(Collision collision) {
                        if (((TileComponent) collision.getCollidedObject().getComponent("tile")).isTile(TileType.PLAYER1)) {
                            return;
                        }

                        super.onCollide(collision);
                    }
                });
                break;
            case TRAP2:
                tc.setCurrentGameSpacePositionNoVelocity(tc.getCurrentGameSpacePosition().plus(ConstantsGameValues.trapPositionAdd));
                toRet.addComponent(new HealthDamageComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize().plus(ConstantsGameValues.trapShapeAdd)), ConstantsGameValues.wallCollisionLayer, 0, ConstantsGameValues.trapsDamage) {
                    @Override
                    public void onCollide(Collision collision) {
                        if (((TileComponent) collision.getCollidedObject().getComponent("tile")).isTile(TileType.PLAYER2)) {
                            return;
                        }

                        super.onCollide(collision);
                    }
                });
                break;
            case TRAP3:
                tc.setCurrentGameSpacePositionNoVelocity(tc.getCurrentGameSpacePosition().plus(ConstantsGameValues.trapPositionAdd));
                toRet.addComponent(new HealthDamageComponent(toRet, new AAB(tc.getCurrentGameSpacePosition(), tc.getSize().plus(ConstantsGameValues.trapShapeAdd)), ConstantsGameValues.wallCollisionLayer, 0, ConstantsGameValues.trapsDamage));
                break;
            case ENEMY:
            case BOX2:
            case WALL2:
            case CORNERBOTTOMRIGHT:
            case CORNERBOTTOMLEFT:
            case CORNERTOPRIGHT:
            case CORNERTOPLEFT:
            case STAIRS:
            case BOSS:
            case HIDDEN:
            case PLAYER1:
            case PLAYER2:
            case BREAKABLE:
            default:
                throw new LevelParseException("Invalid Tile Found in Map");
        }

        return toRet;
    }

    private void checkLevelComplete() {
        if (exit1Complete && exit2Complete) {
            ConstantsGameValues.levelComplete.put(currentLevel, true);
            hel.levelComplete();
        }
    }
}
