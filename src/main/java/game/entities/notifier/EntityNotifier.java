package game.entities.notifier;

import game.Observer;
import game.entities.Entity;
import java.util.List;

public interface EntityNotifier {

    boolean canHandle(Entity entity);

    void notify(Entity entity, List<Observer> observers);
}
