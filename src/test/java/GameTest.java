import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testMainMethodExists() {
        // Test that main method can be called without exceptions in reflection
        assertDoesNotThrow(() -> {
            Game.class.getMethod("main", String[].class);
        });
    }

    @Test
    void testGameClassExists() {
        assertNotNull(Game.class);
    }

    @Test
    void testGameExtendsJFrame() {
        assertTrue(javax.swing.JFrame.class.isAssignableFrom(Game.class));
    }

    @Test
    void testGameHasConstructor() {
        assertDoesNotThrow(() -> {
            Game.class.getDeclaredConstructor();
        });
    }

    @Test
    void testGameClassIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(Game.class.getModifiers()));
    }

    @Test
    void testMainMethodIsPublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
            Game.class.getMethod("main", String[].class).getModifiers()
        ));
    }

    @Test
    void testMainMethodIsStatic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isStatic(
            Game.class.getMethod("main", String[].class).getModifiers()
        ));
    }

    @Test
    void testMainMethodReturnsVoid() throws NoSuchMethodException {
        assertEquals(void.class, 
            Game.class.getMethod("main", String[].class).getReturnType()
        );
    }
}
