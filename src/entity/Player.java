package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// Controllable player class
public class Player extends Entity{
    GamePanel gPanel;
    KeyHandler kHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler kh)
    {
        gPanel = gp;
        kHandler = kh;

        //Location of the player on the screen (in its center)
        screenX = gPanel.screenWidth/2 - gp.tileSize/2;
        screenY = gPanel.screenHeight/2 - gp.tileSize/2;

        setDefaultValues();
        getPlayerImage();
    }

    // Load the sprites of the player
    public void getPlayerImage(){

        up1 = setup("tank_up1");
        up2 = setup("tank_up2");
        down1 = setup("tank_down1");
        down2 = setup("tank_down2");
        right1 = setup("tank_right1");
        right2 = setup("tank_right2");
        left1 = setup("tank_left1");
        left2 = setup("tank_left2");
    }

    public BufferedImage setup(String imageName)
    {
        UtilityTool tool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png")));
            scaledImage = tool.scaleImage(scaledImage, gPanel.tileSize, gPanel.tileSize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return scaledImage;
    }
    public void setDefaultValues()
    {
        worldX = gPanel.tileSize * 21;
        worldY = gPanel.tileSize * 13;
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

        g.drawImage(sprite, screenX, screenY, null);
    }

}
