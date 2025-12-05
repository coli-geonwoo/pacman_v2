package game.entities.direction;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import java.util.Comparator;
import java.util.List;

public class NextDirectionCalculator {

    private static final Comparator<CalculateResult> MIN_DISTANCE_COMPARATOR = Comparator.comparing(CalculateResult::getDistance);

    private final List<DirectionCalculateStrategy> directionCalculateStrategies;

    public NextDirectionCalculator(List<DirectionCalculateStrategy> directionCalculateStrategies) {
        this.directionCalculateStrategies = directionCalculateStrategies;
    }

    public CalculateResult calculateNextDirection(Ghost ghost, Position position, boolean ignoreGhostHouses) {
        return directionCalculateStrategies.stream()
                .map(strategy -> strategy.calculate(ghost, position, ignoreGhostHouses))
                .min(MIN_DISTANCE_COMPARATOR)
                .orElseThrow(() -> new IllegalStateException("No direction calculate strategy found"));
    }
}
