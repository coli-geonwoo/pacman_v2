package game.entities.direction;

import game.entities.Position;
import game.entities.ghosts.Ghost;

public interface DirectionCalculateStrategy {

    CalculateResult calculate(Ghost ghost, Position targetPosition, boolean ignoreGhostHouses);
}
