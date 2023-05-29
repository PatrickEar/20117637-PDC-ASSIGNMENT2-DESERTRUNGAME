package desertrungame;

/**
 *
 * @author Patri
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class SaveLoad {
    
    // save the game state to a file
    public void saveToFile(Difficulty difficulty, Player player, Inventory inventory, Map map, String fileName) throws IOException {
        File folder = new File("saves");
        folder.mkdir();
        try ( 
            // open the file for writing
            PrintWriter writer = new PrintWriter(new FileWriter(folder + "/" + fileName))) {
            
            // write the difficulty data
            writer.println(difficulty.getEventMultiplier());
            writer.println(difficulty.getPriceMultiplier());
            writer.println(difficulty.getDistanceToWin());
            
            // write the player data
            writer.println(player.getHealth());
            writer.println(player.getHunger());
            writer.println(player.getThirst());
            writer.println(player.getCurrency());
            
            // write the inventory data
            List<Consumable> consumables = inventory.getItems();
            writer.println(consumables.size());
            for (Consumable c : consumables) {
                writer.println(c.getId());
            }   
            
            // write the map data
            writer.println(map.getCurrentPosition());
            writer.println(map.getHasScouted());
            
            // close the file
            writer.close();
        }
    }
    
    public void loadFromFile(Difficulty difficulty, Player player, Inventory inventory, Map map, String fileName) throws IOException {
        
        // open the file for reading
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        
        // read and set difficulty data
        difficulty.setEventMultiplier(scanner.nextDouble());
        difficulty.setPriceMultiplier(scanner.nextDouble());
        difficulty.setDistanceToWin(scanner.nextInt());
        
        // read and set player data
        player.setHealth(scanner.nextInt());
        player.setHunger(scanner.nextInt());
        player.setThirst(scanner.nextInt());
        player.setCurrency(scanner.nextInt());
        
        // read and set inventory
        int sizeConsumables = scanner.nextInt();
        inventory.clearInventory();
        // checks id of consumable items and adds the item to the inventory
        for (int i = 0; i < sizeConsumables; i++) {
            String consumable = scanner.next();
            switch (consumable) {
                case "001":
                    {
                        CactusMelon itemConsumable = new CactusMelon(0);
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "002":
                    {
                        Waternut itemConsumable = new Waternut();
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "003":
                    {
                        Sandshroom itemConsumable = new Sandshroom();
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "004":
                    {
                        DryFruit itemConsumable = new  DryFruit();
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "005":
                    {
                        PalmSap itemConsumable = new PalmSap();
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "006":
                    {
                        OatSlab itemConsumable = new OatSlab(0);
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "007":
                    {
                        PurifiedWater itemConsumable = new PurifiedWater(0);
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "008":
                    {
                        MysteryMeat itemConsumable = new MysteryMeat(0);
                        inventory.addItem(itemConsumable);
                        break;
                    }
                case "009":
                    {
                        CamelHumpWater itemConsumable = new CamelHumpWater(0);
                        inventory.addItem(itemConsumable);
                        break;
                    }
                default:
                    break;
            }
        }
        
        // read and set map
        int currentPosition = scanner.nextInt();
        boolean hasScouted = scanner.nextBoolean();
        map.setCurrentPosition(currentPosition);
        map.setHasScouted(hasScouted);
        
        // close the file
        scanner.close();
    }
    
    public void deleteFile(String fileName) {
        File saveFile = new File("saves/" + fileName);
        saveFile.delete();
    }
}
