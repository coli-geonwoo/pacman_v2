package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//Stratégie concrète d'Inky (le fantôme bleu)
public class InkyStrategy extends AbstractGhostStrategy {

    private Ghost otherGhost;

    public InkyStrategy(Ghost ghost, Position position) {
        super(position);
        this.otherGhost = ghost;
    }

    @Override
    //잉키는 블링키의 위치를 이용해 팩맨을 타겟으로 삼습니다. 블링키의 위치와 팩맨 앞의 한 칸 사이의 벡터를 구하고, 이 벡터를 팩맨 앞의 한 칸 위치에 더해 ​​잉키의 타겟을 구합니다.
    public Position getChaseTargetPosition(Ghost ghost) {
        Position pacmanFacingPosition = Utils.getPointDistanceDirection2(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 32d, Utils.directionConverter(Game.getPacman().getDirection()));
        double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition.getX(), pacmanFacingPosition.getY(), otherGhost.getxPos(), otherGhost.getyPos());
        double directionOtherGhost = Utils.getDirection(otherGhost.getxPos(), otherGhost.getyPos(), pacmanFacingPosition.getX(), pacmanFacingPosition.getY());
        int[] blinkyVectorPosition = Utils.getPointDistanceDirection(pacmanFacingPosition.getX(), pacmanFacingPosition.getY(), distanceOtherGhost, directionOtherGhost);
        return new Position(blinkyVectorPosition[0], blinkyVectorPosition[1]);
    }
}
