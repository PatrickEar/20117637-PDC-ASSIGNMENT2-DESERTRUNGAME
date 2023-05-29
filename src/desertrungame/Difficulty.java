package desertrungame;

/**
 *
 * @author Patri
 */
public enum Difficulty {
    // difficultys the player can choose from, adjusts event chance, price, and spaces to win
    EASY(2.0, 0.5, 100),
    NORMAL(1.0, 1.0, 150),
    HARD(0.5, 2.0, 250);

    private double eventMultiplier;
    private double priceMultiplier;
    private int distanceToWin;

    private Difficulty(double eventMultiplier, double priceMultiplier, int distanceToWin) {
        this.eventMultiplier = eventMultiplier;
        this.priceMultiplier = priceMultiplier;
        this.distanceToWin = distanceToWin;
    }

    // getter and setters
    public double getEventMultiplier() {
        return this.eventMultiplier;
    }

    public double getPriceMultiplier() {
        return this.priceMultiplier;
    }

    public int getDistanceToWin() {
        return this.distanceToWin;
    }

    public void setEventMultiplier(double eventMultiplier) {
        this.eventMultiplier = eventMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public void setDistanceToWin(int distanceToWin) {
        this.distanceToWin = distanceToWin;
    }
}
