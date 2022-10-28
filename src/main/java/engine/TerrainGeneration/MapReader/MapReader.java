package engine.TerrainGeneration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MapReader {
    public TileType[][] createTerrain(String filepath) throws IOException, NumberFormatException, LevelParseException {
        if (!filepath.contains(".txt")) {
            throw new LevelParseException("Invalid File Given To Map Reader: Requires .txt files");
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
                throw new LevelParseException(" Line " + count + " Different Length");
            }
            lines.add(line);
        }

        boolean spawn = false;
        boolean exit = false;

        TileType[][] toReturn = new TileType[lines.size()][lineLength];

        for (int j = 0; j < lines.size(); j++) {
            String row = lines.get(j);
            for (int i = 0; i < row.length(); i++) {
                TileType t = TileType.stringToTile(String.valueOf(row.charAt(i)));
                toReturn[j][i] = t;

                if (t == TileType.SPAWN) {
                    spawn = true;
                }

                if (t == TileType.EXIT) {
                    exit = true;
                }
            }
        }

        if (!spawn || !exit) {
            throw new LevelParseException("Spawn or Exit Not Found: Spawn: " + spawn + "Exit: " + exit);
        }

        myReader.close();

        return toReturn;
    }
}
