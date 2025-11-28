package game.ghostStates;

import game.entities.Position;
import game.entities.ghosts.Ghost;

//유령이 휴식을 취하는 구체적인 상태를 설명하는 클래스
public class ScatterMode extends GhostState{
    public ScatterMode(Ghost ghost) {
        super(ghost);
    }

    //SuperGum을 먹었을 때의 전환
    @Override
    public void superPacGumEaten() {
        ghost.switchFrightenedMode();
    }

    //Transition lorsque le timer de l'état courant est terminé (il alterne entre ChaseMode et ScatterMode)
    //현재 상태 타이머가 완료되면 전환합니다(ChaseMode와 ScatterMode 사이를 번갈아 가며 전환합니다)
    @Override
    public void timerModeOver() {
        ghost.switchMode(State.CHASE);
    }

    //Dans cet état, la position ciblée dépend de la stratégie du fantôme
    //이 상태에서는 목표 위치는 유령의 전략에 따라 달라집니다.
    @Override
    public Position getTargetPosition() {
        return ghost.getStrategy().getScatterTargetPosition();
    }

    @Override
    public State getState() {
        return State.SCATTER;
    }
}
