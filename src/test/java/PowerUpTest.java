import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PowerUpTest {

    @Mock
    private Board mockBoard;

    @Mock
    private Graphics mockGraphics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBoard.getSpriteSize()).thenReturn(20);
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
    }

    @Test
    void testConstructor() {
        PowerUp powerUp = new PowerUp(mockBoard);
        assertNotNull(powerUp);
        assertTrue(powerUp.isActive());
    }

    @Test
    void testIsActiveAfterConstruction() {
        PowerUp powerUp = new PowerUp(mockBoard);
        assertTrue(powerUp.isActive());
    }

    @Test
    void testCheckCollisionWhenNotActive() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);
        PowerUp powerUp = new PowerUp(mockBoard);
        // Force powerUp to be inactive by setting isWall to always return true
        // This prevents spawn from setting active to true
        powerUp = new PowerUp(mockBoard);
        
        // Use reflection to set active to false
        try {
            java.lang.reflect.Field activeField = PowerUp.class.getDeclaredField("active");
            activeField.setAccessible(true);
            activeField.set(powerUp, false);
            
            boolean result = powerUp.checkCollision(10, 10, 20);
            assertFalse(result);
        } catch (Exception e) {
            fail("Could not set active field: " + e.getMessage());
        }
    }

    @Test
    void testCheckCollisionWhenActive() {
        PowerUp powerUp = new PowerUp(mockBoard);
        
        // Get powerUp position using reflection
        try {
            java.lang.reflect.Field xField = PowerUp.class.getDeclaredField("x");
            java.lang.reflect.Field yField = PowerUp.class.getDeclaredField("y");
            xField.setAccessible(true);
            yField.setAccessible(true);
            
            // Set known position
            xField.set(powerUp, 100);
            yField.set(powerUp, 100);
            
            // Test collision
            boolean result = powerUp.checkCollision(110, 110, 20);
            assertTrue(result);
            assertFalse(powerUp.isActive());
        } catch (Exception e) {
            fail("Could not access fields: " + e.getMessage());
        }
    }

    @Test
    void testCheckCollisionNoOverlap() {
        PowerUp powerUp = new PowerUp(mockBoard);
        
        try {
            java.lang.reflect.Field xField = PowerUp.class.getDeclaredField("x");
            java.lang.reflect.Field yField = PowerUp.class.getDeclaredField("y");
            xField.setAccessible(true);
            yField.setAccessible(true);
            
            xField.set(powerUp, 100);
            yField.set(powerUp, 100);
            
            boolean result = powerUp.checkCollision(200, 200, 20);
            assertFalse(result);
            assertTrue(powerUp.isActive());
        } catch (Exception e) {
            fail("Could not access fields: " + e.getMessage());
        }
    }

    @Test
    void testDraw() {
        PowerUp powerUp = new PowerUp(mockBoard);
        
        // Should not throw exception
        assertDoesNotThrow(() -> powerUp.draw(mockGraphics));
        
        // Verify color is set
        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
    }

    @Test
    void testDrawWhenInactive() {
        PowerUp powerUp = new PowerUp(mockBoard);
        
        try {
            java.lang.reflect.Field activeField = PowerUp.class.getDeclaredField("active");
            activeField.setAccessible(true);
            activeField.set(powerUp, false);
            
            powerUp.draw(mockGraphics);
            
            // When inactive, should not draw
            verify(mockGraphics, never()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
        } catch (Exception e) {
            fail("Could not set active field: " + e.getMessage());
        }
    }

    @Test
    void testReset() {
        PowerUp powerUp = new PowerUp(mockBoard);
        
        try {
            java.lang.reflect.Field activeField = PowerUp.class.getDeclaredField("active");
            activeField.setAccessible(true);
            activeField.set(powerUp, false);
            
            powerUp.reset();
            assertTrue(powerUp.isActive());
        } catch (Exception e) {
            fail("Could not set active field: " + e.getMessage());
        }
    }

    @Test
    void testSpawnFindsValidPosition() {
        PowerUp powerUp = new PowerUp(mockBoard);
        assertTrue(powerUp.isActive());
    }

    @Test
    void testSpawnWithAllWalls() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);
        PowerUp powerUp = new PowerUp(mockBoard);
        
        // Should still create but may not be active if all positions are walls
        assertNotNull(powerUp);
    }
}
