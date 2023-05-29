package desertrungame;

/**
 *
 * @author Patri
 */
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    
    private Scanner scanner;
    private Random random;
    private Player player;
    private Inventory inventory;
    private SaveLoad saveLoad;
    
    private Difficulty difficulty;
    private double currencyMultiplier;


    public Menu(Difficulty difficulty, Player player, Inventory inventory) {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.saveLoad = new SaveLoad();
        
        this.player = player;
        this.inventory = inventory;
        
        this.difficulty = difficulty;
        this.currencyMultiplier = this.difficulty.getPriceMultiplier();
    }
    
    // prompts the load game selction menu
    public String loadGameMenu(Difficulty difficulty, Player player, Inventory inventory, Map map) throws IOException {
        // searches for a saves folder
        File folder = new File("saves/");
        if (!folder.exists()) {
            System.out.println("No saved files found.");
            return null;
        }
        
        // loads files with the .txt extension
        File[] loadFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        
        // if no files were found 
        if (loadFiles.length == 0) {
            System.out.println("No saved files found.");
            return null;
        }
        
        // prompts user to select a file to load
        System.out.println("Choose a saved file to load:");
        
        // generates a dynamic list to choose a file from
        for (int i = 0; i < loadFiles.length; i++) {
            System.out.printf("%d - %s%n", i + 1, loadFiles[i].getName());
        }
        System.out.println((loadFiles.length + 1) + ". Return to menu");
        
        // when input is valid, load file / return to menu
        int input = -1;
        while (input < 1 || input > loadFiles.length) {
            // handle invalid input
            try {
                input = Integer.parseInt(this.scanner.nextLine());
                
                if (input == ((loadFiles.length+1))) {
                System.out.println("=========================");
                System.out.println("Returning to menu...");
                return null;
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        // prepares file to be returned/used
        File loadFile = loadFiles[input - 1];

        // load the game from the selected file
        saveLoad.loadFromFile(difficulty, player, inventory, map, folder + "/" + loadFile.getName());
        
        // return the name of the file
        return loadFile.getName();
    }
    
    // generates a food market menu
    public void foodMarketMenu() {
        // generates options for the user to purchase from
        List<Consumable> foodMarketShop = generateFoodMarketShop();
        
        System.out.println("=========================");
        System.out.println("\"Welcome to the Food Market!\"");
        System.out.println("You currently have " + this.player.getCurrency() + " coins!");
        System.out.println("=========================");
        
        System.out.println("The vendor asks...");
        
        // creates a dynamic list that the user is able to purchase consumables from
        while(true) {
            System.out.println("=========================");
            System.out.println("What would you like to buy?");
            System.out.println("=========================");
            
            for (int i = 0; i < foodMarketShop.size(); i++) {
                System.out.println((i+1) + ". " + foodMarketShop.get(i).getName() + " (" + foodMarketShop.get(i).getCost() + " coins)");
            }
            System.out.println((foodMarketShop.size() + 1) + ". Leave Food Market");
            
            int input = '\0';
            
            // handles invalid inputs
            try {
                input = this.scanner.nextInt();
            }catch(Exception e){
                System.out.println("Invalid number!");
                this.scanner.nextLine(); // consumes the newline character fron nextInt() to prevent try catch loop from occuring
                continue;
            }
            
            // leaves food market and return to game menu
            if (input == ((foodMarketShop.size()+1))) {
                System.out.println("=========================");
                System.out.println("Leaving Food Market...");
                return;
            } else if (input < 1 || input > foodMarketShop.size()) {
                System.out.println("Invalid option!");
                continue;
            }
            
            Consumable option = foodMarketShop.get(input-1);
            
            // checks if player has enough coins to purchase and adds to inventory
            if(this.player.getCurrency() >= option.getCost()){
                this.inventory.addItem(option);
                this.player.decreaseCurrency(option.getCost());
                System.out.println("\"Thank you for purchasing my " + option.getName() + "!\"");
                System.out.println("You now have " + this.player.getCurrency() + " coins!");
                
                foodMarketShop.remove(option);
            } else {
                System.out.println("\"You don't have enough coins to buy this!\"");
            }
        }
    }
    
    // oasis menu generator
    public void OasisMenu() {
        // generates a random list of three consumables the user can take for free
        List<Consumable> OasisShop = generateOasisShop();
        
        System.out.println("=========================");
        System.out.println("\"Welcome to the Oasis!\""); 
        
        // creates a dynamic list the user can choose from
        while(true) {
            System.out.println("=========================");
            System.out.println("You find...");
            System.out.println("=========================");
            for (int i = 0; i < OasisShop.size(); i++) {
                System.out.println((i+1) + ". " + OasisShop.get(i).getName());
            }
            System.out.println((OasisShop.size() + 1) + ". Leave Oasis");
            
            int input = '\0';
            
            // handles invalid input
            try {
                input = this.scanner.nextInt();
            }catch(Exception e){
                System.out.println("Invalid number!");
                this.scanner.nextLine(); // consumes the newline character fron nextInt() to prevent try catch loop from occuring
                continue;
            }
            
            // leaves oasis and return to game menu
            if (input == ((OasisShop.size()+1))) {
                System.out.println("=========================");
                System.out.println("Leaving Oasis...");
                return;
            } else if (input < 1 || input > OasisShop.size()) {
                System.out.println("Invalid option!");
                continue;
            }
            
            Consumable option = OasisShop.get(input-1);
            
            // adds item to player inventory
            this.inventory.addItem(option);
            System.out.println("\"You store the " + option.getName() + " in your inventory!\"");

            OasisShop.remove(option);
        }
    }
    
    // generates a list of items for food market menu
    private List<Consumable> generateFoodMarketShop() {
        List<Consumable> foodMarketShop = new ArrayList<>();
        
        // items need to be expicilty told to become rounded due to currency multiplier (Easy: 0.5, Normal: 1.0, Hard: 2.0)
        foodMarketShop.add(new OatSlab((int) Math.round(this.currencyMultiplier * this.random.nextInt(3, 6))));
        foodMarketShop.add(new PurifiedWater((int) Math.round(this.currencyMultiplier * this.random.nextInt(2, 5))));
        foodMarketShop.add(new MysteryMeat((int) Math.round(this.currencyMultiplier * this.random.nextInt(7, 10))));
        foodMarketShop.add(new CamelHumpWater((int) Math.round(this.currencyMultiplier * this.random.nextInt(6, 9))));
        foodMarketShop.add(new CactusMelon((int) Math.round(this.currencyMultiplier * this.random.nextInt(10, 13))));
        return foodMarketShop;
    }
    
    // generates a list of items for oasis menu
    private List<Consumable> generateOasisShop() {
        List<Consumable> oasisItems = new ArrayList<>();

        // all items in oasis are free
        oasisItems.add(new CactusMelon(0));
        oasisItems.add(new Waternut());
        oasisItems.add(new Sandshroom());
        oasisItems.add(new DryFruit());
        oasisItems.add(new PalmSap());
        
        // shuffles and selects three random items to add to list
        Collections.shuffle(oasisItems, this.random);
        
        List<Consumable> randomOasisItems = oasisItems.subList(0, 3);
        List<Consumable> OasisShop = new ArrayList<>(randomOasisItems);
        
        return OasisShop;
    }
    
    // opens the player current inventory 
    public void InventoryMenu() {
        System.out.println("=========================");
        System.out.println("You open your inventory!");
        
        // display stats of player
        while(true) {
            System.out.println("=========================");
            System.out.println("Your Current Stats");
            System.out.println("=========================");
            System.out.println("Hunger: " + player.getHunger());
            System.out.println("Thirst: " + player.getThirst());
            System.out.println("Health: " + player.getHealth());
            System.out.println("Coins: " + player.getCurrency());
            System.out.println("=========================");
            
            // prompts the user to select from a dynamic list to use consumables
            System.out.println("\nWhat would you like to use?");
            System.out.println("=========================");
            for (int i = 0; i < inventory.getItems().size(); i++) {
                System.out.println((i+1) + ". " + inventory.getItems().get(i).getName());
            }
            System.out.println((inventory.getItems().size() + 1) + ". Leave Inventory");
            
            int input = '\0';
            
            // handles invalid input
            try {
                input = this.scanner.nextInt();
            }catch(Exception e){
                System.out.println("Invalid number!");
                this.scanner.nextLine(); // consumes the newline character fron nextInt() to prevent try catch loop from occuring
                continue;
            }
            
            // exits inventory and return to game menu
            if (input == ((inventory.getItems().size()+1))) {
                System.out.println("=========================");
                System.out.println("Packing Inventory...");
                return;
            } else if (input < 1 || input > inventory.getItems().size()) {
                System.out.println("Invalid option!");
                continue;
            }
            
            // consume the selected item and change stats accordingly
            System.out.println("You use the " + inventory.getItems().get(input-1).getName() + " in your inventory!");
            
            System.out.println(inventory.getItems().get(input-1).getAction());

            inventory.consumeItem(input-1);
        }
    }
}
