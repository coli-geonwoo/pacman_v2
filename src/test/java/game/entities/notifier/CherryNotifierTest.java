package game.entities.notifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import game.Observer;
import game.entities.Cherry;
import game.entities.Wall;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CherryNotifierTest {

    @DisplayName("체리에게 알림을 발송할 수 있다")
    @Test
    void canHandle() {
        CherryNotifier notifier = new CherryNotifier();

        boolean canHandle = notifier.canHandle(new Cherry(0, 0));

        assertThat(canHandle).isTrue();
    }

    @DisplayName("체리가 아니면 알림을 발송할 수 없다")
    @Test
    void canNotHandle() {
        CherryNotifier notifier = new CherryNotifier();

        boolean canHandle = notifier.canHandle(new Wall(0, 0));

        assertThat(canHandle).isFalse();
    }

    @DisplayName("체리에게 알림을 발송한다")
    @Test
    void notifyToObservers() {
        CherryNotifier notifier = new CherryNotifier();
        Observer observer = Mockito.mock(Observer.class);

        notifier.notify(new Cherry(0,0), List.of(observer));

        Mockito.verify(observer, times(1)).updateCherry(any(Cherry.class));
    }
}
