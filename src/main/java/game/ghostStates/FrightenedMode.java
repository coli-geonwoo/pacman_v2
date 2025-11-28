package game.ghostStates;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//Pac-Man이 PowerPac-Gum을 먹은 후 겁에 질린 유령의 구체적인 상태를 설명하는 클래스
public class FrightenedMode extends GhostState {

    private static final BufferedImage FRIGHTEN_SPRITE_ONE;
    private static final BufferedImage FRIGHTEN_SPRITE_TWO;

    static {
        try {
            FRIGHTEN_SPRITE_ONE = ImageIO.read(
                    FrightenedMode.class.getClassLoader().getResource("img/ghost_frightened.png"));
            FRIGHTEN_SPRITE_TWO = ImageIO.read(
                    FrightenedMode.class.getClassLoader().getResource("img/ghost_frightened_2.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("FrightenedMode image could not be loaded");
        }
    }

    public FrightenedMode() {
        super();
    }

    //유령이 먹혔을 때의 전환
    @Override
    public void eaten(Ghost ghost) {
        ghost.switchMode(State.EATEN);
    }

    //무서운 상태 타이머가 완료되면 전환
    @Override
    public void timerFrightenedModeOver(Ghost ghost) {
        ghost.switchChaseModeOrScatterMode();
    }

    //이 상태에서는 목표 위치는 고스트 주변의 임의의 셀입니다.
    @Override
    public Position getTargetPosition(Ghost ghost) {
        boolean randomAxis = Utils.randomBool();
        return new Position(
                ghost.getxPos() + (randomAxis ? Utils.randomInt(-1, 1) * 32 : 0),
                ghost.getyPos() + (!randomAxis ? Utils.randomInt(-1, 1) * 32 : 0)
        );
    }

    @Override
    public void render(Graphics2D g, Ghost ghost) {
        int frightenedTimer = ghost.getFrightenedTimer();
        int subimage = (int) ghost.getSubimage();
        int size = ghost.getSize();
        int xPos = ghost.getxPos();
        int yPos = ghost.getyPos();

        if (frightenedTimer <= (60 * 5) || frightenedTimer % 20 > 10) {
            g.drawImage(FRIGHTEN_SPRITE_ONE.getSubimage(subimage * size, 0, size, size), xPos, yPos, null);
        } else {
            g.drawImage(FRIGHTEN_SPRITE_TWO.getSubimage(subimage * size, 0, size, size), xPos, yPos, null);
        }
    }

    @Override
    public State getState() {
        return State.FRIGHTENED;
    }
}
