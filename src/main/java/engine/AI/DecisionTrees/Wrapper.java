package engine.AI.DecisionTrees;

public abstract class Wrapper implements BehaviorTreeNode {

    BehaviorTreeNode child;

    @Override
    public abstract Status update(float seconds);

    @Override
    public abstract void reset();
}
