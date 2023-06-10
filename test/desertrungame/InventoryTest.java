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

import java.util.List;

/**
 *
 * @author Patri
 */
public class InventoryTest {

    public InventoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    private Inventory inventory;
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        inventory = new Inventory(player);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addItem method, of class Inventory.
     */
    @Test
    public void testAddItem() {
        CactusMelon cactusMelon = new CactusMelon(10);
        inventory.addItem(cactusMelon);
        
        List<Consumable> items = inventory.getItems();
        assertEquals(1, items.size());
        assertEquals(cactusMelon, items.get(0));
    }

    /**
     * Test of consumeItem method, of class Inventory.
     */
    @Test
    public void testConsumeItem() {
        CactusMelon cactusMelon = new CactusMelon(10);
        inventory.addItem(cactusMelon);
        
        assertTrue(inventory.consumeItem(0));
        assertEquals(0, inventory.getSize());
        assertTrue(inventory.getItems().isEmpty());
        
        assertEquals(25, player.getHunger());
        assertEquals(50, player.getThirst());
    }

    /**
     * Test of getItems method, of class Inventory.
     */
    @Test
    public void testGetItems() {
        CactusMelon cactusMelon1 = new CactusMelon(10);
        CactusMelon cactusMelon2 = new CactusMelon(5);
        
        inventory.addItem(cactusMelon1);
        inventory.addItem(cactusMelon2);
        
        List<Consumable> items = inventory.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(cactusMelon1));
        assertTrue(items.contains(cactusMelon2));
    }

    /**
     * Test of getSize method, of class Inventory.
     */
    @Test
    public void testGetSize() {
        CactusMelon cactusMelon1 = new CactusMelon(10);
        CactusMelon cactusMelon2 = new CactusMelon(5);
        
        inventory.addItem(cactusMelon1);
        inventory.addItem(cactusMelon2);
        
        assertEquals(2, inventory.getSize());
    }

    /**
     * Test of clearInventory method, of class Inventory.
     */
    @Test
    public void testClearInventory() {
        CactusMelon cactusMelon1 = new CactusMelon(10);
        CactusMelon cactusMelon2 = new CactusMelon(5);
        
        inventory.addItem(cactusMelon1);
        inventory.addItem(cactusMelon2);
        
        inventory.clearInventory();
        
        assertEquals(0, inventory.getSize());
        assertTrue(inventory.getItems().isEmpty());
    }

}