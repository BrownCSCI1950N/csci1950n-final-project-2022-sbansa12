package engine.AI.DecisionTrees;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomSelector extends Composite {
    Random rand;

    public RandomSelector(List<BehaviorTreeNode> children, Random rand) {
        super(children);
        this.rand = rand;
    }

    @Override
    public Status update(float seconds) {
        Collections.shuffle(children, rand);
        for (int i = 0; i < children.size(); i++) {
            Status output = children.get(i).update(seconds);
            if (output != Status.FAIL) {
                if (output == Status.RUNNING) {
                    if (i < children.indexOf(lastRunning)) {
                        lastRunning.reset();
                    }
                    lastRunning = children.get(i);
                }
                return output;
            }
        }

        lastRunning = null;
        return Status.FAIL;
    }

    @Override
    public void reset() {

    }
}
