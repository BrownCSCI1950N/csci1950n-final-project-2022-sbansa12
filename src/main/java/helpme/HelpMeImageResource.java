package helpme;

import engine.Resource;
import engine.Sprite;
import engine.support.Vec2d;
import helpme.Constants.ConstantsGameValues;
import javafx.scene.image.Image;

public class HelpMeImageResource {
    public static final Resource<Sprite> images = new Resource<>(){{
        putResource("ROOM", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "ROOM.png"), new Vec2d(1,1)));
        putResource("SPAWN1", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "SPAWN.png"), new Vec2d(1,1)));
        putResource("SPAWN2", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "SPAWN.png"), new Vec2d(1,1)));
        putResource("WALL1", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "WALL.png"), new Vec2d(1,1)));
        putResource("EXIT1", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "EXIT1.png"), new Vec2d(2,1)));
        putResource("EXIT2", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "EXIT2.png"), new Vec2d(2,1)));
        putResource("PLAYER1", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "PLAYER1.png"), new Vec2d(1,1)));
        putResource("PLAYER2", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "PLAYER2.png"), new Vec2d(1,1)));
        putResource("BUTTON", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "BUTTON.png"), new Vec2d(2,1)));
        putResource("BOX1", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "BOX1.png"), new Vec2d(1,1)));
        putResource("TRAP1", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "TRAP1.png"), new Vec2d(1,1)));
        putResource("TRAP2", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "TRAP2.png"), new Vec2d(1,1)));
        putResource("TRAP3", new Sprite(new Image(ConstantsGameValues.resourcesPathway + "TRAP3.png"), new Vec2d(1,1)));

    }};
    public static Sprite getResource(String resourceName) {
        return images.getResource(resourceName);
    }
}
