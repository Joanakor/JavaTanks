package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// Controllable player class
public class Player extends Entity{
    GamePanel gPanel;
    KeyHandler kHandler;

    public Player(GamePanel gp, KeyHandler kh)
    {
        gPanel = gp;
        kHandler = kh;

        setDefaultValues();
        getPlayerImage();
    }

    // Load the sprites of the player
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_up2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_down2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_right2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/tank_left2.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues()
    {
        worldX = 96;
        worldY = 96;
        speed = 5;
        direction = "down";
    }

    public void update()
    {
        if (kHandler.upPressed || kHandler.downPressed || kHandler.rightPressed || kHandler.leftPressed)
        {
            if (kHandler.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (kHandler.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (kHandler.rightPressed) {
                direction = "right";
                worldX += speed;
            } else if (kHandler.leftPressed) {
                direction = "left";
                worldX -= speed;
            }

            // Switch sprite every 10 frames

            spriteCounter++;
            if (spriteCounter > 10) {
                switch (spriteNum) {
                    case 1 -> spriteNum = 2;
                    case 2 -> spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g)
    {
        BufferedImage sprite = null;

        switch(direction)
        {
            case "up":
                sprite = switch (spriteNum) {
                    case 1 -> up1;
                    case 2 -> up2;
                    default -> sprite;
                };
                break;
            case "down":
                sprite = switch (spriteNum) {
                    case 1 -> down1;
                    case 2 -> down2;
                    default -> sprite;
                };
                break;
            case "right":
                sprite = switch (spriteNum) {
                    case 1 -> right1;
                    case 2 -> right2;
                    default -> sprite;
                };
                break;
            case "left":
                sprite = switch (spriteNum) {
                    case 1 -> left1;
                    case 2 -> left2;
                    default -> sprite;
                };
                break;
        }

        g.drawImage(sprite, worldX, worldY, gPanel.tileSize, gPanel.tileSize, null);
    }

}
