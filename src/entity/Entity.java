package entity;

import java.awt.image.BufferedImage;

// Base entity
public class Entity {
    public int x, y;
    public int speed;

    // Animation sprites
    public BufferedImage up1, up2, down1, down2, right1, right2, left1,  left2;
    public String direction;

    // Variable for setting the time between animation sprites
    public int spriteCounter = 0;

    // Variable that defines what animation sprite to draw (1 -> up1, ..., left1; 2 -> up2, ..., left2)
    public int spriteNum = 1;
}
