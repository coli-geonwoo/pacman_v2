package game.entities;

import game.GameLauncher;

public enum GameLevel {

    BEGINNER(-1, 0, Integer.MAX_VALUE),
    LEVEL1(1000, 20 * 60, 5 * 60),
    LEVEL2(2000, 30 * 60, 3 * 60),
    LEVEL3(3000, 40 * 60, 1 * 60),
    LEVEL4(Integer.MAX_VALUE, Integer.MAX_VALUE, 0),
    ;

    private int score;
    private int chaseModeTime;
    private int scatterModeTime;

    GameLevel(int score, int chaseModeTime, int scatterModeTime) {
        this.score = score;
        this.chaseModeTime = chaseModeTime;
        this.scatterModeTime = scatterModeTime;
    }

    public static GameLevel mapWithScore(int gameScore) {
        if (GameLauncher.isBeginnerMode()) {
            return BEGINNER;
        }

        GameLevel[] values = values();
        for (GameLevel level : values) {
            if (level.score >= gameScore) {
                return level;
            }
        }
        throw new RuntimeException("잘못된 점수입니다");
    }

    public int getChaseModeTime() {
        return chaseModeTime;
    }

    public int getScatterModeTime() {
        return scatterModeTime;
    }

    public int getScore() {
        return score;
    }
}
