import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testLeftDirection() {
        assertEquals(180, Direction.LEFT.getAngle());
    }

    @Test
    void testRightDirection() {
        assertEquals(0, Direction.RIGHT.getAngle());
    }

    @Test
    void testUpDirection() {
        assertEquals(90, Direction.UP.getAngle());
    }

    @Test
    void testDownDirection() {
        assertEquals(270, Direction.DOWN.getAngle());
    }

    @Test
    void testEnumValues() {
        Direction[] values = Direction.values();
        assertEquals(4, values.length);
        assertEquals(Direction.LEFT, values[0]);
        assertEquals(Direction.RIGHT, values[1]);
        assertEquals(Direction.UP, values[2]);
        assertEquals(Direction.DOWN, values[3]);
    }

    @Test
    void testValueOf() {
        assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
        assertEquals(Direction.RIGHT, Direction.valueOf("RIGHT"));
        assertEquals(Direction.UP, Direction.valueOf("UP"));
        assertEquals(Direction.DOWN, Direction.valueOf("DOWN"));
    }
}
