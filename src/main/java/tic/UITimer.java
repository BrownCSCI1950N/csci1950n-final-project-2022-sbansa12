package tic;

import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.support.Vec2d;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UITimer extends UIElement {
    public UITimer(Screen screen, UIElement parent, Vec2d position, Vec2d size, double border) {
        super(screen, parent, position, null);

        UIElement timerContainer = new UIRectangle(screen, this, position, size, Constants.timerContainerColor);
        Vec2d positionTimer = new Vec2d(position.x + border, position.y + border);
        Vec2d sizeTimer = new Vec2d(size.x - (border * 2), size.y - (border * 2));
        UIElement timerMax = new UIRectangle(screen, this, positionTimer, sizeTimer, Constants.timerMaxColor);
        UIElement timer = new UIRectangle(screen, this, positionTimer, sizeTimer.pdiv(0,2), Constants.timerColor){

//            private BigDecimal count = new BigDecimal("0");
//            @Override
//            public void onTick(long nanosSincePreviousTick) {
//                count = count.add(BigDecimal.valueOf(nanosSincePreviousTick));
//
//                currentSize.y = count.divide(TIMER_VALUE).multiply(BigDecimal.valueOf(currentSize.y));;
//
//                super.onTick(nanosSincePreviousTick);
//            }
        };

        this.addChildren(timerContainer);
        this.addChildren(timerMax);
        this.addChildren(timer);
    }
}
