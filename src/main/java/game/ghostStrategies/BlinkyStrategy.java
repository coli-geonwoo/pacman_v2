package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.Position;
import game.entities.ghosts.Ghost;

//블링키(붉은 유령)의 구체적인 전략
public class BlinkyStrategy extends AbstractGhostStrategy {

    public BlinkyStrategy(Position position) {
        super(position);
    }

    @Override
    public Position getChaseTargetPosition(Ghost ghost) {
        return Game.getPacmanPosition();
    }
}
