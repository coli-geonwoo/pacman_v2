package game.ghostStrategies;

import game.Game;
import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//Stratégie concrète de Pinky (le fantôme rose)
public class PinkyStrategy extends AbstractGhostStrategy {

    public PinkyStrategy(Position position) {
        super(position);
    }

    @Override
    public Position getChaseTargetPosition(Ghost ghost) {
        return Utils.getPointDistanceDirection2(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 64, Utils.directionConverter(Game.getPacman().getDirection()));
    }
}
