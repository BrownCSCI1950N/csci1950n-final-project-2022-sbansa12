package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    Image image;
    Vec2d spriteSize;

    public Sprite(Image i, Vec2d numberSprites) {
        this.image = i;
        this.spriteSize = new Vec2d(i.getWidth(), i.getHeight()).pdiv(numberSprites);
    }

    public void draw(GraphicsContext g, Vec2d positionDraw, Vec2d size, Vec2d spritePosition) {

        g.drawImage(image,
                spritePosition.x * spriteSize.x, spritePosition.y * spriteSize.y,
                spriteSize.x, spriteSize.y,
                positionDraw.x, positionDraw.y,
                size.x, size.y);
    }
}

