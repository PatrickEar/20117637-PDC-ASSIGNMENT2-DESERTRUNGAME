package desertrungame;

/**
 *
 * @author Patri
 */


// Git Test

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DesertRunGame {
    
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        SaveLoad saveLoad = new SaveLoad();
        
        // main menu loop
        while(true) {
            // initialise game classes
            Difficulty difficulty = Difficulty.EASY;
            Player player = new Player();
            Inventory inventory = new Inventory(player);
            Map map = new Map(difficulty, player, inventory);
            Menu menu = new Menu(difficulty, player, inventory);
            
            // for game loop
            boolean gameLoop = false;
            
            // holds the current save file
            String saveFileName = "";
               
            // main menu
            System.out.println("Welcome to Desert Run!");
            System.out.println("=========================");
            System.out.println("1 - New Game");
            System.out.println("2 - Load Game");
            System.out.println("3 - Credits");
            System.out.println("4 - Quit Game");

            String input = scanner.nextLine();
            System.out.println();

            // inputs
            switch (input) {
                // prompts an input for a file name and repeats until valid input
                case "1":
                    Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+"); // Only allow letters, numbers, underscore, and hyphen
                    Matcher matcher;
                    System.out.println("=========================");
                            
                    do {
                        System.out.println("Enter file name:");
                        saveFileName = scanner.nextLine();
                        matcher = pattern.matcher(saveFileName);
                    } while (!matcher.matches());
                    
                    // adds extension to file
                    saveFileName += ".txt";
                    System.out.println();
                    
                    // difficulty select loop
                    boolean difficultyLoop = true;
                    difficulty = null;
                    
                    // difficulty loop 
                    while(difficultyLoop){
                        // difficulty select menu
                        System.out.println("Choose a difficulty level:");
                        System.out.println("=========================");
                        System.out.println("1 - Easy");
                        System.out.println("2 - Normal");
                        System.out.println("3 - Hard");
                        
                        // difficulty adjusts multipliers for event chance,
                        // price multiplier and spaces to win the game
                        String difficultyChoice = scanner.nextLine();
                        switch (difficultyChoice) {
                            case "1":
                                difficulty = Difficulty.EASY;
                                difficultyLoop = false;
                                break;
                            case "2":
                                difficulty = Difficulty.NORMAL;
                                difficultyLoop = false;
                                break;
                            case "3":
                                difficulty = Difficulty.HARD;
                                difficultyLoop = false;
                                break;
                            default:
                                System.out.println("Invalid input");
                        }
                    }
                    
                    System.out.println("\n=========================");
                    System.out.println("You chose " + difficulty + " difficulty.");
                    
                    // begin game loop
                    gameLoop = true;
                    break;

                case "2":
                    // prompts user to select a file to load (if able)
                    System.out.println("=========================");
                    saveFileName = menu.loadGameMenu(difficulty, player, inventory, map);
                    // if a file is not returned do not start game loop
                    if (saveFileName != null) {
                        gameLoop = true;
                    }
                    break;
                case "3":
                    // credits for the game creation
                    System.out.println("=========================");
                    System.out.println("By Patrick Ear 20117637");
                    System.out.println("=========================");
                    break;
                case "4":
                    // exits the game/application
                    System.out.println("Exiting game...");

                    System.exit(0);
                default:
                    System.out.println("Invalid input.");
            }

            // Game loop
            while (gameLoop && !map.isWin()) {
                // display status
                System.out.println("=========================");
                System.out.println("Your current position is " + map.getCurrentPosition());
                // displays if user is able to scout area
                if (map.getHasScouted()) {
                    System.out.println("You have scouted the area!");
                } else {
                    System.out.println("The area has not been scouted yet!");
                }

                // game menu
                System.out.println("=========================");
                System.out.println("Choose an action:");
                System.out.println("=========================");
                System.out.println("1 - Walk (End Turn)");
                System.out.println("2 - Scout (Once per turn)");
                System.out.println("3 - Inventory");
                System.out.println("4 - Save and Quit to Menu");

                input = scanner.nextLine();
                System.out.println();

                // inputs
                switch (input) {
                    case "1":
                        // ends users turn
                        map.endTurn();
                        break;
                    case "2":
                        // scouts for a randomized event to occur
                        map.isEvent();
                        break;
                    case "3":
                        // opens the inventory menu
                        menu.InventoryMenu();
                        break;
                    case "4":
                        // saves the current stats of the game and returns to main menu
                        System.out.println("=========================");
                        System.out.println("Saving file as " + saveFileName + " and Returning to menu...");
                        System.out.println("=========================");
                        saveLoad.saveToFile(difficulty, player, inventory, map, saveFileName);
                        gameLoop = false;
                        break;
                    default:
                        System.out.println("Invalid input.");
                }

                // check if player died, deletes file if true
                if (player.isDead()) {
                    saveLoad.deleteFile(saveFileName);
                    System.out.println("=========================");
                    System.out.println("You died! Game over.");
                    System.out.println("Deleting save file...");
                    System.out.println("=========================");
                    System.out.println("Final position: " + map.getCurrentPosition());
                    System.out.println("Final hunger: " + player.getHunger());
                    System.out.println("Final thirst: " + player.getThirst());
                    System.out.println("Final Currency: " + player.getCurrency());
                    System.out.println("=========================");
                    System.out.println("Input anything to return to menu");
                    scanner.nextLine();
                    System.out.println("=========================");
                    gameLoop = false;
                }
                // check if player has won, deletes file if true
                if (map.isWin()) {
                    saveLoad.deleteFile(saveFileName);
                    System.out.println("=========================");
                    System.out.println("Congratulations, you made it to the finish!");
                    System.out.println("Deleting save file...");
                    System.out.println("=========================");
                    System.out.println("Final position: " + map.getCurrentPosition());
                    System.out.println("Final hunger: " + player.getHunger());
                    System.out.println("Final thirst: " + player.getThirst());
                    System.out.println("Fincal Currency: " + player.getCurrency());
                    System.out.println("Thanks for playing!");
                    System.out.println("Input anything to return to menu");
                    scanner.nextLine();
                    System.out.println("=========================");
                    gameLoop = false;
                }
            }
        } 
    }
}
