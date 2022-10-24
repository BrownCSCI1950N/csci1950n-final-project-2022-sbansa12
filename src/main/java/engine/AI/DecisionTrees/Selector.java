package engine.AI.DecisionTrees;

import java.util.List;

public class Selector extends Composite {

    public Selector(List<BehaviorTreeNode> children) {
        super(children);
    }

    @Override
    public Status update(float seconds) {

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
