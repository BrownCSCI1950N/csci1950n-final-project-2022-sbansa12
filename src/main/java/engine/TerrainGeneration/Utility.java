package engine.TerrainGeneration;

public class Utility {
    public static String stringMap(TileType[][] map) {

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
