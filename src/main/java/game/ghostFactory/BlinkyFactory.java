package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;

//Factory concrète pour créer des fantômes Blinky
public class BlinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, Position position) {
        return new Blinky(xPos, yPos, position);
    }
}
