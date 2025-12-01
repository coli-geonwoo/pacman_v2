package game.entities.notifier;

import game.Observer;
import game.entities.Entity;
import java.util.List;

public abstract class AbstractEntityNotifier implements EntityNotifier {

    private final Class<? extends Entity> clazz;

    public AbstractEntityNotifier(Class<? extends Entity> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean canHandle(Entity entity) {
        return clazz.isInstance(entity);
    }

    @Override
    public void notify(Entity entity, List<Observer> observers) {
        if(!canHandle(entity)) {
            throw new RuntimeException("구독자들에게 이벤트 발행을 할 수 없습니다");
        }
        notifyToObservers(entity, observers);
    }

    abstract void notifyToObservers(Entity entity, List<Observer> observers);
}
