package game.ghostStrategies;

import game.Game;
import game.entities.Position;
import game.utils.Utils;

//Stratégie concrète de Pinky (le fantôme rose)
public class PinkyStrategy implements IGhostStrategy {

    @Override
    public Position getChaseTargetPosition() {
        return Utils.getPointDistanceDirection2(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 64, Utils.directionConverter(Game.getPacman().getDirection()));
    }

    @Override
    //일시 정지 시, 핑키는 왼쪽 상단 사각형을 목표로 삼습니다.
    public Position getScatterTargetPosition() {
        return new Position(0,0);
    }
}
