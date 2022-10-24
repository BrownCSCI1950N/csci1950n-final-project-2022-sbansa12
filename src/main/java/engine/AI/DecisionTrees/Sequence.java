package engine.AI.DecisionTrees;

import java.util.List;

public class Sequence extends Composite {
    String name;
    public Sequence(String name, List<BehaviorTreeNode> children) {
        super(children);
        this.name = name;
    }

    @Override
    public Status update(float seconds) {

        for (int i = Math.max(0, children.indexOf(lastRunning)); i < children.size(); i++) {
            Status output = children.get(i).update(seconds);
            if (output != Status.SUCCESS) {
                if (output == Status.RUNNING) {
                    lastRunning = children.get(i);
                }
                return output;
            }
        }

        lastRunning = null;
        return Status.SUCCESS;
    }

    @Override
    public void reset() {
        lastRunning.reset();
        lastRunning = null;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder("\n" + this.name + ": Sequence: ");
        for (BehaviorTreeNode bt: children) {
            toReturn.append("\n\t").append(bt.toString());
        }
        return toReturn.append("\n").toString();
    }
}
