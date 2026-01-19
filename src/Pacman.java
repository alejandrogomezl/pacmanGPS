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
    private static final long BLINK_INTERVAL = 250; // Blink every 250ms

    public Pacman(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.board = board;
        this.spriteSize = board.getSpriteSize();
    }

    public void draw(Graphics g) {
        Color pacmanColor = getPacmanColor();
        g.setColor(pacmanColor);
        g.fillArc(x, y, spriteSize, spriteSize, currentDirection.getAngle(), 300);
    }
    
    private Color getPacmanColor() {
        if (!powerUpActive) {
            return Color.YELLOW;
        }
        
        long elapsed = getElapsedPowerUpTime();
        if (elapsed >= POWER_UP_DURATION) {
            powerUpActive = false;
            return Color.YELLOW;
        }
        
        if (elapsed >= BLINK_START_TIME) {
            // Blink between cyan and white
            return (elapsed / BLINK_INTERVAL) % 2 == 0 ? Color.CYAN : Color.WHITE;
        }
        
        // Solid cyan for first 12 seconds
        return Color.CYAN;
    }
    
    private long getElapsedPowerUpTime() {
        return System.currentTimeMillis() - powerUpStartTime;
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
        if (!powerUpActive) {
            return false;
        }
        
        if (getElapsedPowerUpTime() >= POWER_UP_DURATION) {
            powerUpActive = false;
            return false;
        }
        
        return true;
    }
}