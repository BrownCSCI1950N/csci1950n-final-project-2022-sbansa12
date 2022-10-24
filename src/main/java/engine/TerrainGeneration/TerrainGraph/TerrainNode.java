package engine.TerrainGeneration.TerrainGraph;

import engine.AStar.Node;
import engine.TerrainGeneration.TileType;

import engine.support.Vec2i;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TerrainNode implements Node<TerrainNode, TerrainEdge> {
    Vec2i currentPosition;
    TileType[][] map;
    List<TileType> allowedPathTiles;

    /**
     *
     * @param currentPosition - current position in the 2D map
     * @param map - 2D map of the game space
     * @param allowedPathTiles - tiles that we allow to be in the path, therefore all other tiles can potentially overlap
     */
    public TerrainNode(Vec2i currentPosition, TileType[][] map, List<TileType> allowedPathTiles) {
        this.currentPosition = currentPosition;
        this.map = map;
        this.allowedPathTiles = allowedPathTiles;
    }

    @Override
    public List<TerrainEdge> getEdges() {
        List<TerrainEdge> toReturn = new LinkedList<>();

        Vec2i E = new Vec2i(currentPosition.x + 1, currentPosition.y);
        Vec2i W = new Vec2i(currentPosition.x - 1, currentPosition.y);
        Vec2i S = new Vec2i(currentPosition.x, currentPosition.y + 1);
        Vec2i N = new Vec2i(currentPosition.x, currentPosition.y - 1);

        List<Vec2i> possibleNodes = List.of(E, W, S, N);

        for (Vec2i n: possibleNodes) {
            if (inMap(n, map)) {
                if (allowedPathTiles.contains(map[n.y][n.x])) {
                    toReturn.add(new TerrainEdge(this, new TerrainNode(n, map, allowedPathTiles), 10.0));
                }
            }
        }

        Vec2i SE = new Vec2i(currentPosition.x + 1, currentPosition.y + 1);
        Vec2i NW = new Vec2i(currentPosition.x - 1, currentPosition.y - 1);
        Vec2i NE = new Vec2i(currentPosition.x + 1, currentPosition.y - 1);
        Vec2i SW = new Vec2i(currentPosition.x - 1, currentPosition.y + 1);

        List<Vec2i> possibleNodesD = List.of(SE, NW, NE, SW);

        for (Vec2i n: possibleNodesD) {
            if (inMap(n, map)) {
                if (allowedPathTiles.contains(map[n.y][n.x])) {
                    toReturn.add(new TerrainEdge(this, new TerrainNode(n, map, allowedPathTiles), 14.0));
                }
            }
        }

        return toReturn;
    }

    private boolean inMap(Vec2i point, TileType[][] map) {
        return (point.y >= 0) && (point.y < map.length) && (point.x >= 0) && (point.x < map[0].length);
    }

    public Vec2i getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TerrainNode) {
            TerrainNode node = (TerrainNode) obj;
            return node.currentPosition.equals(this.currentPosition);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "N:" + currentPosition;
    }
}
