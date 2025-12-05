package game.entities.pacmanStates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonsterModeTest {

    @DisplayName("10초 이내일 경우, 몬스터모드가 유지된다")
    @Test
    void update() {
        MonsterMode monsterMode = new MonsterMode(0);

        PacmanState updated = monsterMode.update();

        assertThat(updated.isMonsterMode()).isTrue();
    }

    @DisplayName("10초가 지날 경우, 일반모드로 전환된다")
    @Test
    void returnToNormal() {
        MonsterMode monsterMode = new MonsterMode(60*10-1);

        PacmanState updated = monsterMode.update();

        assertThat(updated.isNormalMode()).isTrue();
    }
}
