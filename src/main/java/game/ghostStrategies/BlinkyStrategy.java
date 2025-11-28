package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.Position;

//블링키(붉은 유령)의 구체적인 전략
public class BlinkyStrategy implements IGhostStrategy{

    @Override
    public Position getChaseTargetPosition() {
        return Game.getPacmanPosition();
    }

    @Override
    //일시 정지 시 Blinky는 오른쪽 상단 모서리에 있는 사각형을 타겟팅합니다.
    public Position getScatterTargetPosition() {
        return new Position(GameplayPanel.width, 0);
    }
}
