package game.entities.notifier;

import game.Observer;
import game.entities.Cherry;
import game.entities.Entity;
import java.util.List;

public class CherryNotifier extends AbstractEntityNotifier {

    public CherryNotifier() {
        super(Cherry.class);
    }

    @Override
    void notifyToObservers(Entity entity, List<Observer> observers) {
        observers.forEach(obs -> obs.updateCherry(((Cherry) entity)));
    }
}
