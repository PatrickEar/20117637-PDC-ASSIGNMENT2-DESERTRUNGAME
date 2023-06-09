package desertrungame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patri
 */
public class Inventory {
    private List<Consumable> consumable; // The items in the inventory
    private Player player; // The player

    public Inventory(Player player) {
        this.consumable = new ArrayList<>();
        this.player = player;
    }

    // Adds a item to the inventory
    public void addItem(Consumable consumable) {
            this.consumable.add(consumable);
    }
    
    // Consumes item and adjusts accordingly
    public boolean consumeItem(int index) {
        if (!this.consumable.isEmpty() && index >= 0 && index < this.consumable.size()) {
            this.player.increaseHunger(this.consumable.get(index).getHungerEffect());
            this.player.increaseThirst(this.consumable.get(index).getThirstEffect());
            this.consumable.remove(index);

            // Ensure hunger and thirst cap
            if (this.player.getHunger() >= 25) {
                this.player.setHunger(25);
            }
            if (this.player.getThirst() >= 50) {
                this.player.setThirst(50);
            }

            return true; // Consumption successful
        } else {
            return false; // Invalid item input
        }
    }

    // Returns all items in list
    public List<Consumable> getItems() {
        return this.consumable;
    }
    
    // Returns size of list
    public int getSize() {
        return this.consumable.size();
    }
    
    // Emptys the list
    public void clearInventory() {
        this.consumable.clear();
    }
}
