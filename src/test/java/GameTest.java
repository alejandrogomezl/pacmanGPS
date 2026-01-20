import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testMainMethod() {
        // Can't test GUI creation in headless mode
        // Just verify the class exists and main method is accessible
        assertDoesNotThrow(() -> {
            Class<?> gameClass = Game.class;
            assertNotNull(gameClass);
            assertNotNull(gameClass.getMethod("main", String[].class));
        });
    }
}
