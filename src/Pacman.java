import java.awt.*;
import java.awt.event.*;

public class Pacman {
    private int x, y;
    private int startX, startY;
    private Direction direction = Direction.LEFT;
    private int score = 0;
    private Board board;

    public Pacman(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.board = board;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x, y, 20, 20, direction.getAngle(), 300);
    }

    public void move() {
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
            // Comer punto
            board.eatPoint(x + 10, y + 10);
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: direction = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: direction = Direction.RIGHT; break;
            case KeyEvent.VK_UP: direction = Direction.UP; break;
            case KeyEvent.VK_DOWN: direction = Direction.DOWN; break;
        }
    }

    public int getScore() {
        return score;
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
    }
}