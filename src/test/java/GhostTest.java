import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class GhostTest {

    @Mock
    private Board mockBoard;

    @Mock
    private Graphics mockGraphics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockBoard.getSpriteSize()).thenReturn(20);
        when(mockBoard.wrapX(anyInt())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mockBoard.wrapY(anyInt())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testConstructor() {
        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);
        assertNotNull(ghost);
        assertEquals(100, ghost.getX());
        assertEquals(100, ghost.getY());
        assertEquals(20, ghost.getSpriteSize());
    }

    @Test
    void testGetX() {
        Ghost ghost = new Ghost(150, 200, Color.PINK, mockBoard);
        assertEquals(150, ghost.getX());
    }

    @Test
    void testGetY() {
        Ghost ghost = new Ghost(150, 200, Color.CYAN, mockBoard);
        assertEquals(200, ghost.getY());
    }

    @Test
    void testGetSpriteSize() {
        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);
        assertEquals(20, ghost.getSpriteSize());
    }

    @Test
    void testDraw() {
        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);

        ghost.draw(mockGraphics);

        verify(mockGraphics).setColor(Color.RED);
        verify(mockGraphics).fillOval(100, 100, 20, 20);
    }

    @Test
    void testDrawWithDifferentColor() {
        Ghost ghost = new Ghost(50, 75, Color.PINK, mockBoard);

        ghost.draw(mockGraphics);

        verify(mockGraphics).setColor(Color.PINK);
        verify(mockGraphics).fillOval(50, 75, 20, 20);
    }

    @Test
    void testMoveWithoutWall() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);
        int initialX = ghost.getX();
        int initialY = ghost.getY();

        ghost.move();

        // Position should change (may be x or y depending on random direction)
        assertTrue(ghost.getX() != initialX || ghost.getY() != initialY);
    }

    @Test
    void testMoveWithWall() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(true);

        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);

        // Movement should be attempted but blocked by wall
        assertDoesNotThrow(() -> ghost.move());
    }

    @Test
    void testReset() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Ghost ghost = new Ghost(100, 150, Color.RED, mockBoard);

        // Move the ghost
        ghost.move();

        // Reset should return to start position
        ghost.reset();
        assertEquals(100, ghost.getX());
        assertEquals(150, ghost.getY());
    }

    @Test
    void testMultipleMoves() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);

        // Multiple moves should work
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> ghost.move());
        }
    }

    @Test
    void testWrapXIsCalled() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);
        ghost.move();

        verify(mockBoard, atLeastOnce()).wrapX(anyInt());
    }

    @Test
    void testWrapYIsCalled() {
        when(mockBoard.isWall(anyInt(), anyInt())).thenReturn(false);

        Ghost ghost = new Ghost(100, 100, Color.RED, mockBoard);
        ghost.move();

        verify(mockBoard, atLeastOnce()).wrapY(anyInt());
    }
}
