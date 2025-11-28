package game.ghostStates;

import game.entities.ghosts.Ghost;
import java.util.function.Function;
import java.util.stream.Stream;

public enum State {

    HOUSE(new HouseMode()),
    CHASE(new ChaseMode()),
    SCATTER(new ScatterMode()),
    EATEN(new EatenMode()),
    FRIGHTENED(new FrightenedMode()),
    ;

    private final GhostState state;

    State(GhostState state) {
        this.state = state;
    }

    public static GhostState mapToGhostState(State state) {
        return Stream.of(values())
                .filter(value -> value == state)
                .findAny()
                .orElseThrow(() -> new RuntimeException(state.name() + "와 일치하는 상태가 없습니다"))
                .state;
    }
}
