package game.ghostStates;

import static org.assertj.core.api.Assertions.assertThat;

import game.entities.Position;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HouseModeTest {

    @DisplayName("CHASE 모드에서 집에 있다가 나오면 CHASE 모드로 전환된다")
    @Test
    public void chase_outsideHouse() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        ghost.switchMode(State.CHASE);
        GhostState houseMode = new HouseMode();

        houseMode.outsideHouse(ghost);

        assertThat(ghost.isState(State.CHASE)).isTrue();
    }

    @DisplayName("SCATTER 모드에서 집에 있다가 나오면 SCATTER 모드로 전환된다")
    @Test
    public void scatter_outsideHouse() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        ghost.switchMode(State.SCATTER);
        GhostState houseMode = new HouseMode();

        houseMode.outsideHouse(ghost);

        assertThat(ghost.isState(State.SCATTER)).isTrue();
    }

    @DisplayName("House 모드에서는 유령의 집 벽 충돌을 무시한다")
    @Test
    public void ignoreGhostHouses() {
        GhostState houseMode = new HouseMode();

        assertThat(houseMode.ignoreGhostHouses()).isTrue();
    }
}
