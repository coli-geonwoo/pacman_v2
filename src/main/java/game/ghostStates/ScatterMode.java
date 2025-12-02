package game.ghostStates;

import game.Game;
import game.entities.Pacman;
import game.entities.Position;
import game.entities.ghosts.Ghost;

//유령이 휴식을 취하는 구체적인 상태를 설명하는 클래스
public class ScatterMode extends GhostState{

    public ScatterMode() {
        super();
    }

    //SuperGum을 먹었을 때의 전환
    @Override
    public void superPacGumEaten(Ghost ghost) {
        ghost.switchFrightenedMode();
    }

    //현재 상태 타이머가 완료되면 전환합니다(ChaseMode와 ScatterMode 사이를 번갈아 가며 전환합니다)
    @Override
    public void timerModeOver(Ghost ghost) {
        ghost.switchMode(State.CHASE);
    }

    //이 상태에서는 목표 위치는 유령의 전략에 따라 달라집니다.
    @Override
    public Position getTargetPosition(Ghost ghost) {
        return ghost.getScatterTargetPosition();
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
        return State.SCATTER;
    }
}
