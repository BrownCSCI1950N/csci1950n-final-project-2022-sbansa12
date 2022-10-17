package engine.TerrainGeneration.SpacePartitioning;

import engine.TerrainGeneration.Axis;
import engine.TerrainGeneration.Room;
import engine.support.Vec2i;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static engine.TerrainGeneration.Axis.X;
import static engine.TerrainGeneration.Axis.Y;

class RectangleTree {
    private final Vec2i position;
    private final Vec2i size;
    public Vec2i minRoomSize;
    public Integer maxHallWidth;

    public Axis axisToSplitBy;

    private RectangleTree left;
    private RectangleTree right;

    public Room room;

    private final List<Room> halls;
    private final Random rand;

    public RectangleTree(Vec2i position, Vec2i size, Vec2i minRoomSize, Integer maxHallWidth, Axis axisToSplitBy, Random rand) {
        this.position = position;
        this.size = size;
        this.minRoomSize = minRoomSize;
        this.maxHallWidth = maxHallWidth;
        this.axisToSplitBy = axisToSplitBy;
        this.rand = rand;
        this.left = null;
        this.right = null;
        this.room = null;
        this.halls = new LinkedList<>();
    }

    private Integer indexSplitBy(Axis a) {
        if (a == X) {
            int range = size.x - (2 * (minRoomSize.x + 2));
            if (range <= 0) {
                return -1;
            } else {
                return position.x + (minRoomSize.x + 2) + rand.nextInt(range+1);
            }
        } else if (a == Y){
            int range = size.y - (2 * (minRoomSize.y + 2));
            if (range <= 0) {
                return -1;
            } else {
                return position.y + (minRoomSize.y + 2) + rand.nextInt(range+1);
            }
        } else {
            return -1;
        }
    }

    public boolean split() {
        Integer indexToSplit = indexSplitBy(axisToSplitBy);
        if (indexToSplit == -1) {
            return false;
        }

        if (axisToSplitBy == X) {
            this.left = new RectangleTree(position, new Vec2i(indexToSplit - position.x, size.y), minRoomSize, maxHallWidth, axisToSplitBy.flipAxisXY(), rand);
            this.right = new RectangleTree(new Vec2i(indexToSplit, position.y), new Vec2i(position.x + size.x - indexToSplit, size.y), minRoomSize, maxHallWidth, axisToSplitBy.flipAxisXY(), rand);
        } else if (axisToSplitBy == Y) {
            this.left = new RectangleTree(position, new Vec2i(size.x, indexToSplit - position.y), minRoomSize, maxHallWidth, axisToSplitBy.flipAxisXY(), rand);
            this.right = new RectangleTree(new Vec2i(position.x, indexToSplit), new Vec2i(size.x, position.y + size.y - indexToSplit), minRoomSize, maxHallWidth, axisToSplitBy.flipAxisXY(), rand);
        }

        return true;
    }

    public void createRoom() {
        if (isLeaf()) {
            int randWidth = Math.max((this.size.x - 2 - minRoomSize.x), 1);
            int roomWidth = minRoomSize.x + rand.nextInt(randWidth);
            int randHeight = Math.max((this.size.y - 2 - minRoomSize.y), 1);
            int roomHeight = minRoomSize.y + rand.nextInt(randHeight);

            // Don't put room against side of the leaf as that would merge rooms.
            int randX = Math.max((this.size.x - roomWidth - 2), 1);
            int roomX = 1 + rand.nextInt(randX);
            int randY = Math.max((this.size.y - roomHeight - 2), 1);
            int roomY = 1 + rand.nextInt(randY);

            this.room = new Room(this.position.x + roomX, this.position.y + roomY, roomWidth, roomHeight);
        } else {
            this.left.createRoom();
            this.right.createRoom();
            createHall(this.left.getRoomRecursivelyProbability(), this.right.getRoomRecursivelyProbability());
        }
    }

    private Room getRoomRecursivelyProbability() {
        if (room != null) {
            return room;
        } else {
            Room l = this.left.getRoomRecursivelyProbability();
            Room r = this.right.getRoomRecursivelyProbability();

            if (rand.nextBoolean()) {
                return l;
            } else {
                return r;
            }
        }
    }

    public List<Room> getRoomEnds() {
        if (room != null) {
            return List.of(room);
        } else {
            List<Room> l = this.left.getRoomEnds();
            List<Room> r = this.right.getRoomEnds();

            List<Room> toReturn = new LinkedList<>();
            toReturn.addAll(l);
            toReturn.addAll(r);
            return toReturn;
        }
    }

    private Integer randomHallWidth() {
        return 1 + rand.nextInt(maxHallWidth);
    }

    private void createHall(Room room1, Room room2) {

        // Hall Size
        Integer hallSize = randomHallWidth();

        // Pick Two Arbitrary Points in Each of the Rooms
        int point1X = room1.getPositionX() + 1 + rand.nextInt(room1.getEndPositionX() - room1.getPositionX() - hallSize);
        int point1Y = room1.getPositionY() + 1 + rand.nextInt(room1.getEndPositionY() - room1.getPositionY() - hallSize);
        int point2X = room2.getPositionX() + 1 + rand.nextInt(room2.getEndPositionX() - room2.getPositionX() - hallSize);
        int point2Y = room2.getPositionY() + 1 + rand.nextInt(room2.getEndPositionY() - room2.getPositionY() - hallSize);

        Vec2i pointRoom1 = new Vec2i(point1X, point1Y);
        Vec2i pointRoom2 = new Vec2i(point2X, point2Y);

        Vec2i distancePoints = pointRoom2.minus(pointRoom1);

        if (distancePoints.x < 0) {
            if (distancePoints.y < 0) {
                if (rand.nextBoolean()) {
                    halls.add(new Room(point2X, point1Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point2X, point2Y, hallSize, Math.abs(distancePoints.y)+1));
                } else {
                    halls.add(new Room(point2X, point2Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point1X, point2Y, hallSize, Math.abs(distancePoints.y)+1));
                }
            } else if (distancePoints.y == 0) {
                halls.add(new Room(point2X, point2Y, Math.abs(distancePoints.x), hallSize));
            } else {
                // distancePoints.y > 0
                if (rand.nextBoolean()) {
                    halls.add(new Room(point2X, point1Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point2X, point1Y, hallSize, Math.abs(distancePoints.y)+1));
                } else {
                    halls.add(new Room(point2X, point2Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point1X, point1Y, hallSize, Math.abs(distancePoints.y)+1));
                }
            }
        } else if (distancePoints.x == 0) {
            if (distancePoints.y < 0) {
                halls.add(new Room(point2X, point2Y, hallSize, Math.abs(distancePoints.y)));
            } else if (distancePoints.y == 0) {
                // Not Possible As Would Mean Both Same Point, But We Are Working With Different Rooms
                assert false;
            } else {
                // distancePoints.y > 0
                halls.add(new Room(point1X, point1Y, hallSize, Math.abs(distancePoints.y)));
            }
        } else {
            // distancePoints.x > 0
            if (distancePoints.y < 0) {
                if (rand.nextBoolean()) {
                    halls.add(new Room(point1X, point2Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point1X, point2Y, hallSize, Math.abs(distancePoints.y)+1));
                } else {
                    halls.add(new Room(point1X, point1Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point2X, point2Y, hallSize, Math.abs(distancePoints.y)+1));
                }
            } else if (distancePoints.y == 0) {
                halls.add(new Room(point1X, point1Y, Math.abs(distancePoints.x), hallSize));
            } else {
                // distancePoints.y > 0
                if (rand.nextBoolean()) {
                    halls.add(new Room(point1X, point1Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point2X, point1Y, hallSize, Math.abs(distancePoints.y)+1));
                } else {
                    halls.add(new Room(point1X, point2Y, Math.abs(distancePoints.x)+1, hallSize));
                    halls.add(new Room(point1X, point1Y, hallSize, Math.abs(distancePoints.y)+1));
                }
            }
        }
    }

    public Integer getWidth() {
        return size.x;
    }
    public Integer getHeight() {
        return size.y;
    }

    public RectangleTree getLeft() {
        return left;
    }

    public RectangleTree getRight() {
        return right;
    }

    public Room getRoom() {
        return room;
    }

    public List<Room> getHalls() {
        return halls;
    }

    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }

    public List<Room> getAllHalls() {
        if (isLeaf()) {
            return halls;
        }

        List<Room> l = this.left.getAllHalls();
        List<Room> r = this.right.getAllHalls();

        List<Room> toReturn = new LinkedList<>();
        toReturn.addAll(halls);
        toReturn.addAll(l);
        toReturn.addAll(r);
        return toReturn;
    }

    @Override
    public String toString() {
        return "Rectangle: (" + position.x + " + " + size.x + ", " + position.y + " + " + size.y + ")";
    }
}