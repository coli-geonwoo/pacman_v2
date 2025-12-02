package game.entities;

import game.Game;
import game.Observer;
import game.Sujet;
import game.entities.direction.Speeds;
import game.entities.notifier.EntityNotifyMapper;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

//팩맨을 설명하는 클래스
public class Pacman extends MovingEntity implements Sujet {

    private boolean isGodMode;
    private int godModeTimer = 0;
    private int life = 0;
    private int frameCount = 0;
    private int monsterModeTimer = 0;
    private boolean isMonsterMode = false;
    private CollisionDetector collisionDetector;
    private List<Observer> observerCollection;
    private EntityNotifyMapper entityNotifyMapper;

    private BufferedImage monsterModeSprite;


    public Pacman(int xPos, int yPos, int life) {
        super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);
        observerCollection = new ArrayList<>();
        entityNotifyMapper = new EntityNotifyMapper();
        this.life = life;
        try {
            this.monsterModeSprite = ImageIO.read(getClass().getClassLoader().getResource("img/monster_pacman.png"));
        } catch (IOException e) {
            throw new RuntimeException("팩맨 이미지 로드 과정에서 문제가 생겼습니다");
        }

    }

    //여행 관리
    public void input(KeyHandler k) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!onTheGrid()) {
            return; //팩맨은 플레이 영역의 "사각형"에 있어야 합니다.
        }
        if (!onGameplayWindow()) {
            return; //팩맨은 플레이 영역에 있어야 합니다.
        }

        //누르는 키에 따라 팩맨의 방향이 달라집니다.
        if (k.k_left.isPressed && getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(this, -spd, 0)) {
            new_xSpd = -spd;
        }
        if (k.k_right.isPressed && getxSpd() <= 0 && !WallCollisionDetector.checkWallCollision(this, spd, 0)) {
            new_xSpd = spd;
        }
        if (k.k_up.isPressed && getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(this, 0, -spd)) {
            new_ySpd = -spd;
        }
        if (k.k_down.isPressed && getySpd() <= 0 && !WallCollisionDetector.checkWallCollision(this, 0, spd)) {
            new_ySpd = spd;
        }

        Speeds updateSpeeds = new Speeds(new_xSpd, new_ySpd);
        if (updateSpeeds.isStopped()) {
            return;
        }
        if (!Game.getFirstInput()) {
            Game.setFirstInput(true);
        }
        super.updateSpeed(updateSpeeds);
    }


    @Override
    public void update() {
        if(isMonsterMode) {
            monsterModeTimer++;
            if(monsterModeTimer >= 10*60) {
                isMonsterMode = false;
            }
        }

        if(isGodMode) {
            godModeTimer++;
            if(godModeTimer >= 5 * 60) {
                isGodMode = false;
            }
        }

        frameCount++;

        //모든 충돌 객체 가져오기 > 객체들에게 알림 발송
        List<Entity> allCollisionEntities = collisionDetector.getAllCollisionEntities(this);
        entityNotifyMapper.notifyCollisionEntities(allCollisionEntities, observerCollection);

        //Pacman의 다음 잠재적 위치에 벽이 없으면 그의 위치가 업데이트됩니다.
        if (!WallCollisionDetector.checkWallCollision(this, getxSpd(), getySpd())) {
            updatePosition();
        }
    }


    //monsterMode일 때와 다르게
    @Override
    public void render(Graphics2D g) {
        if (isMonsterMode && frameCount % 10 < 5) {
            g.drawImage(monsterModeSprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0,
                            size, size),
                    getxPos(), getyPos(), null);
            return;
        }

        if(isGodMode) {
            if(frameCount % 10 < 5) {
                g.drawImage(sprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0, size,
                                size),
                        getxPos(), getyPos(), null);
            }
            return;
        }

        g.drawImage(sprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size),
                getxPos(), getyPos(), null);
    }

    public void decreaseLife() {
        this.life -= 1;
    }

    public boolean isLastLife() {
        return this.life == 1;
    }

    public int getLife() {
        return life;
    }

    public boolean isMonsterMode() {
        return isMonsterMode;
    }

    public boolean isGodMode() {
        return isGodMode;
    }

    public void switchMonsterMode() {
        if (isMonsterMode) {
            monsterModeTimer = 0;
            return;
        }
        isMonsterMode = true;
        monsterModeTimer = 0;
    }

    public void switchGameMode() {
        if (isGodMode) {
            godModeTimer = 0;
            return;
        }
        isGodMode = true;
        godModeTimer = 0;
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    @Override
    public void registerObserver(Observer observer) {
        observerCollection.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);
    }
}
