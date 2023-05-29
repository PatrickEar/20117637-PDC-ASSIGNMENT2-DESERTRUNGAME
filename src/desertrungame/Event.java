package desertrungame;

/**
 *
 * @author Patri
 */

// generates a event that the player can encounter
public class Event {
    private String name;
    private String description;

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
