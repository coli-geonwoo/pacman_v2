package game.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import game.GameLauncher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

class GameLevelTest {

    @DisplayName("만약 초보자 모드라면 BEGINNER 모드가 반환된다")
    @Test
    void mapWithScore() {
        try(MockedStatic<GameLauncher> gameLauncherMockedStatic = mockStatic(GameLauncher.class)) {
            gameLauncherMockedStatic.when(GameLauncher::isBeginnerMode).thenReturn(true);

            GameLevel gameLevel = GameLevel.mapWithScore(10);

            assertThat(gameLevel).isEqualTo(GameLevel.BEGINNER);
        }
    }

    @DisplayName("만약 점수가 0 -1000점 사이라면 LEVEL1이 반환된다")
    @Test
    void mapWithScore_level1() {
        try(MockedStatic<GameLauncher> gameLauncherMockedStatic = mockStatic(GameLauncher.class)) {
            gameLauncherMockedStatic.when(GameLauncher::isBeginnerMode).thenReturn(false);

            GameLevel gameLevel = GameLevel.mapWithScore(1000);

            assertThat(gameLevel).isEqualTo(GameLevel.LEVEL1);
        }
    }

    @DisplayName("만약 점수가 1000 - 2000점 사이라면 LEVEL2이 반환된다")
    @ParameterizedTest
    @ValueSource(ints = {1001, 2000})
    void mapWithScore_level2(int score) {
        try(MockedStatic<GameLauncher> gameLauncherMockedStatic = mockStatic(GameLauncher.class)) {
            gameLauncherMockedStatic.when(GameLauncher::isBeginnerMode).thenReturn(false);

            GameLevel gameLevel = GameLevel.mapWithScore(score);

            assertThat(gameLevel).isEqualTo(GameLevel.LEVEL2);
        }
    }

    @DisplayName("만약 점수가 2000 - 3000점 사이라면 LEVEL3이 반환된다")
    @ParameterizedTest
    @ValueSource(ints = {2001, 3000})
    void mapWithScore_level3(int score) {
        try(MockedStatic<GameLauncher> gameLauncherMockedStatic = mockStatic(GameLauncher.class)) {
            gameLauncherMockedStatic.when(GameLauncher::isBeginnerMode).thenReturn(false);

            GameLevel gameLevel = GameLevel.mapWithScore(score);

            assertThat(gameLevel).isEqualTo(GameLevel.LEVEL3);
        }
    }

    @DisplayName("만약 점수가 3000점 이상이라면 LEVEL4이 반환된다")
    @Test
    void mapWithScore_level4() {
        try(MockedStatic<GameLauncher> gameLauncherMockedStatic = mockStatic(GameLauncher.class)) {
            gameLauncherMockedStatic.when(GameLauncher::isBeginnerMode).thenReturn(false);

            GameLevel gameLevel = GameLevel.mapWithScore(3001);

            assertThat(gameLevel).isEqualTo(GameLevel.LEVEL4);
        }
    }
}
