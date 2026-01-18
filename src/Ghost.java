import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private Direction direction;
    private Color color;
    private Random random = new Random();
    private Board board;

    public Ghost(int x, int y, Color color, Board board) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.board = board;
        this.direction = Direction.values()[random.nextInt(4)];
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 20, 20);
    }

    public void move() {
        if (random.nextInt(10) == 0) {
            direction = Direction.values()[random.nextInt(4)];
        }
        
        int newX = x;
        int newY = y;
        
        switch (direction) {
            case LEFT: newX -= 4; break;
            case RIGHT: newX += 4; break;
            case UP: newY -= 4; break;
            case DOWN: newY += 4; break;
        }
        
        // Verificar colisión con paredes
        if (!board.isWall(newX, newY) && !board.isWall(newX + 19, newY) &&
            !board.isWall(newX, newY + 19) && !board.isWall(newX + 19, newY + 19)) {
            x = newX;
            y = newY;
        } else {
            // Cambiar dirección si choca con una pared
            direction = Direction.values()[random.nextInt(4)];
        }
    }
}