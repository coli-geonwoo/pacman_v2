package game.entities.pacmanStates;

import game.entities.Pacman;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MonsterMode implements PacmanState {

    private static int MONSTER_MODE_TIME = 10 * 60;

    private int monsterModeTimer;

    public MonsterMode(int monsterModeTimer) {
        this.monsterModeTimer = monsterModeTimer;
    }

    @Override
    public void render(Pacman pacman, Graphics2D graphics) {
        int frameCount = pacman.getFrameCount();
        int size = pacman.getSize();
        BufferedImage monsterModeSprite = pacman.getMonsterModeSprite();
        BufferedImage normalSprite = pacman.getSprite();

        if (frameCount % 10 < 5) {
            graphics.drawImage(monsterModeSprite.getSubimage(pacman.getImageXPos(), 0, size, size), pacman.getxPos(),
                    pacman.getyPos(), null);
            return;
        }
        graphics.drawImage(normalSprite.getSubimage(pacman.getImageXPos(), 0, size, size), pacman.getxPos(),
                pacman.getyPos(), null);
    }

    @Override
    public PacmanState update() {
        monsterModeTimer++;
        if (monsterModeTimer >= MONSTER_MODE_TIME) {
            return new NormalMode();
        }
        return this;
    }

    @Override
    public void resetTimer() {
        monsterModeTimer = 0;
    }

    @Override
    public boolean isMonsterMode() {
        return true;
    }
}
