package engine.Components;

import engine.GameObject;
import engine.Shape.Shape;

public class DamageComponent extends CollisionComponent{
    Integer damage;
    public DamageComponent(GameObject gameObject, Shape s, Integer layer, Integer damage) {
        super(gameObject, s, layer);
        this.damage = damage;
    }

    public DamageComponent(GameObject gameObject, Shape s, Integer layer, Integer damage, boolean isStatic) {
        super(gameObject, s, layer, isStatic);
        this.damage = damage;
    }

    @Override
    public void onCollide(Collision collision) {
        if (collision.getCollidedObject().hasComponentTag("health")) {
            HealthComponent healthComponent = (HealthComponent) collision.getCollidedObject().getComponent("health");
            healthComponent.damage(damage);
        }
    }

    @Override
    public String getTag() {
        return "damage";
    }
}
