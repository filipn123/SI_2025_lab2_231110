import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SILab2Test {
    
    @Test
    public void testEveryStatement() {
        // Null list
        try {
            SILab2.checkCart(null, "1234567812345678");
            fail("Expecting RuntimeException for null list");
        } catch (RuntimeException e) {
            assertEquals("allItems list can't be null!", e.getMessage());
        }
        
        // Invalid name
        try {
            Item item = new Item(null, 1, 100, 0);
            SILab2.checkCart(List.of(item), "1234567812345678");
            fail("Expecting RuntimeException for invalid item name");
        } catch (RuntimeException e) {
            assertEquals("Invalid item!", e.getMessage());
        }
        
        // Discounted item
        Item item1 = new Item("Test1", 11, 400, 0.1);
        double expected1 = -30 + 400 * 0.9 * 11;
        assertEquals(expected1, SILab2.checkCart(List.of(item1), "1234567812345678"), 0.01);
        
        // Item without discount
        Item item2 = new Item("Test2", 2, 100, 0);
        double expected2 = 200;
        assertEquals(expected2, SILab2.checkCart(List.of(item2), "1234567812345678"), 0.01);
        
        // Invalid card number
        try {
            SILab2.checkCart(List.of(item2), "123");
            fail("Expecting RuntimeException for invalid card number");
        } catch (RuntimeException e) {
            assertEquals("Invalid card number!", e.getMessage());
        }
        
        // Invalid character in card number
        try {
            SILab2.checkCart(List.of(item2), "12345678901234AB");
            fail("Expecting RuntimeException for invalid character in card number");
        } catch (RuntimeException e) {
            assertEquals("Invalid character in card number!", e.getMessage());
        }
    }
    
    @Test
    public void testMultipleCondition() {
        // false || false || false
        Item item1 = new Item("item1", 1, 100, 0);
        double expected1 = 100;
        assertEquals(expected1, SILab2.checkCart(List.of(item1), "1234567812345678"), 0.01);
        
        // false || false || true
        Item item2 = new Item("item2", 11, 100, 0); // -30 + 100*11
        double expected2 = -30 + 1100;
        assertEquals(expected2, SILab2.checkCart(List.of(item2), "1234567812345678"), 0.01);
        
        // false || true || false
        Item item3 = new Item("item3", 1, 100, 0.1); // -30 + 100*0.9*1
        double expected3 = -30 + 90;
        assertEquals(expected3, SILab2.checkCart(List.of(item3), "1234567812345678"), 0.01);
        
        // false || true || true
        Item item4 = new Item("item4", 11, 100, 0.1); // -30 + 100*0.9*11
        double expected4 = -30 + 990;
        assertEquals(expected4, SILab2.checkCart(List.of(item4), "1234567812345678"), 0.01);
        
        // true || false || false
        Item item5 = new Item("item5", 1, 301, 0); // -30 + 301
        double expected5 = -30 + 301;
        assertEquals(expected5, SILab2.checkCart(List.of(item5), "1234567812345678"), 0.01);
        
        // true || false || true
        Item item6 = new Item("item6", 11, 301, 0); // -30 + 301*11
        double expected6 = -30 + 3311;
        assertEquals(expected6, SILab2.checkCart(List.of(item6), "1234567812345678"), 0.01);
        
        // true || true || false
        Item item7 = new Item("item7", 1, 301, 0.1); // -30 + 301*0.9
        double expected7 = -30 + 270.9;
        assertEquals(expected7, SILab2.checkCart(List.of(item7), "1234567812345678"), 0.01);
        
        // true || true || true
        Item item8 = new Item("item8", 11, 301, 0.1); // -30 + 301*0.9*11
        double expected8 = -30 + 301 * 0.9 * 11;
        assertEquals(expected8, SILab2.checkCart(List.of(item8), "1234567812345678"), 0.01);
    }
}
