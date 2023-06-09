package desertrungame;

/**
 *
 * @author Patri
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class DesertRunGame extends JFrame implements ActionListener {
    private JLabel statusLabel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton creditsButton;
    private JButton quitButton;
    private JTextField fileNameField;
    private JButton startButton;
    private JComboBox<String> difficultyComboBox;
    private JButton walkButton;
    private JButton scoutButton;
    private JButton inventoryButton;
    private JList<String> inventoryList;
    private DefaultListModel<String> inventoryListModel;
    private JButton saveAndQuitButton;
    
    private Scanner scanner = new Scanner(System.in);
    private SaveLoad saveLoad = new SaveLoad();
    private Difficulty difficulty;
    private Player player;
    private Inventory inventory;
    private Map map;
    private Menu menu;
    private boolean gameLoop;
    private String saveFileName;
    
    public DesertRunGame() {
        // Initialize game classes
        difficulty = Difficulty.EASY;
        player = new Player();
        inventory = new Inventory(player);
        map = new Map(difficulty, player, inventory);
        menu = new Menu(difficulty, player, inventory);
        gameLoop = false;
        saveFileName = "";
        
        // Set up the main menu GUI
        setTitle("Desert Run");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        statusLabel = new JLabel("Welcome to Desert Run!");
        add(statusLabel);
        
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        add(newGameButton);
        
        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(this);
        add(loadGameButton);
        
        creditsButton = new JButton("Credits");
        creditsButton.addActionListener(this);
        add(creditsButton);
        
        quitButton = new JButton("Quit Game");
        quitButton.addActionListener(this);
        add(quitButton);
        
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DesertRunGame();
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        
        if (actionCommand.equals("New Game")) {
            // Show the new game setup GUI
            getContentPane().removeAll();
            setTitle("Desert Run - New Game");
            
            JLabel fileNameLabel = new JLabel("Enter file name:");
            add(fileNameLabel);
            
            fileNameField = new JTextField(20);
            add(fileNameField);
            
            JButton startButton = new JButton("Start Game");
            startButton.addActionListener(this);
            add(startButton);
            
            JLabel difficultyLabel = new JLabel("Choose a difficulty level:");
            add(difficultyLabel);
            
            difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Normal", "Hard"});
            add(difficultyComboBox);
            
            pack();
            revalidate();
            repaint();
        } else if (actionCommand.equals("Start Game")) {
            // Retrieve the user inputs from the GUI
            saveFileName = fileNameField.getText();
            String difficultyChoice = (String) difficultyComboBox.getSelectedItem();
            
            // Validate the file name
            Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+");
            Matcher matcher = pattern.matcher(saveFileName);
            
            if (matcher.matches()) {
                // Add the file extension
                saveFileName += ".txt";
                
                // Set the difficulty based on user choice
                switch (difficultyChoice) {
                    case "Easy" -> difficulty = Difficulty.EASY;
                    case "Normal" -> difficulty = Difficulty.NORMAL;
                    case "Hard" -> difficulty = Difficulty.HARD;
                }
                
                // Start the game
                startGame();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid file name. Please enter alphanumeric characters, underscores, or hyphens only.");
            }
        } else if (actionCommand.equals("Load Game")) {
            try {
                // Show the load game GUI
                saveFileName = menu.loadGameMenu(difficulty, player, inventory, map);
            } catch (IOException ex) {
                Logger.getLogger(DesertRunGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // If a file is not returned, do not start the game loop
            if (saveFileName != null) {
                startGame();
            }
        } else if (actionCommand.equals("Credits")) {
            // Show the credits
            JOptionPane.showMessageDialog(this, "By Patrick Ear 20117637");
        } else if (actionCommand.equals("Quit Game")) {
            // Exit the game/application
            System.exit(0);
        } else if (actionCommand.equals("Walk")) {
            // End the user's turn
            menu.endTurn(player, map);
        } else if (actionCommand.equals("Scout")) {
            // Scout for a randomized event to occur
            menu.isEvent(map);
        } else if (actionCommand.equals("Inventory")) {
            // Open the inventory menu     
            menu.InventoryMenu(inventory, inventoryList, map, player);
        } else if (actionCommand.equals("Save and Quit")) {
            try {
                // Save the current stats of the game and return to the main menu
                saveLoad.saveToFile(difficulty, player, inventory, map, saveFileName);
            } catch (IOException ex) {
                Logger.getLogger(DesertRunGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameLoop = false;
            
            // Show the main menu GUI
            getContentPane().removeAll();
            setTitle("Desert Run");
            add(statusLabel);
            add(newGameButton);
            add(loadGameButton);
            add(creditsButton);
            add(quitButton);
            pack();
            revalidate();
            repaint();
        }
        
        if (player.isDead()) {
            saveLoad.deleteFile(saveFileName);
            JOptionPane.showMessageDialog(this, "You died! Game over.\nDeleting save file...");
            displayFinalStats();
            gameLoop = false;
        }
        
        if (map.isWin()) {
            saveLoad.deleteFile(saveFileName);
            JOptionPane.showMessageDialog(this, "Congratulations, you made it to the finish!\nDeleting save file...\nThanks for playing!");
            displayFinalStats();
            gameLoop = false;
        }
    }
    
    private void startGame() {
        // Set up the game loop
        gameLoop = true;
        
        // Show the game GUI
        getContentPane().removeAll();
        setTitle("Desert Run - Game");
        
        walkButton = new JButton("Walk");
        walkButton.addActionListener(this);
        add(walkButton);
        
        scoutButton = new JButton("Scout");
        scoutButton.addActionListener(this);
        add(scoutButton);
        
        inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener(this);
        add(inventoryButton);
        
        inventoryListModel = new DefaultListModel<>();
        inventoryList = new JList<>(inventoryListModel);
        add(inventoryList);
        
        saveAndQuitButton = new JButton("Save and Quit");
        saveAndQuitButton.addActionListener(this);
        add(saveAndQuitButton);
        
        pack();
        revalidate();
        repaint();
    }
    
    private void displayFinalStats() {
        JOptionPane.showMessageDialog(this,
                "Final position: " + map.getCurrentPosition() +
                "\nFinal hunger: " + player.getHunger() +
                "\nFinal thirst: " + player.getThirst() +
                "\nFinal Currency: " + player.getCurrency());
    }
}
