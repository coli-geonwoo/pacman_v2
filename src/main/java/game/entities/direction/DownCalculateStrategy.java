package game.entities.direction;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.utils.Utils;
import game.utils.WallCollisionDetector;

public class DownCalculateStrategy implements DirectionCalculateStrategy{

    @Override
    public CalculateResult calculate(Ghost ghost, Position targetPosition, boolean ignoreGhostHouses) {
        if (ghost.getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, ghost.getSpd(), ignoreGhostHouses)) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpd(),
                    targetPosition.getX(), targetPosition.getY());
            return new CalculateResult(distance, new Speeds(0, ghost.getSpd()));
        }
        return CalculateResult.DEFAULT_MAX_DISTANCE;
    }
}
