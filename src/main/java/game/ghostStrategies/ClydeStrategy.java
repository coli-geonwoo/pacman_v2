package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//Stratégie concrète de Clyde (le fantôme jaune)
//클라이드(노란 유령)의 구체적인 전략
public class ClydeStrategy implements IGhostStrategy{
    private Ghost ghost;
    public ClydeStrategy(Ghost ghost) {
        this.ghost = ghost;
    }

    //Clyde cible directement Pacman s'il est au dela d'un rayon de 8 cases, et sinon il cible sa position de pause
    //클라이드는 팩맨이 반경 8칸을 넘으면 팩맨을 직접 표적으로 삼고, 그렇지 않으면 일시 정지 위치를 표적으로 삼습니다.
    @Override
    public int[] getChaseTargetPosition() {
        if (Utils.getDistance(ghost.getxPos(), ghost.getyPos(), Game.getPacman().getxPos(), Game.getPacman().getyPos()) >= 256) {
            int[] position = new int[2];
            position[0] = Game.getPacman().getxPos();
            position[1] = Game.getPacman().getyPos();
            return position;
        }else{
            return getScatterTargetPosition();
        }
    }

    //En pause, Clyde cible la case en bas à gauche
    //일시 정지 시 Clyde는 왼쪽 하단 사각형을 타겟으로 합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = GameplayPanel.height;
        return position;
    }
}
