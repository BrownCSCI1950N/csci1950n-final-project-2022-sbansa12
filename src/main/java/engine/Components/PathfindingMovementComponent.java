package engine.Components;

import engine.AStar.AStar;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.TerrainGeneration.TerrainGraph.TerrainEdge;
import engine.TerrainGeneration.TerrainGraph.TerrainNode;
import engine.support.Vec2i;
import javafx.scene.canvas.GraphicsContext;

import java.math.BigDecimal;
import java.util.List;

public class PathfindingMovementComponent implements Component {
    AStar<TerrainNode, TerrainEdge> algo;
    GameObject gameObject;
    GameObject player;
    BigDecimal countAlgo;
    BigDecimal timerAlgo;
    BigDecimal countMove;
    BigDecimal timerMove;
    TileType[][] map;
    List<TileType> allowedTiles;
    GameTileConversion ttt;
    List<TerrainEdge> path;

    public PathfindingMovementComponent(GameObject gO, GameObject player, AStar<TerrainNode, TerrainEdge> algo, TileType[][] map, GameTileConversion ttt, List<TileType> allowedTiles, BigDecimal timerAlgo, BigDecimal timerMove) {
        this.gameObject = gO;
        this.player = player;
        this.algo = algo;
        this.map = map;
        this.ttt = ttt;
        this.allowedTiles = allowedTiles;
        this.countAlgo = new BigDecimal("0");
        this.timerAlgo = timerAlgo;
        this.countMove = new BigDecimal("0");
        this.timerMove = timerMove;

        // Find Path on Creation so Can Start Moving
        List<Vec2i> gOTile = ttt.gameToTile(gameObject.getTransform().getCurrentGameSpacePosition());
        List<Vec2i> playerTile = ttt.gameToTile(player.getTransform().getCurrentGameSpacePosition());

        path = algo.run(new TerrainNode(gOTile.get(0), map, allowedTiles),
                new TerrainNode(playerTile.get(0), map, allowedTiles));
    }

    public String stringMap(TileType[][] map) {

        StringBuilder toReturn = new StringBuilder();
        for (TileType[] tileTypes : map) {
            for (TileType tileType : tileTypes) {
                toReturn.append(tileType).append(" ");
            }
            toReturn.append("\n");
        }
        return toReturn.toString();
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        runAlgo(nanosSinceLastTick);
        move(nanosSinceLastTick);
    }

    private void runAlgo(long nanosSinceLastTick) {
        countAlgo = countAlgo.add(new BigDecimal(nanosSinceLastTick));
        if (countAlgo.compareTo(timerAlgo) < 0) {
            return;
        }
        countAlgo = new BigDecimal("0");


        List<Vec2i> gOTile = ttt.gameToTile(gameObject.getTransform().getCurrentGameSpacePosition());
        List<Vec2i> playerTile = ttt.gameToTile(player.getTransform().getCurrentGameSpacePosition());

        path = algo.run(new TerrainNode(gOTile.get(0), map, allowedTiles),
                        new TerrainNode(playerTile.get(0), map, allowedTiles));
    }

    private void move(long nanosSinceLastTick) {
        countMove = countMove.add(new BigDecimal(nanosSinceLastTick));
        if (countMove.compareTo(timerMove) < 0) {
            return;
        }
        countMove = new BigDecimal("0");

        if (path.size() == 0) {
            return;
        }

        TerrainNode nextPosition = path.get(0).getTo();
        gameObject.getTransform().setCurrentGameSpacePosition(ttt.tileToGame(nextPosition.getCurrentPosition()));
        path.remove(0);
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "pathMovement";
    }
}
