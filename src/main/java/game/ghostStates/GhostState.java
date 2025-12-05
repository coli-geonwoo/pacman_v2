package game.ghostStates;

import game.entities.Position;
import game.entities.direction.CalculateResult;
import game.entities.direction.DownCalculateStrategy;
import game.entities.direction.LeftCalculateStrategy;
import game.entities.direction.NextDirectionCalculator;
import game.entities.direction.RightCalculateStrategy;
import game.entities.direction.UpCalculateStrategy;
import game.entities.ghosts.Ghost;
import java.awt.Graphics2D;
import java.util.List;

//유령의 다양한 상태를 설명하는 추상 클래스
public abstract class GhostState {

    private final NextDirectionCalculator nextDirectionCalculator;

    public GhostState() {
        this.nextDirectionCalculator = new NextDirectionCalculator(
                List.of(
                        new UpCalculateStrategy(),
                        new DownCalculateStrategy(),
                        new LeftCalculateStrategy(),
                        new RightCalculateStrategy()
                )
        );
    }

    //유령이 이동할 다음 방향을 계산하는 방법
    public void computeNextDir(Ghost ghost) {
        if (!ghost.onTheGrid()) {
            return; //유령은 게임 영역의 "사각형"에 있어야 합니다.
        }
        if (!ghost.onGameplayWindow()) {
            return;  //유령은 놀이 공간에 있어야 합니다
        }

        Position targetPosition = getTargetPosition(ghost);
        CalculateResult minDistDirection = nextDirectionCalculator.calculateNextDirection(ghost, targetPosition,
                ignoreGhostHouses());
        ghost.updateSpeed(minDistDirection.getSpeeds());
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

    public boolean ignoreGhostHouses() {
        return false;
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

    public abstract Position getTargetPosition(Ghost ghost);

    public abstract State getState();

}
