package game.ghostStates;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;
import game.utils.WallCollisionDetector;
import java.awt.Graphics2D;

//Classe abstrate pour décrire les différents états de fantômes
//유령의 다양한 상태를 설명하는 추상 클래스
public abstract class GhostState {

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
    public void computeNextDir(Ghost ghost) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!ghost.onTheGrid()) {
            return; //유령은 게임 영역의 "사각형"에 있어야 합니다.
        }
        if (!ghost.onGameplayWindow()) {
            return;  //유령은 놀이 공간에 있어야 합니다
        }

        double minDist = Double.MAX_VALUE; //유령과 대상 사이의 최소 현재 거리는 다음 방향에 따라 달라집니다.

        //유령이 현재 왼쪽으로 이동하고 있고 왼쪽에 벽이 없다면...
        if (ghost.getxSpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, -ghost.getSpd(), 0, ignoreGhostHouses())) {
            //우리는 유령이 왼쪽으로 이동한다면 목표 위치와 유령의 잠재적 위치 사이의 거리를 살펴봅니다.
            double distance = Utils.getDistance(ghost.getxPos() - ghost.getSpd(), ghost.getyPos(),
                    getTargetPosition(ghost).getX(), getTargetPosition(ghost).getY());

            //이 거리가 현재 최소 거리보다 작으면 유령이 왼쪽으로 이동한다고 말하고 최소 거리를 업데이트합니다.
            if (distance < minDist) {
                new_xSpd = -ghost.getSpd();
                new_ySpd = 0;
                minDist = distance;
            }
        }

        //Même chose en testant vers la droite
        //오른쪽으로 테스트할 때도 마찬가지입니다.
        if (ghost.getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, ghost.getSpd(), 0, ignoreGhostHouses())) {
            double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpd(), ghost.getyPos(),
                    getTargetPosition(ghost).getX(), getTargetPosition(ghost).getY());
            if (distance < minDist) {
                new_xSpd = ghost.getSpd();
                new_ySpd = 0;
                minDist = distance;
            }
        }

        //위쪽으로 테스트할 때도 마찬가지입니다.
        if (ghost.getySpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, -ghost.getSpd(), ignoreGhostHouses())) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() - ghost.getSpd(),
                    getTargetPosition(ghost).getX(), getTargetPosition(ghost).getY());
            if (distance < minDist) {
                new_xSpd = 0;
                new_ySpd = -ghost.getSpd();
                minDist = distance;
            }
        }

        //아래로 테스트할 때도 마찬가지입니다.
        if (ghost.getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, ghost.getSpd(), ignoreGhostHouses())) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpd(),
                    getTargetPosition(ghost).getX(), getTargetPosition(ghost).getY());
            if (distance < minDist) {
                new_xSpd = 0;
                new_ySpd = ghost.getSpd();
                minDist = distance;
            }
        }

        if (new_xSpd == 0 && new_ySpd == 0) {
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
