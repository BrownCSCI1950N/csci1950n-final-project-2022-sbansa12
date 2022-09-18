package tic;

import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.support.Vec2d;
import tic.screens.GameScreen;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UITimerBar extends UIElement {
    public UITimerBar(Screen screen, UIElement parent, Vec2d position, Vec2d size, double border) {
        super(screen, parent, position, null);

        UIElement timerContainer = new UIRectangle(screen, this, position, size, Constants.timerContainerColor);
        UIElement timerMax = new UIRectangle(screen, this, new Vec2d(position.x + border, position.y + border), new Vec2d(size.x - (border * 2), size.y - (border * 2)), Constants.timerMaxColor);
        UIElement timer = new UIRectangle(screen, this, new Vec2d(position.x + border, position.y + size.y - border), new Vec2d(size.x - (border * 2), 0), Constants.timerColor) {

            private final double originalTimerSize = size.y - 2 * border;
            private double currentTimerSize = size.y - 2 * border;
            @Override
            public void onTick(long nanosSincePreviousTick) {
                double timeScale = GameScreen.count.divide(BigDecimal.valueOf(1000000000), Constants.timerBarSmoothness, RoundingMode.FLOOR).divide(Constants.TIMER_VALUE.divide(BigDecimal.valueOf(1000000000), Constants.timerBarSmoothness, RoundingMode.FLOOR), Constants.timerBarSmoothness, RoundingMode.FLOOR).doubleValue();
                double rescaleFactor = currentPosition.x/originalPosition.x;
                this.currentPosition = new Vec2d(currentPosition.x, (originalPosition.y * rescaleFactor) - (currentTimerSize * timeScale));
                this.currentSize = new Vec2d(currentSize.x, currentTimerSize * timeScale);

                super.onTick(nanosSincePreviousTick);
            }

            @Override
            public void onResize(Vec2d newSize) {
                currentTimerSize = originalTimerSize * newSize.y;

                super.onResize(newSize);
            }
        };

        this.addChildren(timerContainer);
        this.addChildren(timerMax);
        this.addChildren(timer);
    }
}