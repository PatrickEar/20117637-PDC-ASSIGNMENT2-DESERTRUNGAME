/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
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
public class MapTest {

    public MapTest() {
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
     * Test of move method, of class Map.
     */
    @Test
    public void testMove() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        int initialPosition = instance.getCurrentPosition();
        instance.move();
        int newPosition = instance.getCurrentPosition();

        assertTrue(newPosition >= initialPosition);
        assertTrue(newPosition <= initialPosition + 4);
    }

    /**
     * Test of isWin method, of class Map.
     */
    @Test
    public void testIsWin() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        int requiredPosition = instance.getRequiredPosition() + 1;
        instance.setCurrentPosition(requiredPosition);
        assertTrue(instance.isWin());

        int notRequiredPosition = instance.getRequiredPosition() - 1;
        instance.setCurrentPosition(notRequiredPosition);
        assertFalse(instance.isWin());
    }

    /**
     * Test of endTurn method, of class Map.
     */
    @Test
    public void testEndTurn() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        int initialPosition = instance.getCurrentPosition();
        instance.endTurn();

        int newPosition = instance.getCurrentPosition();
        assertNotEquals(initialPosition, newPosition);
        assertTrue(newPosition >= initialPosition);
        assertTrue(newPosition <= initialPosition + 4);

        assertFalse(instance.getHasScouted());
    }

    /**
     * Test of getCurrentPosition method, of class Map.
     */
    @Test
    public void testGetCurrentPosition() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        int expResult = 0;
        int result = instance.getCurrentPosition();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCurrentPosition method, of class Map.
     */
    @Test
    public void testSetCurrentPosition() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        int currentPosition = 0;
        instance.setCurrentPosition(currentPosition);
        assertEquals(currentPosition, instance.getCurrentPosition());
    }

    /**
     * Test of getHasScouted method, of class Map.
     */
    @Test
    public void testGetHasScouted() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        assertFalse(instance.getHasScouted());
    }

    /**
     * Test of setHasScouted method, of class Map.
     */
    @Test
    public void testSetHasScouted() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        boolean hasScouted = true;
        instance.setHasScouted(hasScouted);
        assertTrue(instance.getHasScouted());
    }

    /**
     * Test of getEventMultiplier method, of class Map.
     */
    @Test
    public void testGetEventMultiplier() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        double eventMultiplier = 1.5;
        instance.setEventMultiplier(eventMultiplier);
        assertEquals(eventMultiplier, instance.getEventMultiplier(), 0.01);
    }

    /**
     * Test of setEventMultiplier method, of class Map.
     */
    @Test
    public void testSetEventMultiplier() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        double eventMultiplier = 1.5;
        instance.setEventMultiplier(eventMultiplier);
        assertEquals(eventMultiplier, instance.getEventMultiplier(), 0.01);
    }

    /**
     * Test of getOasis method, of class Map.
     */
    @Test
    public void testGetOasis() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        Event oasis = new Event("Oasis", "You found an oasis! You can eat and drink for free.");
        instance.setOasis(oasis);
        assertEquals(oasis, instance.getOasis());
    }

    /**
     * Test of setOasis method, of class Map.
     */
    @Test
    public void testSetOasis() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        Event oasis = new Event("Oasis", "You found an oasis! You can eat and drink for free.");
        instance.setOasis(oasis);
        assertEquals(oasis, instance.getOasis());
    }

    /**
     * Test of getFoodMarket method, of class Map.
     */
    @Test
    public void testGetFoodMarket() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        Event foodMarket = new Event("Food Market", "You found a food market! You can buy some food and drinks.");
        instance.setFoodMarket(foodMarket);
        assertEquals(foodMarket, instance.getFoodMarket());
    }

    /**
     * Test of setFoodMarket method, of class Map.
     */
    @Test
    public void testSetFoodMarket() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        Event foodMarket = new Event("Food Market", "You found a food market! You can buy some food and drinks.");
        instance.setFoodMarket(foodMarket);
        assertEquals(foodMarket, instance.getFoodMarket());
    }

    /**
     * Test of getFindCurrency method, of class Map.
     */
    @Test
    public void testGetFindCurrency() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        Event findCurrency = new Event("Find Currency", "You found some coins on the ground!");
        instance.setFindCurrency(findCurrency);
        assertEquals(findCurrency, instance.getFindCurrency());
    }

    /**
     * Test of setFindCurrency method, of class Map.
     */
    @Test
    public void testSetFindCurrency() {
        Map instance = new Map(Difficulty.EASY, new Player(), new Inventory(new Player()));
        Event findCurrency = new Event("Find Currency", "You found some coins on the ground!");
        instance.setFindCurrency(findCurrency);
        assertEquals(findCurrency, instance.getFindCurrency());
    }
}
