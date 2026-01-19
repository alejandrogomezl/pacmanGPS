import java.awt.*;
import java.util.Random;

public class PowerUp {
    private int x, y;
    private boolean active;
    private Board board;
    private int spriteSize;
    private static final int BLOCK_SIZE = 20;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static final int MAX_SPAWN_ATTEMPTS = 100;
    private Random random = new Random();

    public PowerUp(Board board) {
        this.board = board;
        this.spriteSize = board.getSpriteSize();
        this.active = false;
        spawn();
    }

    public void spawn() {
        // Find a random valid position (not a wall)
        int attempt = 0;
        boolean validPosition = false;
        
        while (!validPosition && attempt < MAX_SPAWN_ATTEMPTS) {
            // Random position in the board
            int col = random.nextInt(BOARD_WIDTH);
            int row = random.nextInt(BOARD_HEIGHT);
            
            x = col * BLOCK_SIZE;
            y = row * BLOCK_SIZE;
            
            // Check if it's not a wall
            if (!board.isWall(x, y)) {
                validPosition = true;
                active = true;
            }
            attempt++;
        }
    }

    public void draw(Graphics g) {
        if (active) {
            // Draw power-up as a star or special icon
            g.setColor(Color.GREEN);
            g.fillOval(x + 5, y + 5, 10, 10);
            g.setColor(Color.WHITE);
            g.fillOval(x + 7, y + 7, 6, 6);
        }
    }

    public boolean checkCollision(int pacmanX, int pacmanY, int pacmanSize) {
        if (!active) {
            return false;
        }
        
        // Check collision with pacman
        if (pacmanX < x + spriteSize &&
            pacmanX + pacmanSize > x &&
            pacmanY < y + spriteSize &&
            pacmanY + pacmanSize > y) {
            active = false;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void reset() {
        spawn();
    }
}
