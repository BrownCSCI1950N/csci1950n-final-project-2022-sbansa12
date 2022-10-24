package engine.Components;

import engine.AI.DecisionTrees.BehaviorTreeNode;

import java.math.BigDecimal;

public class BTComponent extends AIComponent {
    BehaviorTreeNode bt;
    BigDecimal count;
    BigDecimal timer;
    public BTComponent(BehaviorTreeNode bt, BigDecimal timer) {
        this.bt = bt;
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

        bt.update(nanosSinceLastTick);
    }

    @Override
    public String getTag() {
        return "AI";
    }
}
