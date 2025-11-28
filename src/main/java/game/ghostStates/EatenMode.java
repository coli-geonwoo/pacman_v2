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

    public EatenMode() {
        super();
    }

    //유령이 집으로 돌아올 때의 전환
    @Override
    public void insideHouse(Ghost ghost) {
        ghost.switchMode(State.HOUSE);
    }

    //이 상태에서는 목표 위치는 유령집 중앙의 정사각형입니다.
    @Override
    public Position getTargetPosition(Ghost ghost) {
        return new Position(208, 200);
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

    @Override
    public boolean ignoreGhostHouses() {
        return true;
    }
}
