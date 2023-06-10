package desertrungame;

/**
 *
 * @author Patri
 */
import java.util.Random;

public class Map {

    private static final int STARTING_POSITION = 0; // the player's starting position
    
    private Player player; // The player
    private Menu menu; // The menu
    private boolean hasScouted; // Checks if player has used the scout option
    
    private double eventMultiplier; // Adjusts the chances for events based on difficulty
    private int requiredPosition; // The required position to win based on difficulty
    
    private int currentPosition; // The player's current position

    private Random random; // A random number generator
    
    // Events for scouting option
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

    // Moves player in game
    public void move() {
        int spacesMoved = this.random.nextInt(1,5); // move 1-4 spaces
        this.currentPosition += spacesMoved; 
    }
    
    // Checks if player has won
    public boolean isWin() {
        return this.currentPosition >= this.requiredPosition;
    }
    
    // Ends turn 
    public void endTurn(){
        move();
        this.hasScouted = false;
        player.endTurn();
    }
    
    // Getter and Setters
    public int getCurrentPosition() {
        return this.currentPosition;
    }
    
    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getRequiredPosition() {
        return requiredPosition;
    }

    public void setRequiredPosition(int requiredPosition) {
        this.requiredPosition = requiredPosition;
    }
    
    public boolean getHasScouted(){
        return this.hasScouted;
    }
    
    public void setHasScouted(boolean hasScouted){
        this.hasScouted = hasScouted;
    }

    public double getEventMultiplier() {
        return eventMultiplier;
    }

    public void setEventMultiplier(double eventMultiplier) {
        this.eventMultiplier = eventMultiplier;
    }

    public Event getOasis() {
        return oasis;
    }

    public void setOasis(Event oasis) {
        this.oasis = oasis;
    }

    public Event getFoodMarket() {
        return foodMarket;
    }

    public void setFoodMarket(Event foodMarket) {
        this.foodMarket = foodMarket;
    }

    public Event getFindCurrency() {
        return findCurrency;
    }

    public void setFindCurrency(Event findCurrency) {
        this.findCurrency = findCurrency;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
