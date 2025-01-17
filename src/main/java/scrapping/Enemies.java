package scrapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemies {
    private BufferedImage enemyImg;
    public ArrayList<Enemy> allEnemies = new ArrayList<>();
    public int difficult;
    public int damage = 0;
    public Effects effects;

    public Enemies(int difficult){
        this.difficult = difficult;
        effects = new Effects();

        try {
            String pathImg = "src/main/java/scrapping/imgs/enemy.png";
            enemyImg = ImageIO.read(new File(pathImg));
        } catch (IOException e ){
            e.printStackTrace();
        }
    }
    
    public void render(Graphics2D graphics2D){
        for (Enemy enemy : allEnemies){
            graphics2D.drawImage(enemyImg, enemy.pos.x, enemy.pos.y, Enemy.size, Enemy.size, null);
        }
        effects.render(graphics2D);
    }
    
    public void update(){
        damage = 0;

        controlEnemies();

        for (int i =0 ;i< allEnemies.size(); i++){
            Enemy enemy = allEnemies.get(i);
            enemy.updateMove();
            if (enemy.pos.x + Enemy.size < 0){
                damage += enemy.strong;
                allEnemies.remove(enemy);
                controlEnemies();
            }
        }
        effects.update();
    }

    public BufferedImage getIMG(){
        try {
            return ImageIO.read(new File("C:/Users/gabri/IdeaProjects/CopyPage/src/main/java/scrapping/imgs/enemy.png"));
        } catch (IOException e ){
            e.printStackTrace();
            return null;
        }
    }


    private void controlEnemies(){
        if (allEnemies.isEmpty()){
            for (int i = 0; i < new Random().nextInt(difficult) + 1; i++){
                generateEnemy();
            }
            int x = new Random().nextInt(2);
            if (x == 0) {
                difficult += 1;
            }
        }
    }

    public void generateEnemy(){
        Random random = new Random();
        int x = random.nextInt(80) + Main.dimension.width;
        int y = random.nextInt(Main.dimension.height - Enemy.size);
        allEnemies.add(new Enemy(new Point(x,y)));
    }

    public static class Enemy{
        public Point pos;
        public int speed = 3;
        public static final int size = 50;
        public int tempY;
        public int strong = 20;
        private Rectangle area;

        public Enemy (Point initialPos){
            pos = initialPos;
            tempY = new Random().nextInt(Main.dimension.height- size);
        }

        public void updateMove(){
            int distance = pos.y - tempY;

            if (Math.abs(distance) <= speed) {
                tempY = new Random().nextInt(Main.dimension.height- size);
            }

            pos.x -= speed;

            if (distance < 0){
                pos.y += speed;
            } else if (distance > 0 ) {
                pos.y -= speed;
            }
            area = new Rectangle(pos.x,pos.y,size,size);
        }

        public Rectangle getArea(){
            return area;
        }
    }

    public static class Effects{
        private int quantity = 40;

        public ArrayList<Effect> allEffects = new ArrayList<>();

        public void render(Graphics2D graphics2D){
            for (int i = 0 ; i < allEffects.size(); i++){
                Effect effect = allEffects.get(i);
                graphics2D.setColor(effect.cor);
                graphics2D.fillRect((int) effect.x, (int) effect.y, effect.size, effect.size);
            }
        }

        public void update(){
            for (int i = 0; i < allEffects.size(); i++){
                Effect effect = allEffects.get(i);
                effect.x += effect.dx * effect.speed;
                effect.y += effect.dy * effect.speed;
                effect.timer ++;
                if (effect.timer >= effect.duration){
                    allEffects.remove(effect);
                }
            }
        }

        public void generateEffects(Point initialPos){
            for (int i = 0 ; i < quantity; i++){
                allEffects.add(new Effect(initialPos.x , initialPos.y));
            }
        }

        private static class Effect{
            private int size = 3;
            public Color cor;
            public int speed = 3;
            public double duration = 30;
            public int timer;
            Random random  = new Random();
            double dx;
            double dy;
            public double x;
            public double y;

            public Effect(int x, int y){
                this.x = x;
                this.y = y;

                cor = new Color(random.nextInt(254),
                        random.nextInt(254),
                        random.nextInt(254));
                dx = random.nextGaussian();
                dy = random.nextGaussian();
            }
        }
    }
}
