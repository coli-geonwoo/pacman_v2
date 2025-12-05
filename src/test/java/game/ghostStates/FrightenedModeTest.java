package game.ghostStates;

import static org.assertj.core.api.Assertions.assertThat;

import game.entities.Position;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FrightenedModeTest {


    @DisplayName("겁먹은 모드에서 유령이 팩맨과 충돌하면 EATEN 모드로 전환한다")
    @Test
    void eaten() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        GhostState frightenedMode = new FrightenedMode();

        frightenedMode.eaten(ghost);

        assertThat(ghost.isState(State.EATEN)).isTrue();
    }

    @DisplayName("CHASE 모드에서 겁먹은 상태였다가 시간이 다되면 CHASE 모드로 전환된다")
    @Test
    public void chase_timerFrightenedModeOver() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        ghost.switchMode(State.CHASE);
        GhostState frightenedMode = new FrightenedMode();

        frightenedMode.timerFrightenedModeOver(ghost);

        assertThat(ghost.isState(State.CHASE)).isTrue();
    }

    @DisplayName("SCATTER 모드에서 겁먹은 상태였다가 시간이 다되면 SCATTER 모드로 전환된다")
    @Test
    public void scatter_timerFrightenedModeOver() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        ghost.switchMode(State.SCATTER);
        GhostState frightenedMode = new FrightenedMode();

        frightenedMode.timerFrightenedModeOver(ghost);

        assertThat(ghost.isState(State.SCATTER)).isTrue();
    }
}
