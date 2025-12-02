package game.entities.pacmanStates;

import game.entities.Pacman;
import java.awt.Graphics2D;

public interface PacmanState {

    void render(Pacman pacman, Graphics2D graphics);

    default void resetTimer() {
    }

    default PacmanState update() {
        return this;
    }

    default boolean isGodMode() {
        return false;
    }

    default boolean isMonsterMode() {
        return false;
    }

    default boolean isNormalMode() {
        return false;
    }
}
