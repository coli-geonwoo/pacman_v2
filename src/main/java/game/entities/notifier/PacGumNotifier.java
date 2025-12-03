package game.entities.notifier;

import game.Game;
import game.Observer;
import game.entities.Entity;
import game.entities.PacGum;
import java.util.List;

public class PacGumNotifier extends AbstractEntityNotifier {

    public PacGumNotifier() {
        super(PacGum.class);
    }

    @Override
    void notifyToObservers(Entity entity, List<Observer> observers) {
        observers.forEach(obs -> obs.updatePacGumEaten(((PacGum) entity)));
        Game.increaseCrash();
    }
}
