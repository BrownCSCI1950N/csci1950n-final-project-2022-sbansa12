package engine.TerrainGeneration;

public enum TileType {
    PLAYER,
    ROOM,
    WALL,
    SPAWN,
    EXIT,
    STAIRS,
    TRAPS,
    ENEMY,
    BOSS,
    HIDDEN;

    @Override
    public String toString() {
        if (this.equals(PLAYER)) {
            return "P";
        } else if (this.equals(ROOM)) {
            return "□";
        } else if (this.equals(WALL)) {
            return "■";
        } else if (this.equals(SPAWN)) {
            return "S";
        } else if (this.equals(EXIT)) {
            return "E";
        } else if (this.equals(STAIRS)) {
            return "=";
        } else if (this.equals(TRAPS)) {
            return "T";
        } else if (this.equals(ENEMY)) {
            return "X";
        } else if (this.equals(BOSS)) {
            return "B";
        } else if (this.equals(HIDDEN)) {
            return "H";
        } else {
            return "";
        }
    }

    public static TileType stringToTile(String s) {
        switch (s) {
            case "P":
                return PLAYER;
            case "□":
                return ROOM;
            case "■":
                return WALL;
            case "S":
                return SPAWN;
            case "E":
                return EXIT;
            case "=":
                return STAIRS;
            case "T":
                return TRAPS;
            case "X":
                return ENEMY;
            case "B":
                return BOSS;
            case "H":
                return HIDDEN;
            default:
                return null;
        }
    }
}
