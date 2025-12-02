package game.entities.pacmanStates;

import game.entities.Pacman;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NormalMode implements PacmanState {

    @Override
    public void render(Pacman pacman, Graphics2D graphics) {
        int size = pacman.getSize();
        BufferedImage normalSprite = pacman.getSprite();
        graphics.drawImage(normalSprite.getSubimage(pacman.getImageXPos(), 0, size, size), pacman.getxPos(), pacman.getyPos(), null);
    }

    @Override
    public boolean isNormalMode() {
        return true;
    }
}
