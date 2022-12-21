package engine.TerrainGeneration;

public enum TileType {
    PLAYER1,
    PLAYER2,
    ROOM,
    WALL1,
    WALL2,
    SPAWN1,
    SPAWN2,
    EXIT1,
    EXIT2,
    STAIRS,
    TRAP1,
    TRAP2,
    ENEMY,
    BOSS,
    HIDDEN,
    BUTTON,
    BOX1,
    BOX2,
    CORNERTOPLEFT,
    CORNERTOPRIGHT,
    CORNERBOTTOMLEFT,
    CORNERBOTTOMRIGHT,
    BREAKABLE;

    @Override
    public String toString() {
        switch (this) {
            case PLAYER1:
                return "P";
            case PLAYER2:
                return "p";
            case ROOM:
                return "□";
            case WALL1:
                return "■";
            case WALL2:
                return "W";
            case SPAWN1:
                return "S";
            case SPAWN2:
                return "s";
            case EXIT1:
                return "E";
            case EXIT2:
                return "e";
            case STAIRS:
                return "=";
            case TRAP1:
                return "T";
            case TRAP2:
                return "t";
            case ENEMY:
                return "X";
            case BOSS:
                return "B";
            case HIDDEN:
                return "H";
            case BUTTON:
                return "b";
            case BOX1:
                return "▦";
            case BOX2:
                return "▤";
            case CORNERTOPLEFT:
                return "◤";
            case CORNERTOPRIGHT:
                return "◥";
            case CORNERBOTTOMLEFT:
                return "◣";
            case CORNERBOTTOMRIGHT:
                return "◢";
            case BREAKABLE:
                return "☒";
            default:
                return "";
        }
    }

    public static TileType stringToTile(String s) {
        switch (s) {
            case "P":
                return PLAYER1;
            case "p":
                return PLAYER2;
            case "□":
                return ROOM;
            case "■":
                return WALL1;
            case "W":
                return WALL2;
            case "S":
                return SPAWN1;
            case "s":
                return SPAWN2;
            case "E":
                return EXIT1;
            case "e":
                return EXIT2;
            case "=":
                return STAIRS;
            case "T":
                return TRAP1;
            case "t":
                return TRAP2;
            case "X":
                return ENEMY;
            case "B":
                return BOSS;
            case "H":
                return HIDDEN;
            case "b":
                return BUTTON;
            case "▦":
                return BOX1;
            case "▤":
                return BOX2;
            case "◤":
                return CORNERTOPLEFT;
            case "◥":
                return CORNERTOPRIGHT;
            case "◣":
                return CORNERBOTTOMLEFT;
            case "◢":
                return CORNERBOTTOMRIGHT;
            case "☒":
                return BREAKABLE;
            default:
                return null;
        }
    }
}
