package desertrungame;

/**
 *
 * @author Patri
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Menu {
    
    private Scanner scanner;
    private Random random;
    private Player player;
    private Inventory inventory;
    private SaveLoad saveLoad;
    
    private JFrame frame;
    
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
    
    // Prompts the load game selction menu
    public String loadGameMenu(Difficulty difficulty, Player player, Inventory inventory, Map map) throws IOException {
        File folder = new File("saves/");
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "No saved files found.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        File[] loadFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });

        if (loadFiles.length == 0) {
            JOptionPane.showMessageDialog(null, "No saved files found.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        // Create a panel with search filter and selection options
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField searchField = new JTextField(20);
        panel.add(new JLabel("Search:"));
        panel.add(searchField);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        panel.add(scrollPane);

        // Populate the file list with all available files
        for (File file : loadFiles) {
            listModel.addElement(file.getName());
        }

        // Add a search filter listener
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterFiles();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterFiles();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterFiles();
            }

            private void filterFiles() {
                String searchText = searchField.getText().trim().toLowerCase();
                listModel.clear();

                for (File file : loadFiles) {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.contains(searchText)) {
                        listModel.addElement(file.getName());
                    }
                }
            }
        });

        // Create a dialog to show the selection menu
        JDialog dialog = new JDialog((Frame) null, "Load Game", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);

        // Add a Load Game button
        JButton loadButton = new JButton("Load Game");
        final String[] loadedFileName = {null};  // Variable to store the loaded file name

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = fileList.getSelectedIndex();

                if (selectedIndex == -1 || selectedIndex >= listModel.getSize()) {
                    JOptionPane.showMessageDialog(panel, "Please select a valid saved file.", "Load Game", JOptionPane.WARNING_MESSAGE);
                } else {
                    File loadFile = loadFiles[selectedIndex];
                    int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to load this game?", "Load Game", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            saveLoad.loadFromFile(difficulty, player, inventory, map, folder + "/" + loadFile.getName());
                            JOptionPane.showMessageDialog(panel, "Game loaded successfully.", "Load Game", JOptionPane.INFORMATION_MESSAGE);
                            dialog.dispose();
                            loadedFileName[0] = loadFile.getName();  // Set the loaded file name
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(panel, "Error loading the game.", "Load Game", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        panel.add(loadButton);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return loadedFileName[0];
    }
    
    public void endTurn(Player player, Map map) {
        map.endTurn();

        StringBuilder playerInfo = new StringBuilder();
        playerInfo.append("Health: ").append(player.getHealth()).append("\n");
        playerInfo.append("Hunger: ").append(player.getHunger()).append("\n");
        playerInfo.append("Thirst: ").append(player.getThirst()).append("\n");
        playerInfo.append("Currency: ").append(player.getCurrency()).append("\n");
        playerInfo.append("Distance: ").append(map.getCurrentPosition()).append("\n");

        JOptionPane.showMessageDialog(null, "Your turn has ended.\n\n" + playerInfo.toString(), "End Turn", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void isEvent(Map map) {
        int randomChance = this.random.nextInt(100); // Generate a random number between 0-99

        // Checks if user is able to scout area
        if (!map.getHasScouted()) {
            if (randomChance < (20 * map.getEventMultiplier())) { // 20% chance for FoodMarket event, opens menu
                JOptionPane.showMessageDialog(null, map.getFoodMarket().getDescription(), "Event", JOptionPane.INFORMATION_MESSAGE);
                foodMarketMenu();

            } else if (randomChance >= (20 * map.getEventMultiplier()) && randomChance < (30 * map.getEventMultiplier())) { // 10% chance for Oasis event, opens menu
                JOptionPane.showMessageDialog(null, map.getOasis().getDescription(), "Event", JOptionPane.INFORMATION_MESSAGE);
                oasisMenu();

            } else {
                int randomCurrency = this.random.nextInt(1, 6); // Generate a random number between 1-5 for currency found
                JOptionPane.showMessageDialog(null, map.getFindCurrency().getDescription(), "Event", JOptionPane.INFORMATION_MESSAGE);
                // Add the found currency to the player's inventory
                this.player.increaseCurrency(randomCurrency);
            }
            map.setHasScouted(true);

        } else {
            JOptionPane.showMessageDialog(null, "You have already scouted the area!", "Event", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Generates a food market menu
    public void foodMarketMenu() {
        List<Consumable> foodMarketShop = generateFoodMarketShop();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Consumable item : foodMarketShop) {
            listModel.addElement(item.getName() + " (" + item.getCost() + " coins)");
        }

        JList<String> itemList = new JList<>(listModel);
        itemList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel currencyLabel = new JLabel("You currently have " + player.getCurrency() + " coins.");
        panel.add(currencyLabel);

        panel.add(new JLabel("<html><br>Welcome to the Food Market!<br></html>"));
        panel.add(scrollPane);

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < foodMarketShop.size()) {
                Consumable selectedItem = foodMarketShop.get(selectedIndex);
                if (player.getCurrency() >= selectedItem.getCost()) {
                    inventory.addItem(selectedItem);
                    player.decreaseCurrency(selectedItem.getCost());
                    JOptionPane.showMessageDialog(null, "You purchased " + selectedItem.getName() + "!", "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);
                    foodMarketShop.remove(selectedIndex);
                    listModel.removeElementAt(selectedIndex);
                    itemList.clearSelection(); // Clear the selection
                    currencyLabel.setText("You currently have " + player.getCurrency() + " coins."); // Update the currency label
                } else {
                    JOptionPane.showMessageDialog(null, "You don't have enough coins to buy this item.", "Not Enough Coins", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        panel.add(buyButton);

        JButton leaveButton = new JButton("Leave");
        leaveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "You left the Food Market.", "Shop Closed", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose(); // Close the food market menu window
        });
        panel.add(leaveButton);

        frame = new JFrame("Food Market");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    // Oasis menu generator
    public void oasisMenu() {
        List<Consumable> oasisShop = generateOasisShop();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Consumable item : oasisShop) {
            listModel.addElement(item.getName());
        }

        JList<String> itemList = new JList<>(listModel);
        itemList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("<html><br>Welcome to the Oasis!<br></html>"));
        panel.add(scrollPane);

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < oasisShop.size()) {
                Consumable selectedItem = oasisShop.get(selectedIndex);
                inventory.addItem(selectedItem);
                JOptionPane.showMessageDialog(null, "You found a " + selectedItem.getName() + "!", "Searching Successful", JOptionPane.INFORMATION_MESSAGE);
                oasisShop.remove(selectedIndex);
                listModel.removeElementAt(selectedIndex);
                itemList.clearSelection(); // Clear the selection
            }
        });
        panel.add(buyButton);

        JButton leaveButton = new JButton("Leave");
        leaveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "You left the Oasis.", "Location Left", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose(); // Close the oasis menu window
        });
        panel.add(leaveButton);

        frame = new JFrame("Oasis");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    // Generates a list of items for food market menu
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
    
    // Generates a list of items for oasis menu
    private List<Consumable> generateOasisShop() {
        List<Consumable> oasisItems = new ArrayList<>();

        // All items in oasis are free
        oasisItems.add(new CactusMelon(0));
        oasisItems.add(new Waternut());
        oasisItems.add(new Sandshroom());
        oasisItems.add(new DryFruit());
        oasisItems.add(new PalmSap());
        
        // Shuffles and selects three random items to add to list
        Collections.shuffle(oasisItems, this.random);
        
        List<Consumable> randomOasisItems = oasisItems.subList(0, 3);
        List<Consumable> OasisShop = new ArrayList<>(randomOasisItems);
        
        return OasisShop;
    }
    
    // Opens the player current inventory 
    public void InventoryMenu(Inventory inventory, JList<String> inventoryList, Map map, Player player) {
        boolean consumedItem = false;

        do {
            DefaultListModel<String> inventoryListModel = new DefaultListModel<>();

            // Populate the inventory list model with item names
            for (Consumable item : inventory.getItems()) {
                inventoryListModel.addElement(item.getName());
            }

            // Set the inventory list model to the JList
            inventoryList.setModel(inventoryListModel);

            // Create a StringBuilder to store additional player information
            StringBuilder playerInfo = new StringBuilder();
            playerInfo.append("Hunger: ").append(player.getHunger()).append("\n");
            playerInfo.append("Thirst: ").append(player.getThirst()).append("\n");
            playerInfo.append("Currency: ").append(player.getCurrency()).append("\n");
            playerInfo.append("Distance: ").append(map.getCurrentPosition());

            // Check if there are no items in the inventory
            if (inventoryListModel.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You have no more items!\n\n" + playerInfo, "Inventory", JOptionPane.PLAIN_MESSAGE);
                break;
            }

            // Create a JPanel to hold the inventory list and player information
            JPanel panel = new JPanel(new BorderLayout());

            // Create a JLabel for player information and add it to the panel at the top
            JLabel playerInfoLabel = new JLabel("<html><pre>" + playerInfo.toString() + "</pre></html>");
            panel.add(playerInfoLabel, BorderLayout.NORTH);

            // Add the JScrollPane with inventory list to the center of the panel
            panel.add(new JScrollPane(inventoryList), BorderLayout.CENTER);

            // Show the inventory menu
            int option = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Inventory",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
            );

            // Check if an item is selected
            if (option == JOptionPane.OK_OPTION && inventoryList.getSelectedIndex() != -1) {
                // Get the selected item index
                int selectedIndex = inventoryList.getSelectedIndex();

                // Get the selected item
                Consumable selectedItem = inventory.getItems().get(selectedIndex);

                // Ask the user if they want to consume the item
                int confirmOption = JOptionPane.showConfirmDialog(
                        null,
                        "Consume item: " + selectedItem.getName() + "?",
                        "Consume Item",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmOption == JOptionPane.YES_OPTION) {
                    // Consume the item
                    inventory.consumeItem(selectedIndex);
                    consumedItem = true;
                    JOptionPane.showMessageDialog(null, selectedItem.getAction(), "Item Consumed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    consumedItem = false; // Item not consumed, reopen the inventory menu
                }
            } else {
                consumedItem = false; // Item not selected or cancel button clicked, reopen the inventory menu
            }
        } while (consumedItem);
    }
}


