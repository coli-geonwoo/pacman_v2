package game.ghostStates;

import game.entities.ghosts.Ghost;
import game.utils.Utils;
import jdk.jshell.execution.Util;

//Pac-Man이 PowerPac-Gum을 먹은 후 겁에 질린 유령의 구체적인 상태를 설명하는 클래스
public class FrightenedMode extends GhostState{
    public FrightenedMode(Ghost ghost) {
        super(ghost);
    }

    //유령이 먹혔을 때의 전환
    @Override
    public void eaten() {
        ghost.switchEatenMode();
    }

    //무서운 상태 타이머가 완료되면 전환
    @Override
    public void timerFrightenedModeOver() {
        ghost.switchChaseModeOrScatterMode();
    }

    //Dans cet état, la position ciblée est une case aléatoire autour du fantôme
    //이 상태에서는 목표 위치는 고스트 주변의 임의의 셀입니다.
    @Override
    public int[] getTargetPosition(){
        int[] position = new int[2];

        boolean randomAxis = Utils.randomBool();
        position[0] = ghost.getxPos() + (randomAxis ? Utils.randomInt(-1,1) * 32 : 0);
        position[1] = ghost.getyPos() + (!randomAxis ? Utils.randomInt(-1,1) * 32 : 0);
        return position;
    }
}
