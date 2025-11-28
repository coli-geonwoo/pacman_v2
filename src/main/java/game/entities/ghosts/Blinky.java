package game.entities.ghosts;

import game.ghostStrategies.BlinkyStrategy;

// 블링키 구체 클래스 (붉은 유령)
public class Blinky extends Ghost {
    public Blinky(int xPos, int yPos) {
        super(xPos, yPos, "blinky.png", new BlinkyStrategy());
    }
}
