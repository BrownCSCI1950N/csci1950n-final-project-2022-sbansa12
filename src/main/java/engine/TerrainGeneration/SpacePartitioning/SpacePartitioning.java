package engine.TerrainGeneration.SpacePartitioning;

import engine.Axis;
import engine.TerrainGeneration.Room;
import engine.TerrainGeneration.Terrain;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2i;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SpacePartitioning {

    private final Random rand;

    public SpacePartitioning(Random rand) {
        this.rand = rand;
    }

    /**
     * Uses the space partitioning algorithm to generate a 2d array that represents a map
     * @param sizeOfMap - the width and height of the map
     * @param minRoomSize - minimum width and height a room can be
     * @param maxHallWidth - maximum width of the halls, should be between 1 and min height and width of rooms
     * @param probabilitySplitEachRoom - probability that each room splits, between 0 and 1, if -1 goes until can no longer subdivide
     * @return - 2d array of tile types that represents the generated map
     */
    public Terrain createTerrain(Vec2i sizeOfMap, Vec2i minRoomSize, float probabilitySplitEachRoom, Integer maxHallWidth) {
        assert sizeOfMap.x > (2 * minRoomSize.x);
        assert sizeOfMap.y > (2 * minRoomSize.y);
        assert maxHallWidth >= 1;
        assert maxHallWidth <= minRoomSize.x-2;
        assert maxHallWidth <= minRoomSize.y-2;
        assert ((0.0 <= probabilitySplitEachRoom) && (probabilitySplitEachRoom <= 1.0)) || (probabilitySplitEachRoom == -1.0);

        RectangleTree root = new RectangleTree(new Vec2i(0,0), sizeOfMap, minRoomSize, maxHallWidth, Axis.X, rand);

        // Partition Space into Rectangles
        partitionSpace(root, probabilitySplitEachRoom);

        // Create Rooms and Halls In Rectangles
        root.createRoom();

        // Convert Map into 2D Array
        placeSpawnAndExit(root);
        List<Room> allRooms = root.getRoomEnds();
        List<Room> allHalls = root.getAllHalls();
        return new Terrain(sizeOfMap, allRooms, allHalls, rand);
    }

    private void partitionSpace(RectangleTree root, float probabilitySplitEachRoom) {
        List<RectangleTree> toSplit = new LinkedList<>();
        List<RectangleTree> nextSplit = new LinkedList<>();
        toSplit.add(root);
        boolean needSplit = true;
        while (needSplit) {
            for (RectangleTree n: toSplit) {
                if (n == root || randBoolean(probabilitySplitEachRoom)) {
                    if (n.split()) {
                        nextSplit.add(n.getLeft());
                        nextSplit.add(n.getRight());
                    }
                }
            }
            toSplit.clear();
            toSplit.addAll(nextSplit);
            nextSplit.clear();
            needSplit = !toSplit.isEmpty();
        }
    }

    private boolean randBoolean(float p){
        return rand.nextFloat() <= p;
    }

    private void placeSpawnAndExit(RectangleTree root) {
        // Spawn Exit
        List<Room> spawnPossibilities = root.getLeft().getRoomEnds();
        List<Room> exitPossibilities = root.getRight().getRoomEnds();

        Room spawn = spawnPossibilities.get(rand.nextInt(spawnPossibilities.size()));
        Room exit = exitPossibilities.get(rand.nextInt(exitPossibilities.size()));


        int randXs = 1 + rand.nextInt(spawn.getWidth() - 2);
        int randYs = 1 + rand.nextInt(spawn.getHeight() - 2);
        spawn.addSpecialTile(randXs, randYs, TileType.SPAWN1);
        int randXe = 1 + rand.nextInt(exit.getWidth() - 2);
        int randYe = 1 + rand.nextInt(exit.getHeight() - 2);
        exit.addSpecialTile(randXe, randYe, TileType.EXIT1);
    }
}
