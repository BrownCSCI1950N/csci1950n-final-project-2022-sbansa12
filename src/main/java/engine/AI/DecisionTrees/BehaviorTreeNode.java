package engine.AI.DecisionTrees;

public interface BehaviorTreeNode {
    Status update(float seconds);
    void reset();
}
