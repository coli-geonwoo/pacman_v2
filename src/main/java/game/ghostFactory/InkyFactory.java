package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;

//Factory concrète pour créer des fantômes Inky
public class InkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, Position position) {
        return new Inky(xPos, yPos, position);
    }
}
