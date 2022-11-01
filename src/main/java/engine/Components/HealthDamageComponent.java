package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;
import javafx.scene.canvas.GraphicsContext;

public class HealthDamageComponent extends CollisionComponent {

    protected GameObject gameObject;
    Integer maxHealth;
    Integer currentHealth;
    Integer damage;

    public HealthDamageComponent(GameObject gameObject, Shape s, Integer layer, Integer maxHealth, Integer damage) {
        super(gameObject, s, layer, true, false);
        this.gameObject = gameObject;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        assert maxHealth != null || damage != 0;
        this.damage = damage;
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

    public void damage(Integer damageTaken) {
        if (maxHealth == null) {
            return;
        }
        currentHealth = Math.max(currentHealth - damageTaken, 0);
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
    public void onCollide(Collision collision) {
        // Get Component of Other Object and Damage It
        if (collision.getCollidedObject().hasComponentTag("healthDamage")) {
            HealthDamageComponent healthDamageComponent = (HealthDamageComponent) collision.getCollidedObject().getComponent("healthDamage");
            healthDamageComponent.damage(damage);
        }
    }

    public static final String tag = "healthDamage";

    @Override
    public String getTag() {
        return tag;
    }
}
