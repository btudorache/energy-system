package models;

/**
 * Entity class that holds data common to all entities
 */
public abstract class Entity {
    private final int id;
    private int budget;
    private boolean bankrupt;

    public Entity(final int id, final int budget) {
        this.id = id;
        this.budget = budget;
        this.bankrupt = false;
    }

    public final int getId() {
        return id;
    }

    public final int getBudget() {
        return budget;
    }

    public final void setBudget(final int budget) {
        this.budget = budget;
    }

    public final boolean isBankrupt() {
        return bankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        this.bankrupt = bankrupt;
    }
}
