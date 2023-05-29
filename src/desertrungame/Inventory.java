package desertrungame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patri
 */
public class Inventory {
    private List<Consumable> consumable; // the items in the inventory
    private Player player; // the player

    public Inventory(Player player) {
        this.consumable = new ArrayList<>();
        this.player = player;
    }

    // adds a item to the inventory
    public void addItem(Consumable consumable) {
            this.consumable.add(consumable);
    }
    
    // consumes item and adjusts accordingly
    public void consumeItem(int index) {
        if (!this.consumable.isEmpty() && index >= 0 && index < this.consumable.size()) {
            this.player.increaseHunger(this.consumable.get(index).getHungerEffect());
            this.player.increaseThirst(this.consumable.get(index).getThirstEffect());
            this.consumable.remove(index);
            
            // ensures hunger and thirst cap
            if(this.player.getHunger() >= 25){
                this.player.setHunger(25);
            }
            if(this.player.getThirst() >= 50){
                this.player.setThirst(50);
            }
        } else {
            System.out.println("Invalid item input!");
        }
    }

    // returns all items in list
    public List<Consumable> getItems() {
        return this.consumable;
    }
    
    // returns size of list
    public int getSize() {
        return this.consumable.size();
    }
    
    // emptys the list
    public void clearInventory() {
        this.consumable.clear();
    }
}
