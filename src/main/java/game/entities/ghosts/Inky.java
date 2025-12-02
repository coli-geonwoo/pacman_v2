package game.entities.ghosts;

import game.Game;
import game.entities.Position;
import game.ghostStrategies.InkyStrategy;

//잉키의 구체적 수업(푸른 유령)
public class Inky extends Ghost {
    public Inky(int xPos, int yPos, Position position) {
        super(xPos, yPos, "inky.png", new InkyStrategy(Game.getTargetGhost(), position));
    }
}
