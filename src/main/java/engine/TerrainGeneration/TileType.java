package engine.TerrainGeneration;

public enum TileType {
    PLAYER,
    ROOM,
    WALL0,
    WALL1,
    SPAWN,
    EXIT,
    STAIRS,
    TRAPS,
    ENEMY,
    BOSS,
    HIDDEN,
    BUTTON,
    BOX0,
    BOX1,
    CORNERTOPLEFT,
    CORNERTOPRIGHT,
    CORNERBOTTOMLEFT,
    CORNERBOTTOMRIGHT;

    @Override
    public String toString() {
        switch (this) {
            case PLAYER:
                return "P";
            case ROOM:
                return "□";
            case WALL0:
                return "■";
            case WALL1:
                return "W";
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
            case BUTTON:
                return "b";
            case BOX0:
                return "▦";
            case BOX1:
                return "▤";
            case CORNERTOPLEFT:
                return "◤";
            case CORNERTOPRIGHT:
                return "◥";
            case CORNERBOTTOMLEFT:
                return "◣";
            case CORNERBOTTOMRIGHT:
                return "◢";
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
                return WALL0;
            case "W":
                return WALL1;
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
            case "b":
                return BUTTON;
            case "▦":
                return BOX0;
            case "▤":
                return BOX1;
            case "◤":
                return CORNERTOPLEFT;
            case "◥":
                return CORNERTOPRIGHT;
            case "◣":
                return CORNERBOTTOMLEFT;
            case "◢":
                return CORNERBOTTOMRIGHT;
            default:
                return null;
        }
    }
}
