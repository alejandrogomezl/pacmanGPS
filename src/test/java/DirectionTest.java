import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testLeftDirectionAngle() {
        assertEquals(180, Direction.LEFT.getAngle());
    }

    @Test
    void testRightDirectionAngle() {
        assertEquals(0, Direction.RIGHT.getAngle());
    }

    @Test
    void testUpDirectionAngle() {
        assertEquals(90, Direction.UP.getAngle());
    }

    @Test
    void testDownDirectionAngle() {
        assertEquals(270, Direction.DOWN.getAngle());
    }

    @Test
    void testValuesLength() {
        assertEquals(4, Direction.values().length);
    }

    @Test
    void testValueOf() {
        assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
        assertEquals(Direction.RIGHT, Direction.valueOf("RIGHT"));
        assertEquals(Direction.UP, Direction.valueOf("UP"));
        assertEquals(Direction.DOWN, Direction.valueOf("DOWN"));
    }
}
