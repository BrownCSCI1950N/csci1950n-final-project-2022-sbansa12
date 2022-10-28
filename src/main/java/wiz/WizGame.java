package wiz;

import Pair.Pair;
import engine.*;
import engine.AI.DecisionTrees.*;
import engine.AStar.AStar;
import engine.Components.*;
import engine.Shape.AAB;
import engine.Systems.CollisionSystem;
import engine.Systems.KeyProcessSystem;
import engine.Systems.LateTickSystem;
import engine.Systems.TickSystem;
import engine.TerrainGeneration.SpacePartitioning.SpacePartitioning;
import engine.TerrainGeneration.Terrain;
import engine.TerrainGeneration.TerrainGraph.DistanceHeuristic;
import engine.TerrainGeneration.TerrainReader.TerrainReader;
import engine.TerrainGeneration.TileType;
import engine.TerrainGeneration.TerrainGraph.TerrainEdge;
import engine.TerrainGeneration.TerrainGraph.TerrainNode;
import engine.UI.Viewport;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.image.Image;
import wiz.Screens.GameScreen;
import wiz.StateMachinePlayer.IdleStateRightPlayer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WizGame {

    private static Integer zIndex = 1;
    public static final Resource<Sprite> images = new Resource<>();
    Integer initialSeed = 0;
    Viewport viewport;
    GameScreen gameScreen;
    static GameWorld currentGameWorld;
    Terrain mapWorld;
    Random rand;
    static GameObject player;
    GameObject boss;
    List<GameObject> secretHidden = new LinkedList<>();
    List<GameObject> minimapTiles = new LinkedList<>();
    Integer deathCount = 0;
    String deathMessage = "";
    Boolean popUp = false;
    Boolean bossFight = false;
    AStar<TerrainNode, TerrainEdge> aStar = new AStar<>(new DistanceHeuristic());
    GameTileConversion gameTileConversion = new GameTileConversionClosest(Constants.tileSize);
    Random randomGlobal;

    public WizGame() {
        // Setup Resources
        images.putResource("PLAYER", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\PLAYER.png"), new Vec2d(2,9)));
        images.putResource("PROJECTILE_PLAYER", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\PROJECTILE_PLAYER.png"), new Vec2d(1,1)));
        images.putResource("WALL", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\WALL.png"), new Vec2d(1,1)));
        images.putResource("ROOM", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\ROOM.png"), new Vec2d(2,1)));
        images.putResource("SPAWN", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\SPAWN.png"), new Vec2d(1,1)));
        images.putResource("EXIT", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\EXIT.png"), new Vec2d(1,1)));
        images.putResource("TRAPS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\TRAPS.png"), new Vec2d(4,1)));
        images.putResource("STAIRS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\STAIRS.png"), new Vec2d(1,1)));
        images.putResource("ENEMY", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\ENEMY.png"), new Vec2d(2,3)));
        images.putResource("BOSS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\BOSS.png"), new Vec2d(3,1)));
        images.putResource("PROJECTILE_BOSS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\PROJECTILE_BOSS.png"), new Vec2d(1,1)));
    }

    public Integer getDeathCount() {
        return deathCount;
    }
    public String getDeathMessage() {
        return deathMessage;
    }
    public void resetDeathMessage() {
        deathMessage = "";
    }
    public Integer getSeed() {
        return this.initialSeed;
    }
    public void setSeed(Integer seed) {
        this.initialSeed = seed;
    }
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    private static int getZIndex() {
        int toReturn = zIndex;
        zIndex += 1;
        return toReturn;
    }
    public Boolean getPopUp() {
        return popUp;
    }
    public void setPopUp(Boolean newPopUp) {
        this.popUp = newPopUp;
    }
    public Boolean getBossFight() {
        return bossFight;
    }
    public void setBossFight(Boolean newBossFight) {
        this.bossFight = newBossFight;
    }
    public Pair<Terrain, List<GameObject>> getMapWorld() {
        return new Pair<>(mapWorld, currentGameWorld.getSystem("draw").getGameObjects());
    }

    public void startGame() {
        this.minimapTiles.clear();
        this.secretHidden.clear();
        setBossFight(false);
        deathCount = 0;
        deathMessage = "";
        this.rand = new Random(initialSeed);
        if (initialSeed != 2218) {
            currentGameWorld = generateLevel(rand, true);
        } else {
            currentGameWorld = generateBossLevel(rand);
        }
        viewport.setGameWorld(currentGameWorld);
    }

    private GameWorld generateLevel(Random rand, boolean stairs) {
        randomGlobal = rand;
        // Use Space Partition To Generate Level Map
        Terrain terrain = new SpacePartitioning(rand).createTerrain(Constants.mapSize, Constants.minRoomSize, Constants.probabilitySplitRoom, Constants.maxHallWidth);
        if (stairs) {
            terrain.placeSingleTileRandom(TileType.STAIRS, List.of(TileType.ROOM));
        }
        terrain.placeTileRandomlyRooms(TileType.ENEMY, List.of(TileType.ROOM), 0.4F, 0.07F, -1);
        terrain.placeTileRandomlyRoomsCenter(TileType.TRAPS, List.of(TileType.ROOM),0.4F, 0.05F, -1);
        TileType[][] map = terrain.getTileMap();

        mapWorld = terrain;

        // Create Game World
        GameWorld level = createGameWorld(map);

        // Put in Special Tiles
        GameObject spawnPoint = populateGameWorldSpecialTiles(level, terrain.getSpecialTiles());

        // Create Player Game Object
        assert spawnPoint != null;
        player = createPlayer(spawnPoint);

        addPlayerDependentComponents(minimapTiles, player);

        level.addGameObject(player);

        return level;
    }

    private GameObject populateGameWorldSpecialTiles(GameWorld level, Map<TileType, List<Vec2i>> specialTiles) {
        GameObject spawnPoint = null;
        for (TileType t: specialTiles.keySet()) {
            for (Vec2i position: specialTiles.get(t)) {
                GameObject g = tileToGameObject(t, position.x, position.y);
                level.addGameObject(g);
                minimapTiles.add(g);

                assert g != null;
                if (((TileComponent) g.getComponent("tile")).getTileType().equals(TileType.SPAWN)) {
                    spawnPoint = g;
                }
            }
        }

        return spawnPoint;
    }

    private GameWorld createGameWorld(TileType[][] map) {
        GameWorld level = new GameWorld();

        // Add Appropriate Systems [Tick, Key, Collision, Draw, LateTick]
        CollisionSystem collisionSystem = new CollisionSystem(level, List.of("collision", "healthDamage"), true);
        collisionSystem.setLayersCollide(Constants.layersCollide);
        level.prependSystem(collisionSystem);
        level.prependSystem(new KeyProcessSystem(level, List.of("moveKeys", "actionKeys")));
        level.prependSystem(new TickSystem(level, List.of("constantMovement", "timerAction", "sprite", "stateMachine", "discovered", "pathMovement", "AI")));
        level.appendSystem(new LateTickSystem(level, List.of("center")));

        // Add Level Map Game Objects
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i++) {
                GameObject gameObject = tileToGameObject(map[j][i], i, j);
                level.addGameObject(gameObject);
                minimapTiles.add(gameObject);
            }
        }

        return level;
    }

    private GameObject tileToGameObject(TileType t, int x, int y) {
        TransformComponent transformComponent = new TransformComponent(new Vec2d(Constants.tileSize.x * x, Constants.tileSize.y * y), Constants.tileSize);

        if (t == TileType.ROOM) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("ROOM");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.ROOM.name()), new Vec2d(rand.nextInt(2),0)));
            gameObject.addComponent(new TileComponent(TileType.ROOM));
            return gameObject;
        } else if (t == TileType.WALL) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("WALL");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.WALL.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.WALL));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, false, true));
            return gameObject;
        } else if (t == TileType.SPAWN) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("SPAWN");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.SPAWN.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.SPAWN));
            return gameObject;
        } else if (t == TileType.EXIT) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("EXIT");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.EXIT.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.EXIT));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, true, false){
                @Override
                public void onCollide(Collision collision) {
                    if (collision.getCollidedObject().hasComponentTag("player")) {
                        if (bossFight) {
                            gameScreen.setActiveScreen("win");
                        } else {
                            gameScreen.setActiveScreen("tryAgain");
                        }
                    }
                }
            });
            return gameObject;
        } else if (t == TileType.TRAPS) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("TRAP");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.TRAPS.name()), new Vec2d(rand.nextInt(4),0)));
            gameObject.addComponent(new TileComponent(TileType.TRAPS));
            gameObject.addComponent(new HealthDamageComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, 0, Constants.trapsDamage));
            return gameObject;
        } else if (t == TileType.ENEMY) {
            return createEnemy(transformComponent);
        } else if (t == TileType.STAIRS) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("STAIRS");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.STAIRS.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.STAIRS));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, true, false){
                @Override
                public void onCollide(Collision collision) {
                    if (collision.getCollidedObject().hasComponentTag("player")) {
                        currentGameWorld = generateBossLevel(rand);
                        viewport.setGameWorld(currentGameWorld);
                    }
                }
            });
            return gameObject;
        } else if (t == TileType.BOSS) {
            return createBoss(transformComponent);
        } else if (t == TileType.HIDDEN) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("HIDDEN");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.WALL.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.HIDDEN));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, false, true));
            secretHidden.add(gameObject);
            return gameObject;
        } else {
            assert false;
            return null;
        }
    }

    private GameWorld generateBossLevel(Random rand) {
        setBossFight(true);
        randomGlobal = rand;
        try {
            Terrain map = TerrainReader.createTerrain(".\\src\\main\\java\\wiz\\Maps\\boss-room.txt", rand);

            mapWorld = map;

            // Create Game World
            GameWorld level = createGameWorld(map.getTileMap());

            // Put in Special Tiles
            GameObject spawnPoint = populateGameWorldSpecialTiles(level, map.getSpecialTiles());

            // Create Player Game Object
            assert spawnPoint != null;
            player = createPlayer(spawnPoint);

            addPlayerDependentComponents(minimapTiles, player);

            level.addGameObject(player);

            return level;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rand = new Random(initialSeed + 10);
            randomGlobal = rand;
            return generateLevel(rand, false);
        }
    }

    private GameObject createPlayer(GameObject spawnPoint) {
        Vec2d respawnLocation = spawnPoint.getTransform().getCurrentGameSpacePosition().plus(Constants.spawnPointAdd, Constants.spawnPointAdd);
        TransformComponent transformComponentPlayer = new TransformComponent(respawnLocation, Constants.playerSize);
        GameObject playerMake = new GameObject(transformComponentPlayer, getZIndex());
        playerMake.setName("PLAYER");
        playerMake.addComponent(new PlayerComponent());
        playerMake.addComponent(new TileComponent(TileType.PLAYER));

        StateMachineComponent sm = new StateMachineComponent();
        sm.setCurrentState(new IdleStateRightPlayer(sm, playerMake));
        playerMake.addComponent(sm);

        playerMake.addComponent(new MoveKeysComponent(playerMake, Constants.movementKeys, Constants.movementSpeed));
        playerMake.addComponent(new RespawnComponent(playerMake, respawnLocation));
        viewport.setCurrentGamePointCenter(respawnLocation);
        playerMake.addComponent(new CenterComponent(playerMake, viewport));
        playerMake.addComponent(new HealthDamageComponent(playerMake, new AAB(transformComponentPlayer.getCurrentGameSpacePosition(), transformComponentPlayer.getSize()), Constants.playerCollisionLayer, Constants.playerMaxHealth, 0){
            @Override
            public void zeroHealthScript() {
                deathCount += 1;
                if (!bossFight) {
                    deathMessage = "Player got Slimed!";
                } else {
                    deathMessage = "Player got Catted!";
                }

                RespawnComponent respawnComponent = (RespawnComponent) playerMake.getComponent("respawn");
                respawnComponent.script();
            }
        });
        playerMake.addComponent(new CollisionComponent(playerMake, new AAB(transformComponentPlayer.getCurrentGameSpacePosition(), transformComponentPlayer.getSize()), Constants.playerCollisionLayer, false, false));
        playerMake.addComponent(new ActionKeysComponent(playerMake, Constants.projectileKey, true){

            @Override
            public void action() {
                createProjectile(playerMake);
            }
        });

        return playerMake;
    }

    public static Direction directionProjectileShootPlayer(Vec2d velocity) {
        if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
            if (velocity.x < 0) {
                return Direction.LEFT;
            } else if (velocity.x > 0) {
                return Direction.RIGHT;
            } else {
                assert false;
                return Direction.NONE;
            }
        } else if (Math.abs(velocity.x) < Math.abs(velocity.y)) {
            if (velocity.y < 0) {
                return Direction.UP;
            } else if (velocity.y > 0) {
                return Direction.DOWN;
            } else {
                assert false;
                return Direction.NONE;
            }
        } else {
            // abs(x) == abs(y)
            assert velocity.isZero();
            return Direction.RIGHT;
        }
    }

    public static double degToRad(double angle) {
        return angle * (Math.PI / 180.0);
    }

    public static Direction directionProjectileShootFluid(Vec2d velocity) {
        double angle = velocity.angle();
        if ((degToRad(337.5) < angle && angle <= degToRad(360.0)) || (0 < angle && angle <= degToRad(22.5))) {
            return Direction.LEFT;
        } else if (degToRad(22.5) < angle && angle <= degToRad(67.5)) {
            return Direction.NW;
        } else if (degToRad(67.5) < angle && angle <= degToRad(112.5)) {
            return Direction.UP;
        } else if (degToRad(112.5) < angle && angle <= degToRad(157.5)) {
            return Direction.NE;
        } else if (degToRad(157.5) < angle && angle <= degToRad(202.5)) {
            return Direction.RIGHT;
        } else if (degToRad(202.5) < angle && angle <= degToRad(247.5)) {
            return Direction.SE;
        } else if (degToRad(247.5) < angle && angle <= degToRad(292.5)) {
            return Direction.DOWN;
        } else if (degToRad(292.5) < angle && angle <= degToRad(337.5)) {
            return Direction.SW;
        } else {
            // Rounding Error Possibilities as Double
            return Direction.LEFT;
        }
    }

    public static void createProjectile(GameObject shooter) {
        Direction projectileDirection;
        if (shooter.hasComponentTag("player")) {
            Vec2d velocityShooter = shooter.getTransform().getVelocity();
            projectileDirection = directionProjectileShootPlayer(velocityShooter);
        } else {
            Vec2d velocity = shooter.getTransform().getCurrentGameSpacePosition().minus(player.getTransform().getCurrentGameSpacePosition());
            projectileDirection = directionProjectileShootFluid(velocity);
        }


        Vec2d initialPosition = null;
        Vec2d velocityProjectile = null;


        if (projectileDirection ==  Direction.LEFT) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(Constants.projectileSize.x, (Constants.projectileSize.y-Constants.playerSize.y)/2));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedPlayer, 0);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(Constants.projectileSize.x, (Constants.projectileSize.y-Constants.tileSize.y)/2));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedEnemy, 0);
            }
        } else if (projectileDirection ==  Direction.RIGHT) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(-Constants.playerSize.x, (Constants.projectileSize.y-Constants.playerSize.y)/2));
                velocityProjectile = new Vec2d(Constants.projectileSpeedPlayer, 0);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(-Constants.tileSize.x, (Constants.projectileSize.y-Constants.tileSize.y)/2));
                velocityProjectile = new Vec2d(Constants.projectileSpeedEnemy, 0);
            }
        } else if (projectileDirection ==  Direction.UP) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.playerSize.x)/2, Constants.projectileSize.y));
                velocityProjectile = new Vec2d(0,-Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.tileSize.x)/2, Constants.projectileSize.y));
                velocityProjectile = new Vec2d(0,-Constants.projectileSpeedEnemy);
            }
        } else if (projectileDirection ==  Direction.DOWN) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.playerSize.x)/2, -Constants.playerSize.y));
                velocityProjectile = new Vec2d(0, Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.tileSize.x)/2, -Constants.tileSize.y));
                velocityProjectile = new Vec2d(0, Constants.projectileSpeedEnemy);
            }
        } else if (projectileDirection ==  Direction.SE) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( -Constants.playerSize.x, -Constants.playerSize.y));
                velocityProjectile = new Vec2d(Constants.projectileSpeedPlayer, Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( -Constants.tileSize.x, -Constants.tileSize.y));
                velocityProjectile = new Vec2d(Constants.projectileSpeedEnemy, Constants.projectileSpeedEnemy);
            }
        } else if (projectileDirection ==  Direction.SW) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( Constants.projectileSize.x, -Constants.playerSize.y));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedPlayer, Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( Constants.tileSize.x, -Constants.tileSize.y));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedEnemy, Constants.projectileSpeedEnemy);
            }
        } else if (projectileDirection ==  Direction.NE) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( -Constants.playerSize.x, Constants.playerSize.y));
                velocityProjectile = new Vec2d(Constants.projectileSpeedPlayer, -Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( -Constants.tileSize.x, Constants.tileSize.y));
                velocityProjectile = new Vec2d(Constants.projectileSpeedEnemy, -Constants.projectileSpeedEnemy);
            }
        } else if (projectileDirection ==  Direction.NW) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( Constants.projectileSize.x, Constants.playerSize.y));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedPlayer, -Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( Constants.tileSize.x, Constants.tileSize.y));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedEnemy, -Constants.projectileSpeedEnemy);
            }
        }

        TransformComponent transformComponentProjectile = new TransformComponent(initialPosition, Constants.projectileSize);
        GameObject projectile = new GameObject(transformComponentProjectile, getZIndex());
        Integer collisionLayer;
        if (shooter.hasComponentTag("player")) {
            projectile.setName("PROJECTILE_PLAYER");
            projectile.addComponent(new SpriteComponent(projectile, images.getResource("PROJECTILE_PLAYER"), new Vec2d(0,0)));
            collisionLayer = Constants.projectilesPlayerCollisionLayer;
            projectile.addComponent(new TimerActionComponent(Constants.projectileLifeTime){
                @Override
                public void script() {
                    currentGameWorld.removeGameObject(projectile);
                }
            });
        } else {
            projectile.setName("PROJECTILE_BOSS");
            projectile.addComponent(new SpriteComponent(projectile, images.getResource("PROJECTILE_BOSS"), new Vec2d(0,0)));
            collisionLayer = Constants.projectilesEnemyCollisionLayer;
            projectile.addComponent(new TimerActionComponent(Constants.projectileLifeTime.multiply(new BigDecimal("2"))){
                @Override
                public void script() {
                    currentGameWorld.removeGameObject(projectile);
                }
            });
        }
        projectile.addComponent(new ConstantMovementComponent(projectile, velocityProjectile));
        projectile.addComponent(new CollisionComponent(projectile, new AAB(transformComponentProjectile.getCurrentGameSpacePosition(), transformComponentProjectile.getSize()), collisionLayer, true, false){
            @Override
            public void onCollide(Collision collision) {

//                CollisionComponent otherObjectCollisionComponent = (CollisionComponent) collision.getCollidedObject().getComponent("collision");

                // Collision with Static Object
//                if (otherObjectCollisionComponent.isStaticObject()) {
                    currentGameWorld.removeGameObject(projectile);
//                }
            }
        });
        projectile.addComponent(new HealthDamageComponent(projectile, new AAB(transformComponentProjectile.getCurrentGameSpacePosition(), transformComponentProjectile.getSize()), collisionLayer, null, Constants.projectileDamage){
            @Override
            public void onCollide(Collision collision) {
                super.onCollide(collision);
                currentGameWorld.removeGameObject(projectile);
            }
        });

        currentGameWorld.addGameObject(projectile);
        ((CollisionSystem) currentGameWorld.getSystem("collision")).checkCollision((CollisionComponent) projectile.getComponent("collision"), Constants.mapCollisionLayer);
    }

    public void addPlayerDependentComponents(List<GameObject> toAdd, GameObject player) {
        for (GameObject gO: toAdd) {
            gO.addComponent(new DiscoveredComponent(gO, player, Constants.distanceDiscover));
        }
    }

    public double getBossHealth() {
        HealthDamageComponent h = (HealthDamageComponent) boss.getComponent("healthDamage");
        return (double) h.getCurrentHealth()/h.getMaxHealth();
    }

    public GameObject createBoss(TransformComponent transformComponent) {
        GameObject gameObject = new GameObject(transformComponent, getZIndex());
        gameObject.setName("BOSS");
        gameObject.addComponent(new TileComponent(TileType.BOSS));

        // Boss Has Certain Health and Damages When Touched
        gameObject.addComponent(new HealthDamageComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.moveEnemyCollisionLayer, Constants.bossMaxHealth, Constants.trapsDamage){
            @Override
            public void zeroHealthScript() {
                currentGameWorld.removeGameObject(this.gameObject);
                for (GameObject g: secretHidden) {
                    currentGameWorld.removeGameObject(g);
                }
            }
        });

        gameObject.addComponent(new ConstantAnimationComponent(gameObject, WizGame.images.getResource(TileType.BOSS.name()), List.of(new Vec2d(0,0), new Vec2d(2,0)), Constants.bossMoveTime.divideToIntegralValue(new BigDecimal("2"))));

        // Boss AI:
        //   Root: Selector:
        //       Heal: Sequence:
        //           Health Below x%: Condition
        //           5 Times: Wrapper:
        //               RunHeal: Sequence:
        //                   Move Away: Action
        //                   Heal: Action
        //       Attack: Random Selector
        //           Shoot: Sequence:
        //               Player in Range: Condition
        //               Shoot: Action
        //           Move: Sequence:
        //               Player in View: Condition
        //               Move: Action

        Sequence runHealSequence = new Sequence("RunHeal", new LinkedList<>(List.of(new MoveAwayAction(), new HealAction())));
        Sequence healSequence = new Sequence("Heal", new LinkedList<>(List.of(new LowHealthCondition(), new RepeatWrapper(5, runHealSequence))));
        Sequence shootSequence = new Sequence("Shoot", new LinkedList<>(List.of(new PlayerRangeCondition(gameObject, 240000), new AttackAction())));
        Sequence moveSequence = new Sequence("Move", new LinkedList<>(List.of(new PlayerRangeCondition(gameObject, 120000), new MoveAction(gameObject, true))));

        Selector s = new Selector(new LinkedList<>(List.of(healSequence, new RandomSelector(new LinkedList<>(List.of(shootSequence, moveSequence)), rand))));

        gameObject.addComponent(new BTComponent(s, Constants.bossMoveTime));

        boss = gameObject;
        return gameObject;
    }
    public GameObject createEnemy(TransformComponent transformComponent) {
        GameObject gameObject = new GameObject(transformComponent, getZIndex());
        gameObject.setName("ENEMY");
        int randColor = rand.nextInt(3);
        gameObject.addComponent(new ConstantAnimationComponent(gameObject, images.getResource(TileType.ENEMY.name()), List.of(new Vec2d(0,randColor), new Vec2d(1, randColor)), Constants.enemyMoveTime.divideToIntegralValue(new BigDecimal("2"))));
        gameObject.addComponent(new TileComponent(TileType.ENEMY));
        gameObject.addComponent(new HealthDamageComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, Constants.trapsMaxHealth, Constants.trapsDamage){
            @Override
            public void zeroHealthScript() {
                currentGameWorld.removeGameObject(this.gameObject);
            }
        });

        // Enemy AI:
        //   Root: Sequence:
        //       Player in View: Condition
        //       Move: Action

        Sequence moveSequence = new Sequence("Move", new LinkedList<>(List.of(new PlayerRangeCondition(gameObject, 90000), new MoveAction(gameObject, false))));

        gameObject.addComponent(new BTComponent(moveSequence, Constants.enemyMoveTime));

        return gameObject;
    }

    private class LowHealthCondition implements Condition {
        @Override
        public Status update(float seconds) {
            if (getBossHealth() < 0.1) {
                return Status.SUCCESS;
            } else {
                return Status.FAIL;
            }
        }

        @Override
        public void reset() {

        }
    }

    private class MoveAwayAction implements Action {
        @Override
        public Status update(float seconds) {
            List<Vec2i> gOTile = gameTileConversion.gameToTile(boss.getTransform().getCurrentGameSpacePosition());
            List<Vec2i> playerTile = gameTileConversion.gameToTile(player.getTransform().getCurrentGameSpacePosition());

            List<TerrainEdge> path = aStar.run(new TerrainNode(gOTile.get(0), mapWorld.getTileMap(), List.of(TileType.ROOM)),
                    new TerrainNode(playerTile.get(0), mapWorld.getTileMap(), List.of(TileType.ROOM)));

            // Cannot Move Backwards
            Vec2i nextPosition = gOTile.get(0).plus(gOTile.get(0).minus(path.get(0).getTo().getCurrentPosition()));
            if (mapWorld.getTileMap()[nextPosition.y][nextPosition.x] != TileType.ROOM) {
                return Status.SUCCESS;
            }

            boss.removeComponent("sprite");
            boss.addComponent(new ConstantAnimationComponent(boss, WizGame.images.getResource(TileType.BOSS.name()), List.of(new Vec2d(0,0), new Vec2d(2,0)), Constants.bossMoveTime.divideToIntegralValue(new BigDecimal("2"))));

            boss.getTransform().setCurrentGameSpacePosition(gameTileConversion.tileToGame(nextPosition));
            return Status.SUCCESS;
        }

        @Override
        public void reset() {

        }
    }

    private class HealAction implements Action {
        @Override
        public Status update(float seconds) {
            HealthDamageComponent h = (HealthDamageComponent) boss.getComponent("healthDamage");
            h.heal(Constants.projectileDamage);

            return Status.SUCCESS;
        }

        @Override
        public void reset() {

        }
    }

    private static class PlayerRangeCondition implements Condition {
        GameObject gO;
        double range;
        public PlayerRangeCondition(GameObject gO, double range) {
            this.gO = gO;
            this.range = range;
        }

        @Override
        public Status update(float seconds) {
            Vec2d centerPlayer = player.getTransform().getCurrentGameSpacePosition().plus(player.getTransform().getSize().sdiv(2));
            Vec2d centerBoss = gO.getTransform().getCurrentGameSpacePosition().plus(gO.getTransform().getSize().sdiv(2));
            double currentDistance = centerPlayer.dist2(centerBoss);
            if (currentDistance < range) {
                return Status.SUCCESS;
            }

            return Status.FAIL;
        }

        @Override
        public void reset() {

        }
    }

    private class AttackAction implements Action {
        @Override
        public Status update(float seconds) {
            boss.removeComponent("sprite");
            boss.addComponent(new SpriteComponent(boss, WizGame.images.getResource(TileType.BOSS.name()), new Vec2d(1,0)));
            createProjectile(boss);

            return Status.SUCCESS;
        }

        @Override
        public void reset() {

        }
    }

    private class MoveAction implements Action {
        GameObject gO;
        boolean bossP;
        public MoveAction(GameObject gO, boolean bossP){
            this.gO = gO;
            this.bossP = bossP;
        }
        @Override
        public Status update(float seconds) {
            if (bossP) {
                boss.removeComponent("sprite");
                boss.addComponent(new ConstantAnimationComponent(boss, WizGame.images.getResource(TileType.BOSS.name()), List.of(new Vec2d(0,0), new Vec2d(2,0)), Constants.bossMoveTime.divideToIntegralValue(new BigDecimal("2"))));
            }

            List<Vec2i> gOTile = gameTileConversion.gameToTile(gO.getTransform().getCurrentGameSpacePosition());
            List<Vec2i> playerTileList = gameTileConversion.gameToTile(player.getTransform().getCurrentGameSpacePosition());

            int randInt = rand.nextInt(playerTileList.size());
            Vec2i playerTile = playerTileList.get(randInt);

            List<TerrainEdge> path = aStar.run(new TerrainNode(gOTile.get(0), mapWorld.getTileMap(), List.of(TileType.ROOM)),
                    new TerrainNode(playerTile, mapWorld.getTileMap(), List.of(TileType.ROOM)));

            if (path.size() > 0) {
                TerrainNode nextPosition = path.get(0).getTo();
                gO.getTransform().setCurrentGameSpacePosition(gameTileConversion.tileToGame(nextPosition.getCurrentPosition()));
            }
            return Status.SUCCESS;
        }

        @Override
        public void reset() {

        }
    }
}
