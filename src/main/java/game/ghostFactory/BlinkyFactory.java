package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;

//Factory concrète pour créer des fantômes Blinky
public class BlinkyFactory extends AbstractGhostFactory {

    private static final BlinkyFactory SINGLETON_INSTANCE = new BlinkyFactory();

    public static BlinkyFactory getInstance() {
        return SINGLETON_INSTANCE;
    }

    private BlinkyFactory() {}

    @Override
    public Ghost makeGhost(int xPos, int yPos, Position position) {
        return new Blinky(xPos, yPos, position);
    }
}
