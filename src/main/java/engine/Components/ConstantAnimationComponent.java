package engine.Components;

import engine.GameObject;
import engine.Sprite;
import engine.support.Vec2d;

import java.math.BigDecimal;
import java.util.List;

public class ConstantAnimationComponent extends SpriteComponent {

    private Integer index;
    List<Vec2d> spriteAnimationPositions;
    private BigDecimal count;
    private final BigDecimal animationChangeTimeNanos;

    public ConstantAnimationComponent(GameObject gameObject, Sprite sprite, List<Vec2d> spriteAnimationPositions, BigDecimal animationChangeTimeNanos) {
        super(gameObject, sprite, null);
        assert spriteAnimationPositions.size() > 0;
        this.index = 0;
        this.currentSpritePosition = spriteAnimationPositions.get(index);
        this.spriteAnimationPositions = spriteAnimationPositions;
        this.count = new BigDecimal(String.valueOf(0));
        this.animationChangeTimeNanos = animationChangeTimeNanos;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        this.count = this.count.add(new BigDecimal(nanosSinceLastTick));
        if (count.compareTo(animationChangeTimeNanos) < 0) {
            return;
        }
        this.count = new BigDecimal(String.valueOf(0));
        index = (index + 1) % spriteAnimationPositions.size();
        this.currentSpritePosition = spriteAnimationPositions.get(index);
    }
}
