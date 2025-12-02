package game.ghostStates;

import game.Game;
import game.entities.Pacman;
import game.entities.Position;
import game.entities.ghosts.Ghost;

//팩맨을 쫓는 유령의 구체적인 상태를 설명하는 클래스
public class ChaseMode extends GhostState {

    public ChaseMode() {
        super();
    }

    //SuperPacGum을 먹었을 때의 전환
    @Override
    public void superPacGumEaten(Ghost ghost) {
        ghost.switchFrightenedMode();
    }

    //현재 상태 타이머가 완료되면 전환합니다(ChaseMode와 ScatterMode 사이를 번갈아 가며 전환합니다)
    @Override
    public void timerModeOver(Ghost ghost) {
        ghost.switchMode(State.SCATTER);
    }

    //이 상태에서는 목표 위치가 유령의 전략에 따라 달라집니다.
    @Override
    public Position getTargetPosition(Ghost ghost) {
        return ghost.getChaseTargetPosition();
    }

    @Override
    public void eaten(Ghost ghost) {
        Pacman pacman = Game.getPacman();
        if(pacman.isMonsterMode()) {
            ghost.switchMode(State.EATEN);
        }
    }

    @Override
    public State getState() {
        return State.CHASE;
    }
}
