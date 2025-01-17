package scrapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Projects {
    public ArrayList<Project> projects = new ArrayList<>();

    public Projects(){

    }

    public void render(Graphics2D graphics2D){
        try {
            for (Project project : projects) {
                graphics2D.setColor(project.color);
                graphics2D.drawOval(project.pos.x, project.pos.y, project.size, project.size);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(){
        for (int i = 0 ; i < projects.size(); i++){
            Project project = projects.get(i);

            project.pos.x += project.speed;

            if (project.pos.x > Main.dimension.width){
                projects.remove(project);
            }
        }
    }

    public void shot(Point playerPos){
        projects.add(new Project(playerPos));
    }

    static class Project {
        public Point pos;
        public Color color;
        public final int speed = 6;
        public final int size = 3;

        public Project(Point playerPos){
            Random random = new Random();
            //color = new Color(random.nextInt(254), random.nextInt(254), random.nextInt(254));
            color = new Color(255, 255, 0);
            pos = playerPos;
        }
    }
}
