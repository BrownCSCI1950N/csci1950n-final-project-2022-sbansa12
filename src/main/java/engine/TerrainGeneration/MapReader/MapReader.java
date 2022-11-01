package engine.TerrainGeneration.MapReader;

import engine.TerrainGeneration.LevelParseException;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2i;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MapReader {
    public static TileType[][] createTerrain(String filepath, List<TileType> mustInclude) throws IOException, NumberFormatException, LevelParseException {
        if (!filepath.contains(".txt")) {
            throw new LevelParseException("Invalid File " + filepath + " Given To Map Reader: Requires .txt files");
        }
        BufferedReader myReader = new BufferedReader(new FileReader(filepath));

        String line = myReader.readLine();
        int count = 1;
        int lineLength = line.length();
        List<String> lines = new LinkedList<>();
        lines.add(line);

        // Parse Rooms
        while ((line = myReader.readLine()) != null) {
            count +=1;
            if (line.length() != lineLength) {
                throw new LevelParseException(filepath + ": Line " + count + " Different Length: Expected: " +lineLength + " Actual: "+line.length());
            }
            lines.add(line);
        }

        boolean spawn = false;
        boolean exit = false;
        List<Boolean> mustIncludeBool = new LinkedList<>(Collections.nCopies(mustInclude.size(),false));

        TileType[][] toReturn = new TileType[lines.size()][lineLength];

        for (int j = 0; j < lines.size(); j++) {
            String row = lines.get(j);
            for (int i = 0; i < row.length(); i++) {
                TileType t = TileType.stringToTile(String.valueOf(row.charAt(i)));
                if (t == null) {
                    throw new LevelParseException(filepath + ": Unknown Tile Used at Line" + count + " Unknown Tile: " + row.charAt(i));
                }
                toReturn[j][i] = t;

                if (t == TileType.SPAWN) {
                    spawn = true;
                }

                if (t == TileType.EXIT) {
                    exit = true;
                }
                int index = mustInclude.indexOf(t);
                if (index != -1) {
                    mustIncludeBool.set(index, true);
                }
            }
        }

        if (!spawn || !exit) {
            throw new LevelParseException(filepath + ": Spawn or Exit Not Found: Spawn: " + spawn + " Exit: " + exit);
        }

        int index = mustIncludeBool.indexOf(false);
        if (index != -1) {
            throw new LevelParseException(filepath + ": " + mustInclude.get(index).name() + " Tile Not Found");
        }

        myReader.close();

        return toReturn;
    }

    public static void checkMapSize(TileType[][] map, Vec2i size, String filename) throws LevelParseException {
        if (map.length != size.y) {
            throw new LevelParseException("Y Size of Map " + filename + " Incorrect");
        }
        if (map[0].length != size.x) {
            throw new LevelParseException("X Size of Map " + filename + " Incorrect");
        }
    }
}
