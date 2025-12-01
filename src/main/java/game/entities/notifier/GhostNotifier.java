package game.entities.notifier;

import game.Observer;
import game.entities.Entity;
import game.entities.PacGum;
import game.entities.ghosts.Ghost;
import java.util.List;

public class GhostNotifier extends AbstractEntityNotifier {

    public GhostNotifier() {
        super(Ghost.class);
    }

    @Override
    void notifyToObservers(Entity entity, List<Observer> observers) {
        observers.forEach(obs -> obs.updateGhostCollision(((Ghost) entity)));
    }
}
