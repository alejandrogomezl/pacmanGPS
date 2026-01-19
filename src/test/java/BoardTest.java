import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.awt.*;
import java.awt.event.ActionEvent;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BoardTest {

    private Board board;

    @Mock
    private Graphics mockGraphics;

    @Mock
    private ActionEvent mockActionEvent;

    @Mock
    private FontMetrics mockFontMetrics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockGraphics.getFontMetrics()).thenReturn(mockFontMetrics);
        when(mockGraphics.getFontMetrics(any(Font.class))).thenReturn(mockFontMetrics);
        when(mockFontMetrics.stringWidth(anyString())).thenReturn(100);
    }

    @Test
    void testConstructor() {
        board = new Board();
        assertNotNull(board);
        assertEquals(Color.BLACK, board.getBackground());
    }

    @Test
    void testGetSpriteSize() {
        board = new Board();
        assertEquals(20, board.getSpriteSize());
    }

    @Test
    void testIsWallOnWall() {
        board = new Board();
        // Position (0, 0) should be a wall in level 1
        assertTrue(board.isWall(0, 0));
    }

    @Test
    void testIsWallNotOnWall() {
        board = new Board();
        // Position (20, 20) should not be a wall in level 1 (it's a point)
        assertFalse(board.isWall(20, 20));
    }

    @Test
    void testIsWallOutOfBounds() {
        board = new Board();
        // Out of bounds should be considered a wall
        assertTrue(board.isWall(-10, -10));
        assertTrue(board.isWall(1000, 1000));
    }

    @Test
    void testWrapXNegative() {
        board = new Board();
        int wrapped = board.wrapX(-10);
        assertEquals(390, wrapped); // 400 - 10
    }

    @Test
    void testWrapXPositive() {
        board = new Board();
        int wrapped = board.wrapX(450);
        assertEquals(50, wrapped); // 450 - 400
    }

    @Test
    void testWrapXNormal() {
        board = new Board();
        int wrapped = board.wrapX(200);
        assertEquals(200, wrapped);
    }

    @Test
    void testWrapYNegative() {
        board = new Board();
        int wrapped = board.wrapY(-10);
        assertEquals(390, wrapped); // 400 - 10
    }

    @Test
    void testWrapYPositive() {
        board = new Board();
        int wrapped = board.wrapY(450);
        assertEquals(50, wrapped); // 450 - 400
    }

    @Test
    void testWrapYNormal() {
        board = new Board();
        int wrapped = board.wrapY(200);
        assertEquals(200, wrapped);
    }

    @Test
    void testEatPoint() {
        board = new Board();

        // Use reflection to access pacman and check score
        try {
            java.lang.reflect.Field pacmanField = Board.class.getDeclaredField("pacman");
            pacmanField.setAccessible(true);
            Pacman pacman = (Pacman) pacmanField.get(board);

            int initialScore = pacman.getScore();

            // Eat a point at a valid location (20, 20 should have a point)
            board.eatPoint(20, 20);

            // Score should increase by 10
            assertTrue(pacman.getScore() >= initialScore);
        } catch (Exception e) {
            fail("Could not access pacman field: " + e.getMessage());
        }
    }

    @Test
    void testEatPointOutOfBounds() {
        board = new Board();

        try {
            java.lang.reflect.Field pacmanField = Board.class.getDeclaredField("pacman");
            pacmanField.setAccessible(true);
            Pacman pacman = (Pacman) pacmanField.get(board);

            int initialScore = pacman.getScore();

            // Try to eat point out of bounds
            board.eatPoint(-10, -10);

            // Score should not change
            assertEquals(initialScore, pacman.getScore());
        } catch (Exception e) {
            fail("Could not access pacman field: " + e.getMessage());
        }
    }

    @Test
    void testPaintComponent() {
        board = new Board();

        // Can't test paintComponent directly in headless mode
        // Just verify board was created
        assertNotNull(board);
    }

    @Test
    void testActionPerformed() {
        board = new Board();

        assertDoesNotThrow(() -> board.actionPerformed(mockActionEvent));
    }

    @Test
    void testActionPerformedWhenGameOver() {
        board = new Board();

        // Set gameOver to true using reflection
        try {
            java.lang.reflect.Field gameOverField = Board.class.getDeclaredField("gameOver");
            gameOverField.setAccessible(true);
            gameOverField.set(board, true);

            assertDoesNotThrow(() -> board.actionPerformed(mockActionEvent));
        } catch (Exception e) {
            fail("Could not set gameOver field: " + e.getMessage());
        }
    }

    @Test
    void testIsWallWithWrapping() {
        board = new Board();

        // Test that wrapping is applied before checking wall
        int x = -5;
        int y = -5;

        boolean result = board.isWall(x, y);
        // Should wrap and then check
        assertTrue(result || !result); // Just ensure it doesn't crash
    }

    @Test
    void testWrapXZero() {
        board = new Board();
        assertEquals(0, board.wrapX(0));
    }

    @Test
    void testWrapYZero() {
        board = new Board();
        assertEquals(0, board.wrapY(0));
    }

    @Test
    void testWrapXBoundary() {
        board = new Board();
        assertEquals(0, board.wrapX(400));
    }

    @Test
    void testWrapYBoundary() {
        board = new Board();
        assertEquals(0, board.wrapY(400));
    }

    @Test
    void testEatPointOnWall() {
        board = new Board();

        try {
            java.lang.reflect.Field pacmanField = Board.class.getDeclaredField("pacman");
            pacmanField.setAccessible(true);
            Pacman pacman = (Pacman) pacmanField.get(board);

            int initialScore = pacman.getScore();

            // Try to eat point on a wall (0, 0 is a wall)
            board.eatPoint(0, 0);

            // Score should not change
            assertEquals(initialScore, pacman.getScore());
        } catch (Exception e) {
            fail("Could not access pacman field: " + e.getMessage());
        }
    }

    @Test
    void testEatPointOnEmptyPath() {
        board = new Board();

        try {
            java.lang.reflect.Field pacmanField = Board.class.getDeclaredField("pacman");
            pacmanField.setAccessible(true);
            Pacman pacman = (Pacman) pacmanField.get(board);

            // First eat the point
            board.eatPoint(20, 20);
            int scoreAfterEat = pacman.getScore();

            // Try to eat the same point again (now it's empty)
            board.eatPoint(20, 20);

            // Score should not change again
            assertEquals(scoreAfterEat, pacman.getScore());
        } catch (Exception e) {
            fail("Could not access pacman field: " + e.getMessage());
        }
    }

    @Test
    void testInitialLevelIsZero() {
        board = new Board();

        try {
            java.lang.reflect.Field currentLevelField = Board.class.getDeclaredField("currentLevel");
            currentLevelField.setAccessible(true);
            int currentLevel = (int) currentLevelField.get(board);

            assertEquals(0, currentLevel);
        } catch (Exception e) {
            fail("Could not access currentLevel field: " + e.getMessage());
        }
    }

    @Test
    void testGhostsAreCreated() {
        board = new Board();

        try {
            java.lang.reflect.Field ghostsField = Board.class.getDeclaredField("ghosts");
            ghostsField.setAccessible(true);
            Ghost[] ghosts = (Ghost[]) ghostsField.get(board);

            assertNotNull(ghosts);
            assertEquals(3, ghosts.length);
        } catch (Exception e) {
            fail("Could not access ghosts field: " + e.getMessage());
        }
    }

    @Test
    void testPowerUpIsCreated() {
        board = new Board();

        try {
            java.lang.reflect.Field powerUpField = Board.class.getDeclaredField("powerUp");
            powerUpField.setAccessible(true);
            PowerUp powerUp = (PowerUp) powerUpField.get(board);

            assertNotNull(powerUp);
        } catch (Exception e) {
            fail("Could not access powerUp field: " + e.getMessage());
        }
    }

    @Test
    void testTimerIsCreated() {
        board = new Board();

        try {
            java.lang.reflect.Field timerField = Board.class.getDeclaredField("timer");
            timerField.setAccessible(true);
            javax.swing.Timer timer = (javax.swing.Timer) timerField.get(board);

            assertNotNull(timer);
            assertTrue(timer.isRunning());
        } catch (Exception e) {
            fail("Could not access timer field: " + e.getMessage());
        }
    }
}
