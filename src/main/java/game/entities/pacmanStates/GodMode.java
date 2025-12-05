package game.entities.pacmanStates;

import game.entities.Pacman;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GodMode implements PacmanState {

    private static int GOD_MODE_TIME = 5 * 60;

    private int godModeTimer;

    public GodMode() {
        this.godModeTimer = 0;
    }

    @Override
    public void render(Pacman pacman, Graphics2D graphics) {
        int frameCount = pacman.getFrameCount();
        int size = pacman.getSize();
        BufferedImage normalSprite = pacman.getSprite();
        if(frameCount % 10 < 5) {
            graphics.drawImage(normalSprite.getSubimage(pacman.getImageXPos(), 0, size, size),
                    pacman.getxPos(), pacman.getyPos(), null);
        }
    }

    @Override
    public PacmanState update() {
        godModeTimer++;
        if (godModeTimer >= GOD_MODE_TIME) {
            return new NormalMode();
        }
        return this;
    }

    @Override
    public void resetTimer() {
        godModeTimer = 0;
    }

    @Override
    public boolean isGodMode() {
        return true;
    }
}
