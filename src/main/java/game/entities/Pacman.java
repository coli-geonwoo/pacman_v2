package game.entities;

import game.Game;
import game.Observer;
import game.Sujet;
import game.entities.direction.Speeds;
import game.entities.ghosts.Ghost;
import game.entities.notifier.EntityNotifyMapper;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;
import java.util.ArrayList;
import java.util.List;

//팩맨을 설명하는 클래스
public class Pacman extends MovingEntity implements Sujet {
    private CollisionDetector collisionDetector;
    private List<Observer> observerCollection;
    private EntityNotifyMapper entityNotifyMapper;

    public Pacman(int xPos, int yPos) {
        super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);
        observerCollection = new ArrayList<>();
        entityNotifyMapper = new EntityNotifyMapper();
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

        //Selon les touches appuyées, la direction de pacman change en conséquence
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

        //모든 충돌 객체 가져오기 > 객체들에게 알림 발송
        List<Entity> allCollisionEntities = collisionDetector.getAllCollisionEntities(this);
        entityNotifyMapper.notifyCollisionEntities(allCollisionEntities, observerCollection);

        //Pacman의 다음 잠재적 위치에 벽이 없으면 그의 위치가 업데이트됩니다.
        if (!WallCollisionDetector.checkWallCollision(this, getxSpd(), getySpd())) {
            updatePosition();
        }
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
