package engine.Components;

import javafx.scene.canvas.GraphicsContext;

import java.math.BigDecimal;

public class TimerActionComponent implements Component {
    BigDecimal count;
    BigDecimal timer;

    public TimerActionComponent(BigDecimal timer) {
        this.count = new BigDecimal("0");
        this.timer = timer;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        count = count.add(new BigDecimal(nanosSinceLastTick));
        if (count.compareTo(timer) < 0) {
            return;
        }
        count = new BigDecimal("0");
        script();
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "timerAction";
    }

    public void script() {}
}
