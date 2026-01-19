import java.awt.*;
import java.awt.event.*;

public class Pacman {
    private int x, y;
    private int startX, startY;
    private Direction currentDirection = Direction.LEFT;
    private Direction desiredDirection = Direction.LEFT;
    private int score = 0;
    private Board board;
    private int spriteSize;

    public Pacman(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.board = board;
        this.spriteSize = board.getSpriteSize();
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillArc(x, y, spriteSize, spriteSize, currentDirection.getAngle(), 300);
    }

    public void move() {
        int newX = x;
        int newY = y;
        Direction directionToTry = desiredDirection;
        
        // Intentar moverse en la dirección deseada
        switch (desiredDirection) {
            case LEFT: newX -= 4; break;
            case RIGHT: newX += 4; break;
            case UP: newY -= 4; break;
            case DOWN: newY += 4; break;
        }
        
        // Verificar si la dirección deseada está bloqueada
        int edgeOffset = spriteSize - 1;
        boolean desiredBlocked = board.isWall(newX, newY) || board.isWall(newX + edgeOffset, newY) ||
            board.isWall(newX, newY + edgeOffset) || board.isWall(newX + edgeOffset, newY + edgeOffset);
        
        // Si la dirección deseada está bloqueada, intentar continuar en la dirección actual
        if (desiredBlocked && desiredDirection != currentDirection) {
            newX = x;
            newY = y;
            directionToTry = currentDirection;
            
            switch (currentDirection) {
                case LEFT: newX -= 4; break;
                case RIGHT: newX += 4; break;
                case UP: newY -= 4; break;
                case DOWN: newY += 4; break;
            }
        }
        
        // Verificar colisión con paredes
        if (!board.isWall(newX, newY) && !board.isWall(newX + edgeOffset, newY) &&
            !board.isWall(newX, newY + edgeOffset) && !board.isWall(newX + edgeOffset, newY + edgeOffset)) {
            x = newX;
            y = newY;
            currentDirection = directionToTry;
            // Comer punto
            board.eatPoint(x + spriteSize / 2, y + spriteSize / 2);
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: desiredDirection = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: desiredDirection = Direction.RIGHT; break;
            case KeyEvent.VK_UP: desiredDirection = Direction.UP; break;
            case KeyEvent.VK_DOWN: desiredDirection = Direction.DOWN; break;
        }
    }

    public int getScore() {
        return score;
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    public void reset() {
        this.x = startX;
        this.y = startY;
        this.score = 0;
        this.currentDirection = Direction.LEFT;
        this.desiredDirection = Direction.LEFT;
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
}