package game.entities.notifier;

import game.Observer;
import game.entities.Entity;
import game.entities.MonsterPacGum;
import java.util.List;

public class MonsterPacGumNotifier extends AbstractEntityNotifier {

    public MonsterPacGumNotifier() {
        super(MonsterPacGum.class);
    }

    @Override
    void notifyToObservers(Entity entity, List<Observer> observers) {
        observers.forEach(observer -> observer.updateMonsterPacGumEaten((MonsterPacGum) entity));
    }
}
