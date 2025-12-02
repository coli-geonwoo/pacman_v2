package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//클라이드(노란 유령)의 구체적인 전략
public class ClydeStrategy extends AbstractGhostStrategy {

    public ClydeStrategy(Position position) {
        super(position);
    }

    @Override
    //클라이드는 팩맨이 반경 8칸을 넘으면 팩맨을 직접 표적으로 삼고, 그렇지 않으면 일시 정지 위치를 표적으로 삼습니다.
    public Position getChaseTargetPosition(Ghost ghost) {
        if (Utils.getDistance(ghost.getxPos(), ghost.getyPos(), Game.getPacman().getxPos(), Game.getPacman().getyPos()) >= 256) {
            return Game.getPacmanPosition();
        }else{
            return getScatterTargetPosition(ghost);
        }
    }
}
