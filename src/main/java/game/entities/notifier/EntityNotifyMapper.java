package game.entities.notifier;

import game.Observer;
import game.entities.Entity;
import java.util.List;

public class EntityNotifyMapper {

    private final List<EntityNotifier> notifiers;

    public EntityNotifyMapper() {
        this.notifiers = List.of(
                new CherryNotifier(),
                new GhostNotifier(),
                new PacGumNotifier(),
                new SuperPacGumNotifier(),
                new MonsterPacGumNotifier()
        );
    }

    public void notifyCollisionEntities(List<Entity> entities, List<Observer> observers) {
        entities.forEach(entity ->
                {
                    for (EntityNotifier notifier : notifiers) {
                        if (notifier.canHandle(entity)) {
                            notifier.notify(entity, observers);
                        }
                    }
                }
        );
    }
}
