package game.utils;

import game.Game;
import game.entities.*;
import java.util.ArrayList;
import java.util.List;

//두 엔터티 간의 충돌을 감지하는 클래스
public class CollisionDetector {
    private Game game;

    public CollisionDetector(Game game) {
        this.game = game;
    }

    // collisionCheck 유형의 엔터티와 obj 엔터티 간의 충돌 감지; 충돌이 발생하는 경우 테스트된 유형의 엔터티가 반환됩니다.
    //CollisionCheck 유형 엔터티는 직사각형 히트박스를 가지고 있으며 여기서는 obj 엔터티의 히트박스를 점으로 간주합니다(Pacman과 유령 간의 충돌의 경우 이렇게 하면 여백이 생기고 게임이 너무 힘들지 않게 됩니다)
    public Entity checkCollision(Entity obj, Class<? extends Entity> collisionCheck) {
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().contains(obj.getxPos() + obj.getSize() / 2, obj.getyPos() + obj.getSize() / 2)) return e;
        }
        return null;
    }

    //모든 충돌 엔티티를 한번에 반환
    public List<Entity> getAllCollisionEntities(Entity obj) {
        List<Entity> entities = new ArrayList<>();
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && e.getHitbox().contains(obj.getxPos() + obj.getSize() / 2, obj.getyPos() + obj.getSize() / 2)) {
                entities.add(e);
            }
        }
        return entities;
    }

    //Même chose que la méthode précédente, mais toutes les hitboxes sont considérées comme rectangulaires
    //이전 방법과 동일하지만 모든 히트박스는 직사각형으로 간주됩니다.
    public Entity checkCollisionRect(Entity obj, Class<? extends Entity> collisionCheck) {
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().intersects(obj.getHitbox())) return e;
        }
        return null;
    }
}
