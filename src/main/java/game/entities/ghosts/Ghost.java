package game.entities.ghosts;

import game.Game;
import game.entities.MovingEntity;
import game.ghostStates.*;
import game.ghostStrategies.IGhostStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// 귀신을 묘사하기 위한 추상적인 분류
public abstract class Ghost extends MovingEntity {
    protected GhostState state;

    protected final GhostState chaseMode;
    protected final GhostState scatterMode;
    protected final GhostState frightenedMode;
    protected final GhostState eatenMode;
    protected final GhostState houseMode;

    protected int modeTimer = 0;
    protected int frightenedTimer = 0;
    protected boolean isChasing = false;

    protected static BufferedImage frightenedSprite1;
    protected static BufferedImage frightenedSprite2;
    protected static BufferedImage eatenSprite;

    protected IGhostStrategy strategy;

    public Ghost(int xPos, int yPos, String spriteName) {
        super(32, xPos, yPos, 2, spriteName, 2, 0.1f);

        //유령의 다양한 상태 생성
        chaseMode = new ChaseMode(this);
        scatterMode = new ScatterMode(this);
        frightenedMode = new FrightenedMode(this);
        eatenMode = new EatenMode(this);
        houseMode = new HouseMode(this);

        state = houseMode; //초기 상태

        try {
            frightenedSprite1 = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_frightened.png"));
            frightenedSprite2 = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_frightened_2.png"));
            eatenSprite = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_eaten.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Méthodes pour les transitions entre les différents états
    //다른 상태 간 전환을 위한 방법
    public void switchChaseMode() {
        state = chaseMode;
    }
    public void switchScatterMode() {
        state = scatterMode;
    }

    public void switchFrightenedMode() {
        frightenedTimer = 0;
        state = frightenedMode;
    }

    public void switchEatenMode() {
        state = eatenMode;
    }

    public void switchHouseMode() {
        state = houseMode;
    }

    public void switchChaseModeOrScatterMode() {
        if (isChasing) {
            switchChaseMode();
        }else{
            switchScatterMode();
        }
    }

    public IGhostStrategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(IGhostStrategy strategy) {
        this.strategy = strategy;
    }

    public GhostState getState() {
        return state;
    }

    @Override
    public void update() {
        //유령은 플레이어가 움직이기 전까지 움직이지 않습니다.
        if (!Game.getFirstInput()) return;

        //Si le fantôme est dans l'état effrayé, un timer de 7s se lance, et l'état sera notifié ensuite afin d'appliquer la transition adéquate
        //유령이 겁먹은 상태이면 7초 타이머가 시작되고, 그 상태를 감지하여 적절한 전환을 적용합니다.
        if (state == frightenedMode) {
            frightenedTimer++;

            if (frightenedTimer >= (60 * 7)) {
                state.timerFrightenedModeOver();
            }
        }

        //Les fantômes alternent entre l'état chaseMode et scatterMode avec un timer
        //Si le fantôme est dans l'état chaseMode ou scatterMode, un timer se lance, et au bout de 5s ou 20s selon l'état, l'état est notifié ensuite afin d'appliquer la transition adéquate
        //유령은 타이머를 사용하여 chaseMode와 scatterMode 사이를 번갈아 가며 작동합니다.
        //고스트가 chaseMode나 scatterMode에 있는 경우 타이머가 시작되고, 상태에 따라 5초 또는 20초 후에 해당 상태에 알림이 전송되어 적절한 전환이 적용됩니다.
        if (state == chaseMode || state == scatterMode) {
            modeTimer++;

            if ((isChasing && modeTimer >= (60 * 20)) || (!isChasing && modeTimer >= (60 * 5))) {
                state.timerModeOver();
                isChasing = !isChasing;
            }
        }

        //Si le fantôme est sur la case juste au dessus de sa maison, l'état est notifié afin d'appliquer la transition adéquate
        //유령이 자신의 집 바로 위 칸에 있는 경우, 해당 주에 알림을 보내 적절한 전환을 적용합니다.
        if (xPos == 208 && yPos == 168) {
            state.outsideHouse();
        }

        //Si le fantôme est sur la case au milieu sa maison, l'état est notifié afin d'appliquer la transition adéquate
        //유령이 집 중앙의 칸에 있는 경우, 해당 상태에 알림을 보내 적절한 전환을 적용합니다.
        if (xPos == 208 && yPos == 200) {
            state.insideHouse();
        }

        //Selon l'état, le fantôme calcule sa prochaine direction, et sa position est ensuite mise à jour
        //유령은 상태에 따라 다음 방향을 계산하고, 그에 따라 위치가 업데이트됩니다.
        state.computeNextDir();
        updatePosition();
    }

    @Override
    public void render(Graphics2D g) {
        //Différents sprites sont utilisés selon l'état du fantôme (après réflexion, il aurait peut être été plus judicieux de faire une méthode "render" dans GhostState)
        //고스트의 상태에 따라 다른 스프라이트가 사용됩니다(반성해 보면 GhostState 내에 "렌더링" 메서드를 만드는 것이 더 현명했을 수도 있음)
        if (state == frightenedMode) {
            if (frightenedTimer <= (60 * 5) || frightenedTimer%20 > 10) {
                g.drawImage(frightenedSprite1.getSubimage((int)subimage * size, 0, size, size), this.xPos, this.yPos,null);
            }else{
                g.drawImage(frightenedSprite2.getSubimage((int)subimage * size, 0, size, size), this.xPos, this.yPos,null);
            }
        }else if (state == eatenMode) {
            g.drawImage(eatenSprite.getSubimage(direction * size, 0, size, size), this.xPos, this.yPos,null);
        }else{
            g.drawImage(sprite.getSubimage((int)subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size), this.xPos, this.yPos,null);
        }

    }
}
