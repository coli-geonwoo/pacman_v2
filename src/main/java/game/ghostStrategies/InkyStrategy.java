package game.ghostStrategies;

import game.Game;
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
    public Position getChaseTargetPosition(Ghost ghost) {
        Position pacmanFacingPosition = Utils.getPointDistanceDirection2(Game.getPacman().getxPos(),
                Game.getPacman().getyPos(), 32d, Utils.directionConverter(Game.getPacman().getDirection()));
        double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition.getX(), pacmanFacingPosition.getY(),
                otherGhost.getxPos(), otherGhost.getyPos());
        double directionOtherGhost = Utils.getDirection(otherGhost.getxPos(), otherGhost.getyPos(),
                pacmanFacingPosition.getX(), pacmanFacingPosition.getY());
        Position blinkyVectorPosition = Utils.getPointDistanceDirection2(pacmanFacingPosition.getX(),
                pacmanFacingPosition.getY(), distanceOtherGhost, directionOtherGhost);
        return new Position(blinkyVectorPosition.getX(), blinkyVectorPosition.getY());
    }
}
