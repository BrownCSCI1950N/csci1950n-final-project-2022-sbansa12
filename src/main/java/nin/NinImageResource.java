package nin;

import engine.Resource;
import engine.Sprite;
import engine.support.Vec2d;
import javafx.scene.image.Image;

public class NinImageResource {
    public static final Resource<Sprite> images = new Resource<>(){{
        putResource("WALL1", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\WALL.png"), new Vec2d(1,1)));
        putResource("WALL2", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\WALL.png"), new Vec2d(1,1)));
        putResource("ROOM", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\ROOM.png"), new Vec2d(1,1)));
        putResource("SPAWN1", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\SPAWN.png"), new Vec2d(1,1)));
        putResource("EXIT1", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\EXIT.png"), new Vec2d(2,1)));
        putResource("PLAYER1", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\PLAYER.png"), new Vec2d(1,1)));
        putResource("BUTTON", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\BUTTON.png"), new Vec2d(2,1)));
        putResource("BOX1", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\BOX.png"), new Vec2d(1,1)));
        putResource("BOX2", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\HALFBOX.png"), new Vec2d(1,1)));
        putResource("CORNERTOPLEFT", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\CORNERTOPLEFT.png"), new Vec2d(1,1)));
        putResource("CORNERTOPRIGHT", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\CORNERTOPRIGHT.png"), new Vec2d(1,1)));
        putResource("CORNERBOTTOMLEFT", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\CORNERBOTTOMLEFT.png"), new Vec2d(1,1)));
        putResource("CORNERBOTTOMRIGHT", new Sprite(new Image("file:.\\src\\main\\java\\nin\\Images\\CORNERBOTTOMRIGHT.png"), new Vec2d(1,1)));
    }};

    public static Sprite getResource(String resourceName) {
        return images.getResource(resourceName);
    }
}
