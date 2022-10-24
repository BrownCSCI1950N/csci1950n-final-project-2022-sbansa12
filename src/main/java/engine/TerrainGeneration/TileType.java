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
        switch (this) {
            case PLAYER:
                return "P";
            case ROOM:
                return "□";
            case WALL:
                return "■";
            case SPAWN:
                return "S";
            case EXIT:
                return "E";
            case STAIRS:
                return "=";
            case TRAPS:
                return "T";
            case ENEMY:
                return "X";
            case BOSS:
                return "B";
            case HIDDEN:
                return "H";
            default:
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
