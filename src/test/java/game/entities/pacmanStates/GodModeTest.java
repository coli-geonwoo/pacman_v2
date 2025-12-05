package game.entities.pacmanStates;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GodModeTest {

    @DisplayName("5초 이내일 경우, 무적모드가 유지된다")
    @Test
    void update() {
        GodMode godMode = new GodMode(0);

        PacmanState updated = godMode.update();

        assertThat(updated.isGodMode()).isTrue();
    }

    @DisplayName("5초가 지날 경우, 일반모드로 전환된다")
    @Test
    void returnToNormal() {
        GodMode godMode = new GodMode(60 * 5 - 1);

        PacmanState updated = godMode.update();

        assertThat(updated.isNormalMode()).isTrue();
    }
}
