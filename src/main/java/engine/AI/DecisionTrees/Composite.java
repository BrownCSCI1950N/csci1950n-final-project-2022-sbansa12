package engine.AI.DecisionTrees;

import java.util.List;

public abstract class Composite implements BehaviorTreeNode {
    List<BehaviorTreeNode> children;
    BehaviorTreeNode lastRunning;

    public Composite(List<BehaviorTreeNode> children) {
        this.children = children;
        this.lastRunning = null;
    }

    @Override
    public abstract Status update(float seconds);

    @Override
    public abstract void reset();
}
