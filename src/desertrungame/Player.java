package desertrungame;

/**
 *
 * @author Patri
 */

import java.util.Random;

public class Player {
    private int health;
    private int hunger;
    private int thirst;
    private int currency;
    
    private Random random;

    public Player() {
        this.health = 20;
        this.hunger = 25;
        this.thirst = 50;
        this.currency = 0;
        
        this.random = new Random();
    }
    
    // compiles all functions for the end turn
    public void endTurn() {
        decreaseHunger(this.random.nextInt(1,5));
        decreaseThirst(this.random.nextInt(1,5));
        increaseCurrency(this.random.nextInt(2, 5));
    }
    
    // adjusts hunger
    public void increaseHunger(int amount) {
        this.hunger += amount;
    }
    
    public void decreaseHunger(int amount) {
        this.hunger -= amount;

        // if hunger is empty, decrease health by 2
        if (this.hunger < 0) {
            this.hunger = 0;
            decreaseHealth(2);
        } 
        // if hunger is low, decrease health by 1
        else if (this.hunger < 5) {
            decreaseHealth(1);
        }
    }
    
    // adjust thirst
    public void increaseThirst(int amount) {
        this.thirst += amount;
    }
    
    public void decreaseThirst(int amount) {
        this.thirst -= amount;
        
        // if thirst is empty, decrease health by 2
        if (this.thirst < 0) {
            this.thirst = 0;
            decreaseHealth(2);
        } 
        // if thirst is low, decrease health by 1
        else if (this.thirst < 5) {
            decreaseHealth(1); 
        }
    }
    
    // checks if player is at or below 0 health
    public boolean isDead() {
        return this.health <= 0;
    }

    // adjust health
    public void decreaseHealth(int amount) {
        this.health -= amount;
    }

    //adjust currency and prompts
    public void increaseCurrency(int amount) {
        this.currency += amount;
    }
    public void decreaseCurrency(int amount) {
        this.currency -= amount;
    }

    // getters and setters for all parameters
    public int getHealth() {
        return this.health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHunger() {
        return hunger;
    }
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getThirst() {
        return thirst;
    }
    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public int getCurrency() {
        return currency;
    }
    public void setCurrency(int currency) {
        this.currency = currency;
    }
}
