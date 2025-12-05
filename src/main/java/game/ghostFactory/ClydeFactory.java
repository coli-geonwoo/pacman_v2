package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;

public class ClydeFactory extends AbstractGhostFactory {

    private static final ClydeFactory SINGLETON_INSTANCE = new ClydeFactory();

    public static ClydeFactory getInstance() {
        return SINGLETON_INSTANCE;
    }

    private ClydeFactory() {}

    @Override
    public Ghost makeGhost(int xPos, int yPos, Position position, Ghost target) {
        return new Clyde(xPos, yPos, position);
    }
}
