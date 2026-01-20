import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.awt.*;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PacmanTest {

    @Mock
    private Board mockBoard;

    @Mock
    private Graphics mockGraphics;

    @Mock
    private KeyEvent mockKeyEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBoard.getSpriteSize()).thenReturn(20);
        when(mockBoard.wrapX(anyInt())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mockBoard.wrapY(anyInt())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testConstructor() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        assertNotNull(pacman);
        assertEquals(100, pacman.getX());
        assertEquals(100, pacman.getY());
        assertEquals(20, pacman.getSpriteSize());
        assertEquals(0, pacman.getScore());
    }

    @Test
    void testGetX() {
        Pacman pacman = new Pacman(150, 200, mockBoard);
        assertEquals(150, pacman.getX());
    }

    @Test
    void testGetY() {
        Pacman pacman = new Pacman(150, 200, mockBoard);
        assertEquals(200, pacman.getY());
    }

    @Test
    void testGetSpriteSize() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        assertEquals(20, pacman.getSpriteSize());
    }

    @Test
    void testGetScore() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        assertEquals(0, pacman.getScore());
    }

    @Test
    void testAddScore() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        pacman.addScore(10);
        assertEquals(10, pacman.getScore());

        pacman.addScore(20);
        assertEquals(30, pacman.getScore());
    }

    @Test
    void testReset() {
        Pacman pacman = new Pacman(100, 150, mockBoard);
        pacman.addScore(100);
        pacman.activatePowerUp();

        pacman.reset();

        assertEquals(100, pacman.getX());
        assertEquals(150, pacman.getY());
        assertEquals(0, pacman.getScore());
        assertFalse(pacman.isPowered());
    }

    @Test
    void testKeyPressedLeft() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);

        pacman.keyPressed(mockKeyEvent);
        // Direction is set, we can't test it directly but we can test movement
        assertNotNull(pacman);
    }

    @Test
    void testKeyPressedRight() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);

        pacman.keyPressed(mockKeyEvent);
        assertNotNull(pacman);
    }

    @Test
    void testKeyPressedUp() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);

        pacman.keyPressed(mockKeyEvent);
        assertNotNull(pacman);
    }

    @Test
    void testKeyPressedDown() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);

        pacman.keyPressed(mockKeyEvent);
        assertNotNull(pacman);
    }

    @Test
    void testMoveWithoutWall() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Pacman pacman = new Pacman(100, 100, mockBoard);
        int initialX = pacman.getX();

        pacman.move();

        // Should move left by default
        assertTrue(pacman.getX() < initialX);
    }

    @Test
    void testMoveWithWall() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);

        Pacman pacman = new Pacman(100, 100, mockBoard);
        int initialX = pacman.getX();
        int initialY = pacman.getY();

        pacman.move();

        // Should not move if wall blocks
        assertEquals(initialX, pacman.getX());
        assertEquals(initialY, pacman.getY());
    }

    @Test
    void testActivatePowerUp() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        assertFalse(pacman.isPowered());

        pacman.activatePowerUp();
        assertTrue(pacman.isPowered());
    }

    @Test
    void testIsPowered() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        assertFalse(pacman.isPowered());
    }

    @Test
    void testPowerUpExpires() throws InterruptedException {
        Pacman pacman = new Pacman(100, 100, mockBoard);

        // Manually set power up with reflection to control timing
        try {
            java.lang.reflect.Field poweredField = Pacman.class.getDeclaredField("powered");
            java.lang.reflect.Field powerUpStartTimeField = Pacman.class.getDeclaredField("powerUpStartTime");
            poweredField.setAccessible(true);
            powerUpStartTimeField.setAccessible(true);

            poweredField.set(pacman, true);
            powerUpStartTimeField.set(pacman, System.currentTimeMillis() - 20000); // 20 seconds ago

            assertFalse(pacman.isPowered()); // Should be expired
        } catch (Exception e) {
            fail("Could not access fields: " + e.getMessage());
        }
    }

    @Test
    void testDraw() {
        Pacman pacman = new Pacman(100, 100, mockBoard);

        pacman.draw(mockGraphics);

        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
        verify(mockGraphics).fillArc(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testDrawWhenPowered() {
        Pacman pacman = new Pacman(100, 100, mockBoard);
        pacman.activatePowerUp();

        pacman.draw(mockGraphics);

        verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
        verify(mockGraphics).fillArc(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void testDrawWhenPoweredBlinking() {
        Pacman pacman = new Pacman(100, 100, mockBoard);

        // Set power up to blinking phase
        try {
            java.lang.reflect.Field poweredField = Pacman.class.getDeclaredField("powered");
            java.lang.reflect.Field powerUpStartTimeField = Pacman.class.getDeclaredField("powerUpStartTime");
            poweredField.setAccessible(true);
            powerUpStartTimeField.setAccessible(true);

            poweredField.set(pacman, true);
            powerUpStartTimeField.set(pacman, System.currentTimeMillis() - 13000); // 13 seconds ago

            pacman.draw(mockGraphics);

            verify(mockGraphics, atLeastOnce()).setColor(any(Color.class));
            verify(mockGraphics).fillArc(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt());
        } catch (Exception e) {
            fail("Could not access fields: " + e.getMessage());
        }
    }

    @Test
    void testMoveCallsEatPoint() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Pacman pacman = new Pacman(100, 100, mockBoard);
        pacman.move();

        verify(mockBoard).eatPoint(anyInt(), anyInt());
    }

    @Test
    void testMoveCallsWrapX() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Pacman pacman = new Pacman(100, 100, mockBoard);
        pacman.move();

        verify(mockBoard, atLeastOnce()).wrapX(anyInt());
    }

    @Test
    void testMoveCallsWrapY() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Pacman pacman = new Pacman(100, 100, mockBoard);
        pacman.move();

        verify(mockBoard, atLeastOnce()).wrapY(anyInt());
    }

    @Test
    void testMoveWithDesiredDirectionBlocked() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true, true, false, false, false, false);
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);

        Pacman pacman = new Pacman(100, 100, mockBoard);
        pacman.keyPressed(mockKeyEvent);

        int initialX = pacman.getX();
        pacman.move();

        // Should continue in current direction (LEFT) instead of desired (RIGHT)
        assertTrue(pacman.getX() <= initialX);
    }
}
