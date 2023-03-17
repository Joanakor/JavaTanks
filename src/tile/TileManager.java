package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gPanel;

    // Array of all tile variations
    Tile[] tile;

    // Numerical map of tiles
    int mapTileNum[][];


    public TileManager(GamePanel gp)
    {
        this.gPanel = gp;

        tile = new Tile[10];

        mapTileNum = new int[gPanel.MaxWorldCol][gPanel.MaxWorldRow];


        getTileImage();
        loadMap();
    }

    // Load map from txt file to the 2d array

    public void setup(int index, String imageName, boolean collision)
    {
        UtilityTool tool = new UtilityTool();

        try
        {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png")));
            tile[index].image = tool.scaleImage(tile[index].image, gPanel.tileSize, gPanel.tileSize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap()
    {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("maps/tilemap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gPanel.MaxWorldCol && row < gPanel.MaxWorldRow)
            {
                String line = br.readLine();
                while (col < gPanel.MaxWorldCol)
                {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gPanel.MaxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
            is.close();
        }
        catch (Exception e)
        {

        }
    }

    // Load all tile textures
    public void getTileImage()
    {
        setup(0, "floor", false);
        setup(1, "wall", false);
        setup(2, "water", false);
        setup(3, "tree", false);
        setup(4, "xpl_barrel", false);

    }

    public void draw(Graphics2D g2)
    {
        int worldCol = 0;
        int worldRow = 0;

        // Draw the floor tiles

        while (worldCol < gPanel.MaxWorldCol && worldRow < gPanel.MaxWorldRow)
        {
            int worldX = worldCol * gPanel.tileSize;
            int worldY = worldRow * gPanel.tileSize;
            int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
            int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;

            if(worldX + gPanel.tileSize > gPanel.player.worldX - gPanel.player.screenX &&
                    worldX - gPanel.tileSize < gPanel.player.worldX + gPanel.player.screenX &&
                    worldY + gPanel.tileSize > gPanel.player.worldY - gPanel.player.screenY &&
                    worldY - gPanel.tileSize < gPanel.player.worldY + gPanel.player.screenY)
            {
                g2.drawImage(tile[0].image, screenX, screenY, null);
                g2.drawImage(tile[0].image, screenX, screenY, null);

                if (mapTileNum[worldCol][worldRow] != 0)
                    g2.drawImage(tile[mapTileNum[worldCol][worldRow]].image, screenX, screenY, null);
                    g2.drawImage(tile[mapTileNum[worldCol][worldRow]].image, screenX, screenY, null);

            }

            worldCol++;
            if (worldCol == gPanel.MaxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
