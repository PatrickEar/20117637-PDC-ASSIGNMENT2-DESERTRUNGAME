package desertrungame;

/**
 *
 * @author Patri
 */
import java.util.Random;

public class Map {

    private static final int STARTING_POSITION = 0; // the player's starting position
    
    private Player player; // the player
    private Menu menu; // the menu
    private boolean hasScouted; // checks if player has used the scout option
    
    private double eventMultiplier; // adjusts the chances for events based on difficulty
    private int requiredPosition; // the required position to win based on difficulty
    
    private int currentPosition; // the player's current position

    private Random random; // a random number generator
    
    // events for scouting option
    private Event oasis;
    private Event foodMarket; 
    private Event findCurrency;

    public Map(Difficulty difficulty, Player player, Inventory inventory) {
        this.currentPosition = STARTING_POSITION;
        this.player = player;
        this.menu = new Menu(difficulty, player, inventory);
        this.hasScouted = false;
        
        this.random = new Random();
        
        // events the player can encounter
        this.oasis = new Event("Oasis", "You found an oasis! You can eat and drink for free.");
        this.foodMarket = new Event("Food Market", "You found a food market! You can buy some food and drinks.");
        this.findCurrency = new Event("Find Currency", "You found some coins on the ground!");
        
        this.requiredPosition = difficulty.getDistanceToWin();
        this.eventMultiplier = difficulty.getEventMultiplier();
    }

    // moves player in game
    public void move() {
        int spacesMoved = this.random.nextInt(1,5); // move 1-4 spaces
        this.currentPosition += spacesMoved; 
        System.out.println("You travel " + spacesMoved +" spaces");
    }
    
    // checks if player has won
    public boolean isWin() {
        return this.currentPosition >= this.requiredPosition;
    } 
    
    // generates an random event from the events a player can encounter
    public void isEvent() {
        int randomChance = this.random.nextInt(100); // generate a random number between 0-99
        
        // checks if user is able to scout area
        if(!this.hasScouted) {
            if (randomChance < (20 * this.eventMultiplier)) { // 20% chance for FoodMarket event, opens menu
                System.out.println("=========================");
                System.out.println(this.foodMarket.getDescription());
                this.menu.foodMarketMenu();

            } else if (randomChance >= (20 * this.eventMultiplier) && randomChance < (30 * this.eventMultiplier)) { // 10% chance for Oasis event, opens menu
                System.out.println("=========================");
                System.out.println(this.oasis.getDescription());
                this.menu.OasisMenu();

            } else {
                int randomCurrency = this.random.nextInt(1,6); // generate a random number between 1-5 for currency found
                System.out.println("=========================");
                System.out.println(this.findCurrency.getDescription());
                // add the found currency to the player's inventory
                this.player.increaseCurrency(randomCurrency);

            }
            this.hasScouted = true;
                    
        } else {
            System.out.println("=========================");
            System.out.println("You have already scouted the area!");
            System.out.println("=========================");
        }
    }
    
    // ends turn 
    public void endTurn(){
        System.out.println("=========================");
        System.out.println("END TURN");
        System.out.println("=========================");
        move();
        this.hasScouted = false;
        player.endTurn();
    }
    
    // getter and setters
    public int getCurrentPosition() {
        return this.currentPosition;
    }
    
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
    
    public boolean getHasScouted(){
        return this.hasScouted;
    }
    
    public void setHasScouted(boolean hasScouted){
        this.hasScouted = hasScouted;
    }
    
}
