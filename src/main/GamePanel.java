package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // All tiles are 16x16px
    final int originalTileSize = 16;

    // Scale all tiles by 3 times
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 3 * 16 = 48px
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    // Game window resolution
    public final int screenWidth = maxScreenCol * tileSize; // 16 * 48 = 768px
    public final int screenHeight = maxScreenRow * tileSize; // 12 * 488 = 576px

    // Frames per second
    final int FPS = 60;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);

    Player player = new Player(this, keyHandler);

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    // Game thread
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Game loop
    @Override
    public void run()
    {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1) {
                // UPDATE:
                update();
                // DRAW:
                repaint();

                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        player.draw(g2);

        g2.dispose();
    }
}
