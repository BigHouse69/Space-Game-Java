package scrapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {
    public Point pos;
    public final int speed = 5;
    private BufferedImage playerImg;
    public final int width = 80;
    public final int height = 80;

    public int life = 200;

    public Player() {
        pos = new Point( 30, (int) Main.dimension.height/2);
        loadImg();
    }

    public void render(Graphics2D graphics2D){
        if (playerImg != null) {
            graphics2D.drawImage(playerImg, pos.x, pos.y, width, height, null);
        }
    }
    public final int widthLifeBar = life * 3;

    public void update(){

    }

    private void loadImg(){
        try {
            String path_img = "C:/Users/gabri/IdeaProjects/CopyPage/src/main/java/scrapping/imgs/player.png";
            playerImg = ImageIO.read(new File(path_img));
        } catch (IOException e ){
            e.printStackTrace();
        }
    }
}