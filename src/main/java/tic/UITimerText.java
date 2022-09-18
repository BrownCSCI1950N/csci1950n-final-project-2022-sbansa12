package tic;

import engine.FontWrapper;
import engine.Screen;
import engine.UI.UIElement;
import engine.UI.UIText;
import engine.support.Vec2d;
import javafx.scene.paint.Color;
import tic.screens.GameScreen;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UITimerText extends UIElement {
    public UITimerText(Screen screen, UIElement parent, Vec2d position, Color color, FontWrapper font) {
        super(screen, parent, position, null);

        UIElement timer = new UIText(screen, parent, position, "", color, font){
            @Override
            public void onTick(long nanosSincePreviousTick) {
                this.text = GameScreen.count.divide(BigDecimal.valueOf(1000000000), Constants.timerTextDecimalPlaces, RoundingMode.FLOOR) + "/" + (Constants.TIMER_VALUE.divide(BigDecimal.valueOf(1000000000), Constants.timerTextDecimalPlaces, RoundingMode.FLOOR));

                super.onTick(nanosSincePreviousTick);
            }
        };

        this.addChildren(timer);
    }
}
