import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private int startX, startY;
    private Direction direction;
    private Color color;
    private Random random = new Random();
    private Board board;
    private int spriteSize;

    public Ghost(int x, int y, Color color, Board board) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.color = color;
        this.board = board;
        this.spriteSize = board.getSpriteSize();
        this.direction = Direction.values()[random.nextInt(4)];
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, spriteSize, spriteSize);
    }

    public void move() {
        if (random.nextInt(10) == 0) {
            direction = Direction.values()[random.nextInt(4)];
        }

        int newX = x;
        int newY = y;

        switch (direction) {
        case LEFT:
            newX -= 4;
            break;
        case RIGHT:
            newX += 4;
            break;
        case UP:
            newY -= 4;
            break;
        case DOWN:
            newY += 4;
            break;
        }

        // Apply screen wrapping
        newX = board.wrapX(newX);
        newY = board.wrapY(newY);

        // Verificar colisión con paredes
        int edgeOffset = spriteSize - 1;
        if (!board.isWall(newX, newY) && !board.isWall(newX + edgeOffset, newY)
                && !board.isWall(newX, newY + edgeOffset) && !board.isWall(newX + edgeOffset, newY + edgeOffset)) {
            x = newX;
            y = newY;
        } else {
            // Cambiar dirección si choca con una pared
            direction = Direction.values()[random.nextInt(4)];
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpriteSize() {
        return spriteSize;
    }

    public void reset() {
        this.x = startX;
        this.y = startY;
        this.direction = Direction.values()[random.nextInt(4)];
    }
}