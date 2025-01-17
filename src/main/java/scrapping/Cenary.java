package scrapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Cenary {
    Effects effects;
    boolean effects_;

    public Cenary(boolean effects_){
        this.effects_ = effects_;
        if (effects_) {
            effects = new Effects(3, 40);
        }
    }

    public void render(Graphics2D graphics2D){
        //BG
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, Main.dimension.width, Main.dimension.height);

        //Render Effects
        if (effects_) {
            renderEffects(graphics2D);
        }
    }

    public void update(){
        //Atualiza effeitos
        if (effects_) {
        for (Effects.Effect effect: effects.allEffect){
            effect.pos.x -= effect.speed;
        }

        //Chamando controlador de efeitos
            effects.controlEffects();
        }
    }

    public void turnEffects(){
        effects_ = !effects_;
        if (effects_){
            effects = new Effects(3, 40);
        }
        System.out.println("Effects: " + effects_);
    }

    private void renderEffects(Graphics2D graphics2D){
        for(Effects.Effect effect: effects.allEffect){
            graphics2D.setColor(effect.cor);
            graphics2D.fillRect(effect.pos.x, effect.pos.y, effect.lengthSquare, effect.lengthSquare);
        }
    }

    private static class Effects{
        public int speed;
        public int much;
        public ArrayList<Effect> allEffect = new ArrayList<>();

        public Effects(int speed, int much){
            this.speed = speed;
            this.much = much;
            generateBegingEfffects();
        }

        public void controlEffects(){
            //Cria
            if (allEffect.size() < much){
                generateEffect();
            }

            //Deleta
            for (int i = 0; i < allEffect.size(); i++){
                Effect effect = allEffect.get(i);
                if (effect.pos.x + effect.lengthSquare <= 0){
                    allEffect.remove(effect);
                }
            }
        }

        private void generateBegingEfffects(){
            for (int i=0; i < much; i++){
                generateEffect();
                allEffect.get(i).pos.x = new Random().nextInt(Main.dimension.width);
            }
        }

        private void generateEffect(){
            allEffect.add(new Effect());
        }

        private static class Effect{
            public Point pos;
            public final int lengthSquare = new Random().nextInt(4) + 2;
            public final int speed = new Random().nextInt(3) + 1;
            public final Color cor = Color.WHITE;

            public Effect(){
                int x = new Random().nextInt(60) + Main.dimension.width;
                int y = new Random().nextInt(Main.dimension.height);
                pos = new Point(x, y);
            }
        }
    }
}
