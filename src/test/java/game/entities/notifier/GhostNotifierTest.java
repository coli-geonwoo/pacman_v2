package game.entities.notifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import game.Observer;
import game.entities.Cherry;
import game.entities.Position;
import game.entities.Wall;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GhostNotifierTest {

    @DisplayName("유령에게 알림을 발송할 수 있다")
    @Test
    void canHandle() {
        GhostNotifier notifier = new GhostNotifier();

        boolean canHandle = notifier.canHandle(new Blinky(0, 0, new Position(2, 2)));

        assertThat(canHandle).isTrue();
    }

    @DisplayName("유령이 아니면 알림을 발송할 수 없다")
    @Test
    void canNotHandle() {
        GhostNotifier notifier = new GhostNotifier();

        boolean canHandle = notifier.canHandle(new Cherry(0, 0));

        assertThat(canHandle).isFalse();
    }

    @DisplayName("유령에게 알림을 발송한다")
    @Test
    void notifyToObservers() {
        GhostNotifier notifier = new GhostNotifier();
        Observer observer = Mockito.mock(Observer.class);

        notifier.notify(new Blinky(0, 0, new Position(2, 2)), List.of(observer));

        Mockito.verify(observer, times(1)).updateGhostCollision(any(Ghost.class));
    }
}
