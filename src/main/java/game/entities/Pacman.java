package game.entities;

import game.Game;
import game.Observer;
import game.Sujet;
import game.entities.direction.Speeds;
import game.entities.ghosts.Ghost;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;

import java.util.ArrayList;
import java.util.List;

//팩맨을 설명하는 클래스
public class Pacman extends MovingEntity implements Sujet {
    private CollisionDetector collisionDetector;
    private List<Observer> observerCollection;

    public Pacman(int xPos, int yPos) {
        super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);
        observerCollection = new ArrayList<>();
    }

    //여행 관리
    public void input(KeyHandler k) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!onTheGrid()) return; //팩맨은 플레이 영역의 "사각형"에 있어야 합니다.
        if (!onGameplayWindow()) return; //팩맨은 플레이 영역에 있어야 합니다.

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
        if (updateSpeeds.isStopped()) return;
        if (!Game.getFirstInput()) Game.setFirstInput(true);
        super.updateSpeed(updateSpeeds);
    }


    //TODO 다형성 활용하는 방식으로 변경
    @Override
    public void update() {
        //우리는 팩맨이 팩덤, 파워 팩덤, 유령과 접촉하는지 매번 확인하고, 관찰자들에게 그에 따른 알림을 보냅니다.
        Cherry cherry = (Cherry) collisionDetector.checkCollision(this, Cherry.class);
        if (cherry != null) {
            notifyObserverCherryEaten(cherry);
        }

        PacGum pg = (PacGum) collisionDetector.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);
        }

        SuperPacGum spg = (SuperPacGum) collisionDetector.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            notifyObserverSuperPacGumEaten(spg);
        }

        Ghost gh = (Ghost) collisionDetector.checkCollision(this, Ghost.class);
        if (gh != null) {
            notifyObserverGhostCollision(gh);
        }

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

    @Override
    public void notifyObserverPacGumEaten(PacGum pg) {
        observerCollection.forEach(obs -> obs.updatePacGumEaten(pg));
    }

    @Override
    public void notifyObserverCherryEaten(Cherry cherry) {
        observerCollection.forEach(obs -> obs.updateCherry(cherry));
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        observerCollection.forEach(obs -> obs.updateSuperPacGumEaten(spg));
    }

    @Override
    public void notifyObserverGhostCollision(Ghost gh) {
        observerCollection.forEach(obs -> obs.updateGhostCollision(gh));
    }
}
