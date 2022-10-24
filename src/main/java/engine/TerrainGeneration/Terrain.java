package engine.TerrainGeneration;

import engine.support.Vec2i;

import java.util.*;

public class Terrain {

    Random rand;
    Vec2i sizeOfMap;
    List<Room> rooms;
    List<Room> halls;

    /**
     * Assumes that there a spawn and exit have already been chosen
     * @param sizeOfMap - size of the entire map
     * @param rooms - list of rooms to be in the map
     * @param halls - list of halls to be in the map
     * @param rand - the random generator to be used for additional functions
     */
    public Terrain(Vec2i sizeOfMap, List<Room> rooms, List<Room> halls, Random rand) {
        this.sizeOfMap = sizeOfMap;
        this.rooms = rooms;
        this.halls = halls;
        this.rand = rand;
    }

    private TileType[][] convertTileMap() {
        TileType[][] map = new TileType[sizeOfMap.y][sizeOfMap.x];

        for (TileType[] tileTypes : map) {
            Arrays.fill(tileTypes, TileType.WALL);
        }

        // Draw Halls into Array
        drawRooms(map, halls);

        // Draw Rooms into Array
        drawRooms(map, rooms);

        return map;
    }

    public Vec2i getSizeOfMap() {
        return sizeOfMap;
    }

    private void drawRooms(TileType[][] map, List<Room> rooms) {
        for (Room r: rooms) {
            for (int i = 0; i < r.getWidth(); i++) {
                for (int j = 0; j < r.getHeight(); j++) {
                    map[r.getPositionY() + j][r.getPositionX() + i] = TileType.ROOM;
                }
            }
        }
    }

    /**
     *
     * @return - a 2d array of tiles that are either ROOM or WALL, Layout of the map.
     */
    public TileType[][] getTileMap() {
        return convertTileMap();
    }

    public Map<TileType, List<Vec2i>> getSpecialTiles() {
        Map<TileType, List<Vec2i>> toReturn = new HashMap<>();
        specialTileListRooms(toReturn, rooms);
        specialTileListRooms(toReturn, halls);
        return toReturn;
    }

    private void specialTileListRooms(Map<TileType, List<Vec2i>> toReturn, List<Room> halls) {
        for (Room r: halls) {
            Map<TileType, List<Vec2i>> roomSpecialTiles = r.getSpecialTiles();
            for (TileType t: roomSpecialTiles.keySet()) {
                List<Vec2i> positions = toReturn.computeIfAbsent(t, k -> new LinkedList<>());
                positions.addAll(roomSpecialTiles.get(t));
            }
        }
    }

    private boolean randBoolean(float p){
        return rand.nextFloat() <= p;
    }

    /**
     *
     * @param t - the tile to place
     * @param allowedOverwrite - room types can be overwritten
     * @param probabilityRoom - probability that a room will have this tile
     * @param probabilityPerTile - probability that a tile in a room will be this tile
     * @param uptoPerRoom - max amount of tile type per room, if less than 0 taken to mean infinite. if 0 will cause assertion failure.
     */
    public void placeTileRandomlyRooms(TileType t, List<TileType> allowedOverwrite, float probabilityRoom, float probabilityPerTile, Integer uptoPerRoom) {
        assert uptoPerRoom != 0;
        for (Room r: rooms) {
            if (allowedOverwrite.contains(r.getRoomType()) && randBoolean(probabilityRoom)) {
                int count = 0;
                for (int i = 0; i < r.getWidth(); i++) {
                    if (uptoPerRoom > 0){
                        if (count >= uptoPerRoom) {
                            break;
                        }
                    }

                    for (int j = 0; j < r.getHeight(); j++) {
                        if (uptoPerRoom > 0) {
                            if (count >= uptoPerRoom) {
                                break;
                            }
                        }
                        if (randBoolean(probabilityPerTile)) {
                            r.addSpecialTile(i, j, t);
                            count += 1;
                        }
                    }
                }
            }
        }
    }

    public void placeTileRandomlyRoomsCenter(TileType t, List<TileType> allowedOverwrite, float probabilityRoom, float probabilityPerTile, Integer uptoPerRoom) {
        assert uptoPerRoom != 0;
        for (Room r: rooms) {
            if (allowedOverwrite.contains(r.getRoomType()) && randBoolean(probabilityRoom)) {
                int count = 0;
                for (int i = 1; i < r.getWidth()-1; i++) {
                    if (uptoPerRoom > 0){
                        if (count >= uptoPerRoom) {
                            break;
                        }
                    }

                    for (int j = 1; j < r.getHeight()-1; j++) {
                        if (uptoPerRoom > 0) {
                            if (count >= uptoPerRoom) {
                                break;
                            }
                        }
                        if (randBoolean(probabilityPerTile)) {
                            r.addSpecialTile(i, j, t);
                            count += 1;
                        }
                    }
                }
            }
        }
    }

    public void placeSingleTileRandom(TileType t, List<TileType> allowedRoomTypes) {
        while (true) {
            int index = rand.nextInt(rooms.size());
            Room r = rooms.get(index);
            if (allowedRoomTypes.contains(r.getRoomType())) {
                int randX = rand.nextInt(r.getWidth());
                int randY = rand.nextInt(r.getHeight());
                r.addSpecialTile(randX, randY, t);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Terrain: Rooms: " + rooms + " Halls: " + halls + "\n" + stringMap();
    }

    public TileType[][] convertTerrainFullTileMap() {
        TileType[][] toReturn = getTileMap();

        Map<TileType, List<Vec2i>> specialTiles = getSpecialTiles();

        for (TileType t: specialTiles.keySet()) {
            for (Vec2i position: specialTiles.get(t)) {
                toReturn[position.y][position.x] = t;
            }
        }

        return toReturn;
    }

    public String stringMap() {
        TileType[][] map = convertTerrainFullTileMap();

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
