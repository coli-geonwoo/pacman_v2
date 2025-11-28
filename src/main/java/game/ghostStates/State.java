package game.ghostStates;

import game.entities.ghosts.Ghost;
import java.util.function.Function;
import java.util.stream.Stream;

public enum State {

    HOUSE(HouseMode::new),
    CHASE(ChaseMode::new),
    SCATTER(ScatterMode::new),
    EATEN(EatenMode::new),
    FRIGHTENED(FrightenedMode::new),
    ;

    private final Function<Ghost, GhostState> function;

    State(Function<Ghost, GhostState> function) {
        this.function = function;
    }

    public static GhostState mapToGhostState(Ghost ghost, State state) {
        return Stream.of(values())
                .filter(value -> value == state)
                .findAny()
                .orElseThrow(() -> new RuntimeException(state.name() + "와 일치하는 상태가 없습니다"))
                .function.apply(ghost);
    }
}
