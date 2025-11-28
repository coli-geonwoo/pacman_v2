package game.ghostStates;

import game.entities.Position;
import game.entities.direction.CalculateResult;
import game.entities.direction.NextDirectionCalculator;
import game.entities.direction.Speeds;
import game.entities.ghosts.Ghost;
import game.utils.Utils;
import game.utils.WallCollisionDetector;
import java.awt.Graphics2D;

//Classe abstrate pour décrire les différents états de fantômes
//유령의 다양한 상태를 설명하는 추상 클래스
public abstract class GhostState {

    private final NextDirectionCalculator nextDirectionCalculator;

    public GhostState() {
        this.nextDirectionCalculator = new NextDirectionCalculator();
    }

    //한 상태에서 다른 상태로의 다양한 가능한 전환
    public void superPacGumEaten(Ghost ghost) {
    }

    public void timerModeOver(Ghost ghost) {
    }

    public void timerFrightenedModeOver(Ghost ghost) {
    }

    public void eaten(Ghost ghost) {
    }

    public void outsideHouse(Ghost ghost) {
    }

    public void insideHouse(Ghost ghost) {
    }

    //유령이 이동할 다음 방향을 계산하는 방법
    //TODO 리팩터링
    public void computeNextDir(Ghost ghost) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!ghost.onTheGrid()) {
            return; //유령은 게임 영역의 "사각형"에 있어야 합니다.
        }
        if (!ghost.onGameplayWindow()) {
            return;  //유령은 놀이 공간에 있어야 합니다
        }

        Position targetPosition = getTargetPosition(ghost);
        CalculateResult minDistDirection = nextDirectionCalculator.calculateNextDirection(ghost, targetPosition, ignoreGhostHouses());
        Speeds speeds = minDistDirection.getSpeeds();
        new_xSpd = speeds.getxSpeed();
        new_ySpd = speeds.getySpeed();

        if (speeds.isSame(0, 0)) {
            return;
        }

        //모든 케이스가 테스트된 후, 고스트의 방향을 변경합니다(이 방향은 수평 속도와 수직 속도로 정의되므로 대각선으로 이동할 수 없도록 검사를 수행합니다)
        if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
            ghost.setxSpd(new_xSpd);
            ghost.setySpd(new_ySpd);
        } else {
            if (new_xSpd != 0) {
                ghost.setxSpd(0);
                ghost.setxSpd(new_ySpd);
            } else {
                ghost.setxSpd(new_xSpd);
                ghost.setySpd(0);
            }
        }
    }

    public void render(Graphics2D g, Ghost ghost) {
        g.drawImage(
                ghost.getSprite().getSubimage((int) ghost.getSubimage() * ghost.getSize()
                                + ghost.getDirection() * ghost.getSize() * ghost.getNbSubimagesPerCycle(), 0, ghost.getSize(),
                        ghost.getSize()),
                ghost.getxPos(),
                ghost.getyPos(),
                null
        );
    }

    public boolean isSame(State state) {
        return this.getState() == state;
    }

    public abstract Position getTargetPosition(Ghost ghost);

    public abstract State getState();

    public boolean ignoreGhostHouses() {
        return false;
    }

}
