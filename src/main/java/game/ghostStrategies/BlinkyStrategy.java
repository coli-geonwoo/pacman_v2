package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;

//Stratégie concrète de Blinky (le fantôme rouge)
//블링키(붉은 유령)의 구체적인 전략
public class BlinkyStrategy implements IGhostStrategy{
    //Blinky cible directement la position de Pacman
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        position[0] = Game.getPacman().getxPos();
        position[1] = Game.getPacman().getyPos();
        return position;
    }

    //En pause, Blinky cible la case en haut à droite
    //일시 정지 시 Blinky는 오른쪽 상단 모서리에 있는 사각형을 타겟팅합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GameplayPanel.width;
        position[1] = 0;
        return position;
    }
}
