package com.zaria.framework.es.components.interaction;

import com.badlogic.gdx.artemis.Component;


public class DamageComponent implements Component {
    private float damage;
    private boolean dieOnDamage;

    public DamageComponent() {
    }

    @Override
    public void reset() {
        setDamage(0);
        setDieOnDamage(true);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public boolean isDieOnDamage() {
        return dieOnDamage;
    }

    public void setDieOnDamage(boolean dieOnDamage) {
        this.dieOnDamage = dieOnDamage;
    }

    @Override
    public String toString() {
        return "Damage[damage=" + damage + ",dieOnDamage=" + dieOnDamage + "]";
    }
}
