package game.entities.ghosts;

import game.entities.Position;
import game.ghostStrategies.ClydeStrategy;

//클라이드 구체 등급(노란 유령)
public class Clyde extends Ghost {
    public Clyde(int xPos, int yPos, Position positon) {
        super(xPos, yPos, "clyde.png", new ClydeStrategy(positon));
    }
}
