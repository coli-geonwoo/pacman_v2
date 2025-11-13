package game.utils;

import game.Game;
import game.entities.Entity;
import game.entities.GhostHouse;
import game.entities.Wall;

import java.awt.*;

//Classe pour détecter les collision entre une entité et un mur (par rapport à la classe CollisionDetector, les murs sont statiques)
//엔티티와 벽 사이의 충돌을 감지하는 클래스(CollisionDetector 클래스와 달리 벽은 정적임)
public class WallCollisionDetector {

    //Fonction pour s'avoir s'il y a un mur à la position d'une entité + un certain delta (ce delta permet de détecter le mur avant de rentrer dedans)
    //엔티티 위치에 벽이 있는지 확인하는 함수 + 특정 델타(이 델타를 통해 벽에 들어가기 전에 벽을 감지할 수 있음)
    public static boolean checkWallCollision(Entity obj, int dx, int dy) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : Game.getWalls()) {
            if (w.getHitbox().intersects(r)) return true;
        }
        return false;
    }

    //Même chose que la méthode précédente, mais on peut ignorer ici les collisions avec les murs de la maison des fantômes
    //이전 방법과 동일하지만 여기서는 유령 집의 벽과의 충돌을 무시할 수 있습니다.
    public static boolean checkWallCollision(Entity obj, int dx, int dy, boolean ignoreGhostHouses) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : Game.getWalls()) {
            if (!(ignoreGhostHouses && w instanceof GhostHouse) && w.getHitbox().intersects(r)) return true;
        }
        return false;
    }
}
