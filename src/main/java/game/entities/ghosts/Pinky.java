package game.entities.ghosts;

import game.ghostStrategies.PinkyStrategy;

//핑키(분홍 유령)의 구체 클래스
public class Pinky extends Ghost {
    public Pinky(int xPos, int yPos) {
        super(xPos, yPos, "pinky.png");
        setStrategy(new PinkyStrategy());
    }
}
