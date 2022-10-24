package engine.AI.DecisionTrees;

/**
 * Repeats Updating the Child x Times Regardless of Status
 */
public class RepeatWrapper extends Wrapper {
    private final Integer repeatTimes;
    private Integer count;
    public RepeatWrapper(Integer repeatTimes, BehaviorTreeNode child) {
        this.repeatTimes = repeatTimes;
        this.child = child;
        this.count = 0;
    }

    @Override
    public Status update(float seconds) {
        if (count < repeatTimes) {
            child.update(seconds);
            count += 1;
            return Status.RUNNING;
        }

        this.count = 0;
        return Status.SUCCESS;
    }

    @Override
    public void reset() {
        this.count = 0;
    }
}
