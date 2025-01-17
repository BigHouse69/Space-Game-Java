package scrapping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Canvas implements Runnable, KeyListener {
    public static Dimension dimension;
    public BufferStrategy buffer;
    private Cenary cenary;
    private Enemies enemies;
    private Player player;
    private Projects projects;

    public void render(){
        Graphics2D graphics2D = (Graphics2D) buffer.getDrawGraphics();

        cenary.render(graphics2D);
        player.render(graphics2D);
        projects.render(graphics2D);
        enemies.render(graphics2D);
        renderHUD(graphics2D);

        buffer.show();
    }

    public void update(){
        if (!pause) {
            cenary.update();
            movePlayer();
            player.update();
            projects.update();
            enemies.update();
            colision();
            controlDead();
        }
    }


    @Override
    public void run(){
        createBufferStrategy(3);
        buffer = getBufferStrategy();
        cenary = new Cenary(true);
        player = new Player();
        projects = new Projects();
        enemies = new Enemies(1);

        while (true) {
            update();
            render();


            try{
            Thread.sleep(1000/60);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private int destroyedEnimies = 0;

    public void renderHUD(Graphics2D graphics2D){
        //Render life
        graphics2D.setColor(Color.GREEN);
        graphics2D.fillRect(5, 5, player.life*3, 15);

        graphics2D.setColor(Color.gray);
        graphics2D.drawRect(5, 5, player.widthLifeBar, 15);

        //Enemies destroyed
        BufferedImage enemyIMG = enemies.getIMG();
        graphics2D.drawImage(enemyIMG, 5, 25, 25, 25, null);
        Font font = new Font("Consolas", Font.BOLD, 20);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(String.valueOf(destroyedEnimies), 7, 70);

        //Pause
        if (pause){
            font = new Font("Consolas", Font.BOLD, 80);
            graphics2D.setColor(Color.red);
            graphics2D.drawString("Paused",
                    dimension.width /2 - 80 / 2,
                    dimension.height / 2 - 80 / 2);
        }
    }

    public void controlDead(){
        player.life -= enemies.damage;
        if (player.life <= 0){
            System.exit(0);
        }
    }

    public void colision(){
        try {
            for (int i = 0; i < enemies.allEnemies.size(); i++) {
                Rectangle areaEnemy = enemies.allEnemies.get(i).getArea();
                for (int j = 0; j < projects.projects.size(); j++) {
                    if (areaEnemy.contains(projects.projects.get(j).pos)) {
                        projects.projects.remove(j);
                        enemies.effects.generateEffects(new Point(areaEnemy.x, areaEnemy.y));
                        enemies.allEnemies.remove(i);
                        destroyedEnimies += 1;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Main(){
        dimension = new Dimension(900, 700);
        addKeyListener(this);
    }

    public static void main(String[] args) {
        Main main = new Main();
        JFrame jFrame = new JFrame("Game");
        jFrame.add(main);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setSize(dimension);

        Thread thread = new Thread(main);
        thread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    boolean key_w = false;
    boolean key_d = false;
    boolean key_a = false;
    boolean key_s = false;

    private void movePlayer(){
        if (key_w){
            player.pos.y -= player.speed;
        }
        if (key_d){
            player.pos.x += player.speed;
        }
        if (key_a){
            player.pos.x -= player.speed;
        }
        if (key_s){
            player.pos.y += player.speed;
        }
    }

    private boolean pause = false;

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'e'){
            cenary.turnEffects();
            System.out.println("Effects: "+cenary.effects_);
        }
        switch (e.getKeyChar()){
            case ('w'):
                key_w = true;
                break;
            case ('s'):
                key_s = true;
                break;
            case ('d'):
                key_d = true;
                break;
            case  ('a'):
                key_a = true;
                break;

            case ('f'):
                Point posShot = new Point(player.pos.x+player.width/2, player.pos.y+player.height/2);
                projects.shot(posShot);
                break;

            case ('p'):
                pause = !pause;
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()){
            case ('w'):
                key_w = false;
                break;
            case ('s'):
                key_s = false;
                break;
            case ('d'):
                key_d = false;
                break;
            case  ('a'):
                key_a = false;
                break;
            default:
                break;
        }
    }
}