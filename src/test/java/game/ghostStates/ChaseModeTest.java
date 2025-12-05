package game.ghostStates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

import game.Game;
import game.GameLauncher;
import game.entities.GameLevel;
import game.entities.Pacman;
import game.entities.Position;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class ChaseModeTest {


    @DisplayName("추적 모드에서 팩맨이 슈퍼팩검을 먹으면 겁먹은 모드로 전환된다")
    @Test
    void superPacGumEaten() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        ChaseMode chaseMode = new ChaseMode();

        chaseMode.superPacGumEaten(ghost);

        assertThat(ghost.isState(State.FRIGHTENED)).isTrue();
    }

    @DisplayName("추적 모드에서 시간이 다 되면 분산모드로 전환된다")
    @Test
    void timerModeOver() {
        Ghost ghost = new Blinky(1, 1, new Position(1, 1));
        ChaseMode chaseMode = new ChaseMode();

        chaseMode.timerModeOver(ghost);

        assertThat(ghost.isState(State.SCATTER)).isTrue();
    }

    @DisplayName("팩맨이 몬스터 모드일 경우, 추적모드에서도 EATEN 모드로 전환된다")
    @Test
    void eaten() {

        try(MockedStatic<Game> gameMock = mockStatic(Game.class)) {
            Pacman pacman = Mockito.mock(Pacman.class);
            Mockito.when(pacman.isMonsterMode()).thenReturn(true);
            gameMock.when(Game::getPacman).thenReturn(pacman);
            Ghost ghost = new Blinky(1, 1, new Position(1, 1));
            ChaseMode chaseMode = new ChaseMode();

            chaseMode.eaten(ghost);

            assertThat(ghost.isState(State.EATEN)).isTrue();
        }
    }
}
