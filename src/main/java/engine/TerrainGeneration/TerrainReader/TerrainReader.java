package engine.TerrainGeneration.TerrainReader;

import engine.TerrainGeneration.Room;
import engine.TerrainGeneration.Terrain;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2i;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TerrainReader {
    public Terrain createTerrain(String filepath, Random rand) throws IOException, NumberFormatException, LevelParseException {
        if (!filepath.contains(".txt")) {
            throw new LevelParseException("Invalid File Given To Terrain Reader: Requires .txt files");
        }
        BufferedReader myReader = new BufferedReader(new FileReader(filepath));

        // Parse Map Size
        String header = myReader.readLine();
        String[] size = header.split(",");
        int width;
        int height;
        try {
            width = Integer.parseInt(size[0].strip().substring(1).strip());
            height = Integer.parseInt(size[1].strip().substring(0, size[1].strip().length()-1).strip());
        } catch (NumberFormatException e) {
            myReader.close();
            throw new LevelParseException("Map Size Incorrectly Formatted");
        }

        String barrier1 = myReader.readLine();
        if (!barrier1.strip().equals("----")) {
            myReader.close();
            throw new LevelParseException("Barrier1 Incorrectly Formatted");
        }

        boolean spawn = false;
        boolean exit = false;
        boolean barrier2 = false;

        String line;
        int count = 2;
        List<Room> rooms = new LinkedList<>();

        // Parse Rooms
        while ((line = myReader.readLine()) != null) {
            count +=1;
            if (line.strip().equals("----")) {
                barrier2 = true;
                break;
            }

            Room newRoom = parseRoom(line, "Room", myReader, count, new Vec2i(width, height));

            if (newRoom.getRoomType() == TileType.SPAWN) {
                spawn = true;
            }

            if (newRoom.getRoomType() == TileType.EXIT) {
                exit = true;
            }

            rooms.add(newRoom);
        }

        if (!barrier2) {
            myReader.close();
            throw new LevelParseException("Barrier2 Incorrectly Formatted or Does Not Exist");
        }

        List<Room> halls = new LinkedList<>();

        // Parse Rooms
        while ((line = myReader.readLine()) != null) {
            count += 1;

            Room newHall = parseRoom(line, "Hall", myReader, count, new Vec2i(width, height));

            if (newHall.getRoomType() == TileType.SPAWN) {
                spawn = true;
            }

            if (newHall.getRoomType() == TileType.EXIT) {
                exit = true;
            }

            halls.add(newHall);
        }

        if (!spawn || !exit) {
            throw new LevelParseException("Spawn or Exit Not Found: Spawn: " + spawn + "Exit: " + exit);
        }

        myReader.close();

        return new Terrain(new Vec2i(width, height), rooms, halls, rand);
    }

    private Room parseRoom(String line, String roomOrHall, BufferedReader myReader, Integer count, Vec2i mapSize) throws IOException, LevelParseException {
        String[] roomDefinition = line.split("\\|");

        if (roomDefinition.length != 2 && roomDefinition.length != 1) {
            myReader.close();
            throw new LevelParseException(roomOrHall + "Definition on Line " + count + " Formatted Incorrectly");
        }

        // Parse Room Position and Size
        String[] roomPositionSize = roomDefinition[0].strip().split(",");

        if (roomPositionSize.length != 2) {
            myReader.close();
            throw new LevelParseException(roomOrHall+" Definition on Line " + count + " Formatted Incorrectly");
        }

        String[] roomXValues = roomPositionSize[0].substring(1).strip().split("\\+");
        String[] roomYValues = roomPositionSize[1].substring(1).strip().split("\\+");

        if (roomXValues.length != 2 || roomYValues.length != 2) {
            myReader.close();
            throw new LevelParseException(roomOrHall+" Definition on Line " + count + " Formatted Incorrectly");
        }

        int roomXPosition;
        int roomXSize;
        int roomYPosition;
        int roomYSize;

        try {
            roomXPosition = Integer.parseInt(roomXValues[0].strip());
            roomXSize = Integer.parseInt(roomXValues[1].strip());
            roomYPosition = Integer.parseInt(roomYValues[0].strip());
            roomYSize = Integer.parseInt(roomYValues[1].strip().substring(0, roomYValues[1].strip().length()-1).strip());
        }  catch (NumberFormatException e) {
            myReader.close();
            throw new LevelParseException(roomOrHall+" Definition on Line " + count + " Formatted Incorrectly");
        }

        if (roomXPosition < 0 || roomXPosition >= mapSize.x) {
            myReader.close();
            throw new LevelParseException(roomOrHall + " X Position on Line " + count + " Out Of Bounds");
        }
        if (roomYPosition < 0 || roomYPosition >= mapSize.y) {
            myReader.close();
            throw new LevelParseException(roomOrHall + " Y Position on Line " + count + " Out Of Bounds");
        }
        if (roomXSize < 0 || roomXPosition + roomXSize >= mapSize.x) {
            myReader.close();
            throw new LevelParseException(roomOrHall + " X Size on Line " + count + " Out Of Bounds");
        }
        if (roomYSize < 0 || roomYPosition + roomYSize >= mapSize.y) {
            myReader.close();
            throw new LevelParseException(roomOrHall + " Y Size on Line " + count + " Out Of Bounds");
        }

        Room newRoom = new Room(roomXPosition, roomYPosition, roomXSize, roomYSize);

        // Parse Special Tiles
        if (roomDefinition.length == 2) {
            String[] specialTiles = roomDefinition[1].split(":");

            for (String specialTile : specialTiles) {
                String[] specialT = specialTile.strip().split(",");
                if (specialT.length != 3) {
                    myReader.close();
                    throw new LevelParseException(roomOrHall+" Special Tiles Definition on Line " + count + " Formatted Incorrectly");
                }

                TileType tileType = TileType.stringToTile(specialT[0].substring(1));
                if (tileType == null) {
                    myReader.close();
                    throw new LevelParseException(roomOrHall + " Special Tiles Definition on Line " + count + " Formatted Incorrectly");
                }

                int specialTileX;
                int specialTileY;
                try {
                    specialTileX = Integer.parseInt(specialT[1].strip());
                    specialTileY = Integer.parseInt(specialT[2].strip().substring(0, specialT[2].strip().length() - 1).strip());
                } catch (NumberFormatException e) {
                    myReader.close();
                    throw new LevelParseException(roomOrHall + " Special Tiles Definition on Line " + count + " Formatted Incorrectly");
                }

                if (specialTileX < 0 || specialTileX >= roomXSize) {
                    myReader.close();
                    throw new LevelParseException(roomOrHall + " Special Tiles X Position on Line " + count + " Formatted Incorrectly");
                }
                if (specialTileY < 0 || specialTileY >= roomYSize) {
                    myReader.close();
                    throw new LevelParseException(roomOrHall + " Special Tiles Y Position on Line " + count + " Formatted Incorrectly");
                }

                newRoom.addSpecialTile(specialTileX, specialTileY, tileType);
            }
        }

        return newRoom;
    }
}
