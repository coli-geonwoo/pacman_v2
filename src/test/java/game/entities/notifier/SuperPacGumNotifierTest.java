package game.entities.notifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import game.Observer;
import game.entities.Cherry;
import game.entities.PacGum;
import game.entities.SuperPacGum;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SuperPacGumNotifierTest {

    @DisplayName("슈퍼 팩검에게 알림을 발송할 수 있다")
    @Test
    void canHandle() {
        SuperPacGumNotifier notifier = new SuperPacGumNotifier();

        boolean canHandle = notifier.canHandle(new SuperPacGum(0,0));

        assertThat(canHandle).isTrue();
    }

    @DisplayName("슈퍼 팩검이 아니면 알림을 발송할 수 없다")
    @Test
    void canNotHandle() {
        SuperPacGumNotifier notifier = new SuperPacGumNotifier();

        boolean canHandle = notifier.canHandle(new Cherry(0, 0));

        assertThat(canHandle).isFalse();
    }

    @DisplayName("슈퍼 팩검에게 알림을 발송한다")
    @Test
    void notifyToObservers() {
        SuperPacGumNotifier notifier = new SuperPacGumNotifier();
        Observer observer = Mockito.mock(Observer.class);

        notifier.notify(new SuperPacGum(0,0), List.of(observer));

        Mockito.verify(observer, times(1)).updateSuperPacGumEaten(any(SuperPacGum.class));
    }

}
