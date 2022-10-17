package engine.TerrainGeneration;

import engine.support.Vec2i;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Room {
    private final Integer positionX;
    private final Integer positionY;
    private final Integer width;
    private final Integer height;
    private final Map<TileType, List<Vec2i>> specialTiles;
    private TileType roomType;

    public Room(Integer positionX, Integer positionY, Integer width, Integer height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.specialTiles = new HashMap<>();
        this.roomType = TileType.ROOM;
    }

    public void addSpecialTile(Integer x, Integer y, TileType t) {
        this.roomType = t;
        List<Vec2i> positions = specialTiles.computeIfAbsent(t, k -> new LinkedList<>());
        positions.add(new Vec2i(positionX + x,positionY + y));
    }

    public Map<TileType, List<Vec2i>> getSpecialTiles() {
        return specialTiles;
    }

    public TileType getRoomType() {
        return roomType;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
    public Integer getEndPositionX() {
        return positionX + width - 1;
    }
    public Integer getEndPositionY() {
        return positionY + height - 1;
    }

    @Override
    public String toString() {
        return "Room: (" + positionX + " + " + width + ", " + positionY + " + " + height + ")";
    }
}
