package game.ghostStrategies;

import game.entities.Position;
import game.entities.ghosts.Ghost;

public abstract class AbstractGhostStrategy implements IGhostStrategy {

    private final Position position;

    public AbstractGhostStrategy(Position position) {
        this.position = position;
    }

    @Override
    public Position getScatterTargetPosition(Ghost ghost) {
        return this.position;
    }
}
