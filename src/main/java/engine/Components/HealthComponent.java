package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;
import javafx.scene.canvas.GraphicsContext;

public class HealthComponent extends CollisionComponent {

    protected GameObject gameObject;
    Integer maxHealth;
    Integer currentHealth;

    public HealthComponent(GameObject gameObject, Shape s, Integer layer, Integer maxHealth) {
        super(gameObject, s, layer);
        this.gameObject = gameObject;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public Integer getCurrentHealth() {
        return currentHealth;
    }
    public Integer getMaxHealth() {
        return maxHealth;
    }

    public void heal(Integer health) {
        currentHealth = Math.min(currentHealth + health, maxHealth);
    }

    public void damage(Integer damage) {
        currentHealth = Math.max(currentHealth - damage, 0);
        if (currentHealth == 0) {
            zeroHealthScript();
        }
    }

    public void zeroHealthScript() {}

    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "health";
    }
}
