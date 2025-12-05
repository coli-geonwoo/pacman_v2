package game.ghostStates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import game.entities.Position;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatenModeTest {

    @DisplayName("만약 EATEN 모드에서 유령 집에 도착했을 때 HOUSE 모드로 전환된다")
    @Test
    void insideHouse() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        GhostState eatenMode = new EatenMode();

        eatenMode.insideHouse(ghost);

        assertThat(ghost.isState(State.HOUSE)).isTrue();
    }

    @DisplayName("EATEN 모드에서는 유령의 집 벽 충돌을 무시한다")
    @Test
    void ignoreGhostHouses() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        GhostState eatenMode = new EatenMode();

        boolean ignoreGhostHouses = eatenMode.ignoreGhostHouses();

        assertThat(ignoreGhostHouses).isTrue();
    }
}
