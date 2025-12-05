package game.ghostFactory;

import game.entities.Position;
import game.entities.ghosts.*;

//다른 생성자로부터 다른 구체적인 고스트를 생성하기 위한 추상 팩토리
public abstract class AbstractGhostFactory {

    public abstract Ghost makeGhost(int xPos, int yPos, Position position, Ghost target);

    public static Ghost makeByUserInputs(String input, int xPos, int yPos, Position position, Ghost target) {
        if(input.equals("blinky")) {
            return BlinkyFactory.getInstance()
                    .makeGhost(xPos, yPos, position, null);
        }

        if(input.equals("inky")) {
            return InkyFactory.getInstance()
                    .makeGhost(xPos, yPos, position, target);
        }

        if(input.equals("clyde")) {
            return ClydeFactory.getInstance()
                    .makeGhost(xPos, yPos, position, null);
        }

        if(input.equals("pinky")) {
            return PinkyFactory.getInstance()
                    .makeGhost(xPos, yPos, position, null);
        }

        throw new RuntimeException("해당하는 유령 타입 생성 팩토리 객체가 없습니다.");
    }
}

