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
    private boolean powerUpActive = false;
    private long powerUpStartTime = 0;
    private static final long POWER_UP_DURATION = 15000; // 15 seconds in milliseconds
    private static final long BLINK_START_TIME = 12000; // Start blinking at 12 seconds (last 3 seconds)

    public Pacman(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.board = board;
        this.spriteSize = board.getSpriteSize();
    }

    public void draw(Graphics g) {
        // Update power-up state
        if (powerUpActive) {
            long elapsed = System.currentTimeMillis() - powerUpStartTime;
            if (elapsed >= POWER_UP_DURATION) {
                powerUpActive = false;
            }
        }
        
        // Choose color based on power-up state
        Color pacmanColor = Color.YELLOW;
        if (powerUpActive) {
            long elapsed = System.currentTimeMillis() - powerUpStartTime;
            if (elapsed >= BLINK_START_TIME) {
                // Blink between blue and white every 250ms
                if ((elapsed / 250) % 2 == 0) {
                    pacmanColor = Color.CYAN;
                } else {
                    pacmanColor = Color.WHITE;
                }
            } else {
                // Solid blue for first 12 seconds
                pacmanColor = Color.CYAN;
            }
        }
        
        g.setColor(pacmanColor);
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
        
        // Apply screen wrapping
        newX = board.wrapX(newX);
        newY = board.wrapY(newY);
        
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
            
            // Apply screen wrapping again for current direction
            newX = board.wrapX(newX);
            newY = board.wrapY(newY);
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
        this.powerUpActive = false;
        this.powerUpStartTime = 0;
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
    
    public void activatePowerUp() {
        powerUpActive = true;
        powerUpStartTime = System.currentTimeMillis();
    }
    
    public boolean isPoweredUp() {
        if (powerUpActive) {
            long elapsed = System.currentTimeMillis() - powerUpStartTime;
            if (elapsed >= POWER_UP_DURATION) {
                powerUpActive = false;
                return false;
            }
            return true;
        }
        return false;
    }
}