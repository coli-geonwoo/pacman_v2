package game.entities.direction;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;
import game.utils.WallCollisionDetector;

public class RightCalculateStrategy implements DirectionCalculateStrategy {

    @Override
    public CalculateResult calculate(Ghost ghost, Position targetPosition, boolean ignoreGhostHouses) {
        if (ghost.getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, ghost.getSpd(), 0, ignoreGhostHouses)) {
            double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpd(), ghost.getyPos(),
                    targetPosition.getX(), targetPosition.getY());

            return new CalculateResult(distance, new Speeds(ghost.getSpd(), 0));
        }
        return CalculateResult.DEFAULT_MAX_DISTANCE;
    }
}
