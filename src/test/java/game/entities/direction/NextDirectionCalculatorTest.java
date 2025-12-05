package game.entities.direction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import game.entities.Position;
import game.entities.ghosts.Blinky;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NextDirectionCalculatorTest {

    @DisplayName("다양한 방향 계산 전략 중에서 가장 최단 거리의 방향을 선택한다")
    @Test
    void calculateNextDirection() {
        DirectionCalculateStrategy shortStrategy = Mockito.mock(DirectionCalculateStrategy.class);
        DirectionCalculateStrategy longStrategy = Mockito.mock(DirectionCalculateStrategy.class);
        CalculateResult shortResult = new CalculateResult(0, new Speeds(0,0));
        CalculateResult longResult = new CalculateResult(Double.MAX_VALUE, new Speeds(0,0));
        Mockito.when(shortStrategy.calculate(any(), any(), eq(true))).thenReturn(shortResult);
        Mockito.when(longStrategy.calculate(any(), any(), eq(true))).thenReturn(longResult);
        NextDirectionCalculator calculator = new NextDirectionCalculator(List.of(shortStrategy, longStrategy));

        CalculateResult calculateResult = calculator.calculateNextDirection(
                new Blinky(0, 0, new Position(0, 0)),
                new Position(0, 0),
                true
        );

        assertThat(calculateResult.getDistance()).isZero();
    }



}
