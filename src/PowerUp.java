import java.awt.*;

public class PowerUp {
    private int x, y;
    private boolean active;
    private boolean collected;
    private int spriteSize;
    private static final int INNER_OFFSET = 4;
    
    public PowerUp(int x, int y, int spriteSize) {
        this.x = x;
        this.y = y;
        this.spriteSize = spriteSize;
        this.active = true;
        this.collected = false;
    }
    
    public void draw(Graphics g) {
        if (active && !collected) {
            g.setColor(Color.MAGENTA);
            g.fillOval(x, y, spriteSize, spriteSize);
            g.setColor(Color.WHITE);
            int innerSize = spriteSize - (INNER_OFFSET * 2);
            g.fillOval(x + INNER_OFFSET, y + INNER_OFFSET, innerSize, innerSize);
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
    
    public boolean isActive() {
        return active && !collected;
    }
    
    public void collect() {
        collected = true;
    }
    
    public void reset() {
        collected = false;
        active = true;
    }
}
