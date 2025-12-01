package game.entities.notifier;

import game.Observer;
import game.entities.Entity;
import game.entities.PacGum;
import game.entities.SuperPacGum;
import java.util.List;

public class SuperPacGumNotifier extends AbstractEntityNotifier {

    public SuperPacGumNotifier() {
        super(SuperPacGum.class);
    }

    @Override
    void notifyToObservers(Entity entity, List<Observer> observers) {
        observers.forEach(obs -> obs.updateSuperPacGumEaten(((SuperPacGum) entity)));
    }
}
