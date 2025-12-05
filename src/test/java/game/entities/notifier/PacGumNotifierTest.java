package game.entities.notifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import game.Observer;
import game.entities.Cherry;
import game.entities.MonsterPacGum;
import game.entities.PacGum;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PacGumNotifierTest {

    @DisplayName("팩검에게 알림을 발송할 수 있다")
    @Test
    void canHandle() {
        PacGumNotifier notifier = new PacGumNotifier();

        boolean canHandle = notifier.canHandle(new PacGum(0,0));

        assertThat(canHandle).isTrue();
    }

    @DisplayName("팩검이 아니면 알림을 발송할 수 없다")
    @Test
    void canNotHandle() {
        PacGumNotifier notifier = new PacGumNotifier();

        boolean canHandle = notifier.canHandle(new Cherry(0, 0));

        assertThat(canHandle).isFalse();
    }

    @DisplayName("팩검에게 알림을 발송한다")
    @Test
    void notifyToObservers() {
        PacGumNotifier notifier = new PacGumNotifier();
        Observer observer = Mockito.mock(Observer.class);

        notifier.notify(new PacGum(0,0), List.of(observer));

        Mockito.verify(observer, times(1)).updatePacGumEaten(any(PacGum.class));
    }

}
