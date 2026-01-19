import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private Pacman pacman;
    private Ghost[] ghosts;
    private int currentLevel = 0;
    private int[][] levelData;
    private boolean gameOver = false;
    private static final int BLOCK_SIZE = 20;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static final int SPRITE_SIZE = 20;
    private static final int PACMAN_START_X = 180;
    private static final int PACMAN_START_Y = 300;
    private static final int GHOST1_START_X = 180;
    private static final int GHOST1_START_Y = 180;
    private static final int GHOST2_START_X = 60;
    private static final int GHOST2_START_Y = 60;
    private static final int GHOST3_START_X = 300;
    private static final int GHOST3_START_Y = 60;
    
    // Códigos para el mapa: 0=pared, 1=punto, 2=camino vacío
    private static final int[][][] LEVELS = {
        // Nivel 1 - Diseño simple
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,1,0},
            {0,1,1,1,1,0,1,1,1,0,0,1,1,1,0,1,1,1,1,0},
            {0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,0,0,2,2,0,0,1,0,1,0,0,0,0},
            {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
            {0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,0},
            {0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0},
            {0,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0},
            {0,0,1,0,1,0,1,0,0,0,0,0,0,1,0,1,0,1,0,0},
            {0,1,1,1,1,0,1,1,1,0,0,1,1,1,0,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        },
        // Nivel 2 - Diseño con más paredes
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0},
            {0,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,0},
            {0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0},
            {0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0},
            {0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,0,1,0},
            {0,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,0},
            {0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0},
            {2,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,2},
            {0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,0},
            {0,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,0},
            {0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,0,1,0},
            {0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0},
            {0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0},
            {0,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,0},
            {0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        },
        // Nivel 3 - Diseño complejo
        {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1,0},
            {0,1,0,1,1,0,1,1,1,0,0,1,1,1,0,1,1,0,1,0},
            {0,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0},
            {0,1,0,1,1,1,1,0,1,0,0,1,0,1,1,1,1,0,1,0},
            {0,1,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1,0},
            {2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2},
            {0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0},
            {0,1,0,1,1,1,1,0,1,0,0,1,0,1,1,1,1,0,1,0},
            {0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,0,1,0,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,0},
            {0,1,0,1,1,0,1,1,1,0,0,1,1,1,0,1,1,0,1,0},
            {0,1,0,0,0,0,1,0,1,0,0,1,0,1,0,0,0,0,1,0},
            {0,1,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,0},
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        }
    };

    public Board() {
        setFocusable(true);
        setBackground(Color.BLACK);
        loadLevel(currentLevel);
        pacman = new Pacman(PACMAN_START_X, PACMAN_START_Y, this);
        ghosts = new Ghost[] {
            new Ghost(GHOST1_START_X, GHOST1_START_Y, Color.RED, this),
            new Ghost(GHOST2_START_X, GHOST2_START_Y, Color.PINK, this),
            new Ghost(GHOST3_START_X, GHOST3_START_Y, Color.CYAN, this)
        };
        timer = new Timer(40, this);
        timer.start();
        addKeyListener(new PacmanKeyAdapter());
    }
    
    private void loadLevel(int level) {
        if (level < 0 || level >= LEVELS.length) {
            level = 0;
        }
        levelData = new int[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                levelData[i][j] = LEVELS[level][i][j];
            }
        }
    }
    
    public int getSpriteSize() {
        return SPRITE_SIZE;
    }
    
    public boolean isWall(int x, int y) {
        // Apply wrapping before checking wall collision
        x = wrapX(x);
        y = wrapY(y);
        
        int col = x / BLOCK_SIZE;
        int row = y / BLOCK_SIZE;
        if (row < 0 || row >= BOARD_HEIGHT || col < 0 || col >= BOARD_WIDTH) {
            return true;
        }
        return levelData[row][col] == 0;
    }
    
    public int wrapX(int x) {
        int boardPixelWidth = BOARD_WIDTH * BLOCK_SIZE;
        if (x < 0) {
            return x + boardPixelWidth;
        } else if (x >= boardPixelWidth) {
            return x - boardPixelWidth;
        }
        return x;
    }
    
    public int wrapY(int y) {
        int boardPixelHeight = BOARD_HEIGHT * BLOCK_SIZE;
        if (y < 0) {
            return y + boardPixelHeight;
        } else if (y >= boardPixelHeight) {
            return y - boardPixelHeight;
        }
        return y;
    }
    
    public void eatPoint(int x, int y) {
        int col = x / BLOCK_SIZE;
        int row = y / BLOCK_SIZE;
        if (row >= 0 && row < BOARD_HEIGHT && col >= 0 && col < BOARD_WIDTH) {
            if (levelData[row][col] == 1) {
                levelData[row][col] = 2;
                pacman.addScore(10);
                checkLevelComplete();
            }
        }
    }
    
    private void checkLevelComplete() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (levelData[i][j] == 1) {
                    return;
                }
            }
        }
        // Todos los puntos comidos, avanzar al siguiente nivel
        currentLevel++;
        if (currentLevel >= LEVELS.length) {
            currentLevel = 0; // Reiniciar al primer nivel
        }
        loadLevel(currentLevel);
        pacman.reset();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        pacman.draw(g);
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }
        
        if (gameOver) {
            drawGameOver(g);
        }
    }

    private void drawBoard(Graphics g) {
        // Dibujar paredes y puntos
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (levelData[i][j] == 0) {
                    // Dibujar pared
                    g.setColor(Color.BLUE);
                    g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                } else if (levelData[i][j] == 1) {
                    // Dibujar punto
                    g.setColor(Color.WHITE);
                    g.fillOval(j * BLOCK_SIZE + 7, i * BLOCK_SIZE + 7, 6, 6);
                }
            }
        }
        
        // Dibujar información
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + pacman.getScore(), 10, 410);
        g.drawString("Level: " + (currentLevel + 1), 150, 410);
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String gameOverText = "GAME OVER";
        int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
        int y = getHeight() / 2 - 20;
        g.drawString(gameOverText, x, y);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        fm = g.getFontMetrics();
        String restartText = "Press SPACE to restart";
        x = (getWidth() - fm.stringWidth(restartText)) / 2;
        y = getHeight() / 2 + 40;
        g.drawString(restartText, x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            pacman.move();
            for (Ghost ghost : ghosts) {
                ghost.move();
            }
            checkCollisions();
        }
        repaint();
    }
    
    private void checkCollisions() {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();
        int pacmanSize = pacman.getSpriteSize();
        
        for (Ghost ghost : ghosts) {
            int ghostX = ghost.getX();
            int ghostY = ghost.getY();
            int ghostSize = ghost.getSpriteSize();
            
            // Detectar colisión usando rectángulos
            if (pacmanX < ghostX + ghostSize &&
                pacmanX + pacmanSize > ghostX &&
                pacmanY < ghostY + ghostSize &&
                pacmanY + pacmanSize > ghostY) {
                gameOver = true;
                break;
            }
        }
    }
    
    private void restartGame() {
        gameOver = false;
        currentLevel = 0;
        loadLevel(currentLevel);
        pacman.reset();
        for (Ghost ghost : ghosts) {
            ghost.reset();
        }
    }

    private class PacmanKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
                restartGame();
            } else if (!gameOver) {
                pacman.keyPressed(e);
            }
        }
    }
}