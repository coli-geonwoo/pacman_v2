package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Pinky;

//Factory concrète pour créer des fantômes Pinky
public class PinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, Position pos) {
        return new Pinky(xPos, yPos, pos);
    }
}
