package desertrungame;

/**
 *
 * @author Patri
 */
public abstract class Consumable {
    private String name;
    private String action;
    private int hungerEffect;
    private int thirstEffect;
    private int cost;
    private String id;

    // holds the name, use action text, hunger effect, thirst effect and ID of consumables
    public Consumable(String id, String name, String action, int hungerEffect, int thirstEffect, int cost) {
        this.name = name;
        this.action = action;
        this.hungerEffect = hungerEffect;
        this.thirstEffect = thirstEffect;
        this.cost = cost;
        this.id = id;
    }

    // getter and setters
    public String getName() {
        return this.name;
    }

    public String getAction() {
        return this.action;
    }

    public int getHungerEffect() {
        return this.hungerEffect;
    }

    public int getThirstEffect() {
        return this.thirstEffect;
    } 
    
    public String getId() {
        return this.id;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public int getCost() {
        return this.cost;
    }
}
