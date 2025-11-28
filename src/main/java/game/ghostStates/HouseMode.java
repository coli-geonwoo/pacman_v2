package game.ghostStates;

import game.entities.Position;
import game.entities.ghosts.Ghost;

//집에 있는 유령의 구체적인 상태를 설명하는 클래스
public class HouseMode extends GhostState {

    public HouseMode() {
        super();
    }

    //Transition lorsque le fantôme est hors de sa maison
    //유령이 집 밖에 있을 때의 전환
    @Override
    public void outsideHouse(Ghost ghost) {
        ghost.switchChaseModeOrScatterMode();
    }

    //이 상태에서는 목표 위치는 유령의 집 바로 위의 사각형입니다.
    @Override
    public Position getTargetPosition(Ghost ghost) {
        return new Position(208, 168);
    }

    @Override
    public State getState() {
        return State.HOUSE;
    }

    @Override
    public boolean ignoreGhostHouses() {
        return true;
    }
}
