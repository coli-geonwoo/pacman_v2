package game.ghostStates;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;
import game.utils.WallCollisionDetector;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//팩맨에게 먹힌 유령의 구체적인 상태를 설명하는 클래스
public class EatenMode extends GhostState{

    private static final BufferedImage EATEN_SPRITE;

    static {
        try {
            EATEN_SPRITE = ImageIO.read(EatenMode.class.getClassLoader().getResource("img/ghost_eaten.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("EatenMode image could not be loaded");
        }

    }

    public EatenMode(Ghost ghost) {
        super(ghost);
    }

    //유령이 집으로 돌아올 때의 전환
    @Override
    public void insideHouse() {
        ghost.switchMode(State.HOUSE);
    }

    //이 상태에서는 목표 위치는 유령집 중앙의 정사각형입니다.
    @Override
    public Position getTargetPosition() {
        return new Position(208, 200);
    }

    //Même chose que la méthode de la classe abstraite, mais on ignore ici les collisions avec les murs de la maison des fantômes
    //추상 클래스 메서드와 동일하지만 여기서는 유령 집의 벽과의 충돌을 무시합니다.
    @Override
    public void computeNextDir() {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!ghost.onTheGrid()) return;
        if (!ghost.onGameplayWindow()) return;

        double minDist = Double.MAX_VALUE;

        if (ghost.getxSpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, -ghost.getSpd(), 0, true)) {
            double distance = Utils.getDistance(ghost.getxPos() - ghost.getSpd(), ghost.getyPos(), getTargetPosition().getX(), getTargetPosition().getY());
            if (distance < minDist) {
                new_xSpd = -ghost.getSpd();
                new_ySpd = 0;
                minDist = distance;
            }
        }
        if (ghost.getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, ghost.getSpd(), 0, true)) {
            double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpd(), ghost.getyPos(),  getTargetPosition().getX(), getTargetPosition().getY());
            if (distance < minDist) {
                new_xSpd = ghost.getSpd();
                new_ySpd = 0;
                minDist = distance;
            }
        }
        if (ghost.getySpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, -ghost.getSpd(), true)) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() - ghost.getSpd(), getTargetPosition().getX(), getTargetPosition().getY());
            if (distance < minDist) {
                new_xSpd = 0;
                new_ySpd = -ghost.getSpd();
                minDist = distance;
            }
        }
        if (ghost.getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, ghost.getSpd(), true)) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpd(), getTargetPosition().getX(), getTargetPosition().getY());
            if (distance < minDist) {
                new_xSpd = 0;
                new_ySpd = ghost.getSpd();
                minDist = distance;
            }
        }

        if (new_xSpd == 0 && new_ySpd == 0) return;

        if (java.lang.Math.abs(new_xSpd) != java.lang.Math.abs(new_ySpd)) {
            ghost.setxSpd(new_xSpd);
            ghost.setySpd(new_ySpd);
        } else {
            if (ghost.getxSpd() != 0) {
                ghost.setxSpd(0);
                ghost.setxSpd(new_ySpd);
            }else{
                ghost.setxSpd(new_xSpd);
                ghost.setySpd(0);
            }
        }
    }

    @Override
    public void render(Graphics2D g, Ghost ghost) {
        int size = ghost.getSize();
        int xPos = ghost.getxPos();
        int yPos = ghost.getyPos();
        int direction = ghost.getDirection();
        g.drawImage(EATEN_SPRITE.getSubimage(direction * size, 0, size, size), xPos, yPos,null);
    }

    @Override
    public State getState() {
        return State.EATEN;
    }
}
