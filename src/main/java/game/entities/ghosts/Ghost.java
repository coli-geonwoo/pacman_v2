package game.entities.ghosts;

import game.Game;
import game.entities.MovingEntity;
import game.entities.Position;
import game.ghostStates.GhostState;
import game.ghostStates.State;
import game.ghostStrategies.IGhostStrategy;
import java.awt.Graphics2D;

// 귀신을 묘사하기 위한 추상적인 분류
public abstract class Ghost extends MovingEntity {

    protected GhostState state;
    protected int modeTimer = 0;
    protected int frightenedTimer = 0;
    protected boolean isChasing = false;
    protected final IGhostStrategy strategy;

    public Ghost(int xPos, int yPos, String spriteName, IGhostStrategy strategy) {
        super(32, xPos, yPos, 2, spriteName, 2, 0.1f);
        this.state = State.mapToGhostState(State.HOUSE);
        this.strategy = strategy;
    }

    public void eaten() {
        state.eaten(this);
    }

    public void superPacGumEaten() {
        state.superPacGumEaten(this);
    }

    //다른 상태 간 전환을 위한 방법
    public void switchMode(State targetState) {
        state = State.mapToGhostState(targetState);
    }

    public void switchFrightenedMode() {
        frightenedTimer = 0;
        state = State.mapToGhostState(State.FRIGHTENED);
    }

    public void switchChaseModeOrScatterMode() {
        if (isChasing) {
            switchMode(State.CHASE);
        } else {
            switchMode(State.SCATTER);
        }
    }

    @Override
    public void update() {
        //유령은 플레이어가 움직이기 전까지 움직이지 않습니다.
        if (!Game.getFirstInput()) {
            return;
        }

        //유령이 겁먹은 상태이면 7초 타이머가 시작되고, 그 상태를 감지하여 적절한 전환을 적용합니다.
        if (state.isSame(State.FRIGHTENED)) {
            frightenedTimer++;

            if (frightenedTimer >= (60 * 7)) {
                state.timerFrightenedModeOver(this);
            }
        }

        //유령은 타이머를 사용하여 chaseMode와 scatterMode 사이를 번갈아 가며 작동합니다.
        //고스트가 chaseMode나 scatterMode에 있는 경우 타이머가 시작되고, 상태에 따라 5초 또는 20초 후에 해당 상태에 알림이 전송되어 적절한 전환이 적용됩니다.
        if (state.isSame(State.CHASE) || state.isSame(State.SCATTER)) {
            modeTimer++;

            if ((isChasing && modeTimer >= (60 * 20)) || (!isChasing && modeTimer >= (60 * 5))) {
                state.timerModeOver(this);
                isChasing = !isChasing;
            }
        }

        //유령이 자신의 집 바로 위 칸에 있는 경우, 해당 주에 알림을 보내 적절한 전환을 적용합니다.
        if (xPos == 208 && yPos == 168) {
            state.outsideHouse(this);
        }

        //유령이 집 중앙의 칸에 있는 경우, 해당 상태에 알림을 보내 적절한 전환을 적용합니다.
        if (xPos == 208 && yPos == 200) {
            state.insideHouse(this);
        }

        //유령은 상태에 따라 다음 방향을 계산하고, 그에 따라 위치가 업데이트됩니다.
        state.computeNextDir(this);
        updatePosition();
    }

    @Override
    public void render(Graphics2D g) {
        state.render(g, this);
    }

    public int getFrightenedTimer() {
        return frightenedTimer;
    }

    public Position getScatterTargetPosition() {
        return this.strategy.getScatterTargetPosition(this);
    }

    public Position getChaseTargetPosition() {
        return this.strategy.getChaseTargetPosition(this);
    }

    public GhostState getState() {
        return state;
    }
}
