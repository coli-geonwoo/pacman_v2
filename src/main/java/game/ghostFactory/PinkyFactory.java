package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Pinky;

//Factory concrète pour créer des fantômes Pinky
public class PinkyFactory extends AbstractGhostFactory {

    private static final PinkyFactory SINGLETON_INSTANCE = new PinkyFactory();

    public static PinkyFactory getInstance() {
        return SINGLETON_INSTANCE;
    }

    private PinkyFactory() {}

    @Override
    public Ghost makeGhost(int xPos, int yPos, Position pos) {
        return new Pinky(xPos, yPos, pos);
    }
}
