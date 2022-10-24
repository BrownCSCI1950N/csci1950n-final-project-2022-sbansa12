package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class DiscoveredComponent implements Component {

    boolean isDiscovered;
    GameObject gO;
    GameObject discoverer;
    double distanceDiscover;

    public DiscoveredComponent(GameObject gO, GameObject discoverer, double distanceDiscover) {
        this.gO = gO;
        this.discoverer = discoverer;
        this.distanceDiscover = distanceDiscover;
        this.isDiscovered = false;
    }

    @Override
    public void tick(long nanosSinceLastTick) {
        if (!isDiscovered) {
            Vec2d centerObject = gO.getTransform().getCurrentGameSpacePosition().plus(gO.getTransform().getSize().sdiv(2));
            Vec2d centerDiscoverer = discoverer.getTransform().getCurrentGameSpacePosition().plus(discoverer.getTransform().getSize().sdiv(2));
            double currentDistance = centerObject.dist2(centerDiscoverer);
            if (currentDistance < distanceDiscover) {
                this.isDiscovered = true;
            }
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "discovered";
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }
}
