package desertrungame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patri
 */
public class PlayerTest {
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of endTurn method, of class Player.
     */
    @Test
    public void testEndTurn() {
        Player player = new Player();
        player.endTurn();

        assertTrue(player.getHealth() == 20);
        assertTrue(player.getHunger() <= 25);
        assertTrue(player.getThirst() <= 50);
        assertTrue(player.getCurrency() >= 0);
    }

    /**
     * Test of increaseHunger method, of class Player.
     */
    @Test
    public void testIncreaseHunger() {
        Player player = new Player();
        player.increaseHunger(5);

        assertEquals(30, player.getHunger());
    }
    /**
     * Test of decreaseHunger method, of class Player.
     */
    @Test
    public void testDecreaseHunger() {
        Player player = new Player();
        player.decreaseHunger(5);

        assertEquals(20, player.getHunger());
        assertEquals(20, player.getHealth());
    }

    /**
     * Test of increaseThirst method, of class Player.
     */
    @Test
    public void testIncreaseThirst() {
        Player player = new Player();
        player.increaseThirst(5);

        assertEquals(55, player.getThirst());
    }

    /**
     * Test of decreaseThirst method, of class Player.
     */
    @Test
    public void testDecreaseThirst() {
        Player player = new Player();
        player.decreaseThirst(5);

        assertEquals(45, player.getThirst());
        assertEquals(20, player.getHealth());
    }

    /**
     * Test of isDead method, of class Player.
     */
    @Test
    public void testIsDead() {
        Player player = new Player();
        assertFalse(player.isDead());

        player.decreaseHealth(20);
        assertTrue(player.isDead());
    }

    /**
     * Test of decreaseHealth method, of class Player.
     */
    @Test
    public void testDecreaseHealth() {
        Player player = new Player();
        player.decreaseHealth(5);

        assertEquals(15, player.getHealth());
    }

    /**
     * Test of increaseCurrency method, of class Player.
     */
    @Test
    public void testIncreaseCurrency() {
        Player player = new Player();
        player.increaseCurrency(5);

        assertTrue(player.getCurrency() >= 5);
    }

    /**
     * Test of decreaseCurrency method, of class Player.
     */
    @Test
    public void testDecreaseCurrency() {
        Player player = new Player();
        player.decreaseCurrency(5);

        assertEquals(-5, player.getCurrency());
    }

    /**
     * Test of getHealth method, of class Player.
     */
    @Test
    public void testGetHealth() {
        Player player = new Player();
        assertEquals(20, player.getHealth());
    }

    /**
     * Test of setHealth method, of class Player.
     */
    @Test
    public void testSetHealth() {
        Player player = new Player();
        player.setHealth(10);
        assertEquals(10, player.getHealth());
    }

    /**
     * Test of getHunger method, of class Player.
     */
    @Test
    public void testGetHunger() {
        Player player = new Player();
        assertEquals(25, player.getHunger());
    }

    /**
     * Test of setHunger method, of class Player.
     */
    @Test
    public void testSetHunger() {
        Player player = new Player();
        player.setHunger(15);
        assertEquals(15, player.getHunger());
    }
    /**
     * Test of getThirst method, of class Player.
     */
    @Test
    public void testGetThirst() {
        Player player = new Player();
        assertEquals(50, player.getThirst());
    }

    /**
     * Test of setThirst method, of class Player.
     */
    @Test
    public void testSetThirst() {
        Player player = new Player();
        player.setThirst(40);
        assertEquals(40, player.getThirst());
    }

    /**
     * Test of getCurrency method, of class Player.
     */
    @Test
    public void testGetCurrency() {
        Player player = new Player();
        assertEquals(0, player.getCurrency());
    }

    /**
     * Test of setCurrency method, of class Player.
     */
    @Test
    public void testSetCurrency() {
        Player player = new Player();
        player.setCurrency(10);
        assertEquals(10, player.getCurrency());
    }
    
}
