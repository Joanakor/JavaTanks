package tile;

import main.GamePanel;

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

        mapTileNum = new int[gPanel.maxScreenCol][gPanel.maxScreenRow];


        getTileImage();
        loadMap();
    }

    // Load map from txt file to the 2d array
    public void loadMap()
    {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("maps/tilemap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gPanel.maxScreenCol && row < gPanel.maxScreenRow)
            {
                String line = br.readLine();
                while (col < gPanel.maxScreenCol)
                {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gPanel.maxScreenCol)
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

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/floor.png")));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wall.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/tree.png")));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/xpl_barrel.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2)
    {
        int col = 0;
        int row = 0;
        int y = 0;
        int x = 0;

        // Draw the floor tiles

        while (col < gPanel.maxScreenCol && row < gPanel.maxScreenRow)
        {


            while (col < gPanel.maxScreenCol)
            {
                g2.drawImage(tile[0].image, x, y, gPanel.tileSize, gPanel.tileSize, null);

                if (mapTileNum[col][row] != 0)
                    g2.drawImage(tile[mapTileNum[col][row]].image, x, y, gPanel.tileSize, gPanel.tileSize, null);

                col++;
                x += gPanel.tileSize;
            }
            if (col == gPanel.maxScreenCol)
            {
                col = 0;
                row++;
                x = 0;
                y += gPanel.tileSize;
            }
        }
    }
}
