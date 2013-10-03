package com.roundtriangles.games.zaria.es.components.interaction;

import com.artemis.Component;


public class HealthComponent implements Component {
    private float health;
    private float maxHealth;

    public HealthComponent() {
    }

    @Override
    public void reset() {
        this.setHealth(0);
        this.setMaxHealth(0);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public String toString() {
        return "Health[health=" + health + ",maxHealth=" + maxHealth + "]";
    }
}
