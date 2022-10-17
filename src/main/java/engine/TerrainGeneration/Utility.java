package engine.TerrainGeneration;

import engine.support.Vec2i;

import java.util.List;
import java.util.Map;

public class Utility {
    private static TileType[][] convertTerrainFullTileMap(Terrain map) {
        TileType[][] toReturn = map.getTileMap();

        Map<TileType, List<Vec2i>> specialTiles = map.getSpecialTiles();

        for (TileType t: specialTiles.keySet()) {
            for (Vec2i position: specialTiles.get(t)) {
                toReturn[position.y][position.x] = t;
            }
        }

        return toReturn;
    }
    public static void printMap(Terrain map) {
        System.out.println(stringMap(map));
    }

    public static String stringMap(Terrain terrain) {
        TileType[][] map = convertTerrainFullTileMap(terrain);

        StringBuilder toReturn = new StringBuilder();
        for (TileType[] tileTypes : map) {
            for (TileType tileType : tileTypes) {
                toReturn.append(tileType).append(" ");
            }
            toReturn.append("\n");
        }
        return toReturn.toString();
    }
}
