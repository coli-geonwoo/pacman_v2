package game.ghostFactory;

import game.entities.ghosts.*;

//다른 생성자로부터 다른 구체적인 고스트를 생성하기 위한 추상 팩토리
public abstract class AbstractGhostFactory {
    public abstract Ghost makeGhost(int xPos, int yPos);
}

