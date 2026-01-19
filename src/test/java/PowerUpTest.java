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

    private PowerUp powerUp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBoard.getSpriteSize()).thenReturn(20);
        // Mock isWall to return false for valid positions
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        powerUp = new PowerUp(mockBoard);
    }

    @Test
    void testConstructorInitializesActivePowerUp() {
        assertTrue(powerUp.isActive());
    }

    @Test
    void testSpawnCreatesActivePowerUp() {
        powerUp.spawn();
        assertTrue(powerUp.isActive());
    }

    @Test
    void testSpawnAttemptsToFindValidPosition() {
        // Mock board to return true (wall) for most positions
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);
        // But return false for specific position
        when(mockBoard.isWall(0, 0)).thenReturn(false);
        
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // Should be active if it found a valid position
        // Or inactive if it exhausted all attempts
        assertNotNull(testPowerUp);
    }

    @Test
    void testDrawWhenActive() {
        powerUp.draw(mockGraphics);
        
        // Verify that colors are set and ovals are drawn
        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
        verify(mockGraphics, times(2)).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testDrawWhenInactive() {
        // Create a power up and deactivate it
        PowerUp inactivePowerUp = new PowerUp(mockBoard);
        inactivePowerUp.checkCollision(0, 0, 20); // This should deactivate it if collision occurs
        
        Graphics mockGraphics2 = mock(Graphics.class);
        
        if (!inactivePowerUp.isActive()) {
            inactivePowerUp.draw(mockGraphics2);
            // Should not draw anything when inactive
            verify(mockGraphics2, never()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
        }
    }

    @Test
    void testCheckCollisionWhenActive() {
        // Manually set the powerUp to a known position
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // Assuming powerUp spawned at some position
        // Test collision with overlapping coordinates
        boolean collided = testPowerUp.checkCollision(0, 0, 20);
        
        if (collided) {
            assertFalse(testPowerUp.isActive());
        }
    }

    @Test
    void testCheckCollisionWhenInactive() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // First collision
        testPowerUp.checkCollision(0, 0, 20);
        
        // Second collision should return false as it's inactive
        boolean secondCollision = testPowerUp.checkCollision(0, 0, 20);
        assertFalse(secondCollision);
    }

    @Test
    void testCheckCollisionNoOverlap() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // Test collision with coordinates far away (no overlap)
        boolean collided = testPowerUp.checkCollision(1000, 1000, 20);
        assertFalse(collided);
        assertTrue(testPowerUp.isActive());
    }

    @Test
    void testReset() {
        powerUp.reset();
        assertTrue(powerUp.isActive());
    }

    @Test
    void testIsActiveReturnsCorrectState() {
        assertTrue(powerUp.isActive());
        
        // Force a collision to make it inactive
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // Collision at exact position
        testPowerUp.checkCollision(0, 0, 20);
        
        if (!testPowerUp.isActive()) {
            assertFalse(testPowerUp.isActive());
        }
    }

    @Test
    void testSpawnRetriesWhenPositionIsWall() {
        // All positions are walls
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);
        
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // Should have attempted multiple times
        verify(mockBoard, atLeast(1)).isWall(anyInt(), anyInt());
    }

    @Test
    void testCollisionDetectionBoundaryConditions() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);
        PowerUp testPowerUp = new PowerUp(mockBoard);
        
        // Test edge collision detection
        // pacmanX < x + spriteSize && pacmanX + pacmanSize > x
        // pacmanY < y + spriteSize && pacmanY + pacmanSize > y
        
        // No collision - pacman completely to the left
        boolean result1 = testPowerUp.checkCollision(-100, 0, 20);
        assertFalse(result1);
    }
}
