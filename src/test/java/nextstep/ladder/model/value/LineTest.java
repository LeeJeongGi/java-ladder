package nextstep.ladder.model.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LineTest {

    private final int participants = 5;

    @Test
    @DisplayName("사다리 라인 생성 시 처음은 반드시 true 이고 이전 라인이 있다면 생성되지 않아야 한다.")
    void lineDrawTest() {
        Line line = new Line(participants, drawLine());

        assertThat(line.isBeforePoint(1)).isFalse();
    }

    @Test
    @DisplayName("사다리타기 라인이 생성되지 않아야 한다.")
    void lineUnDrawTest() {
        Line line = new Line(participants, unDrawLine());

        IntStream.range(0, participants)
                .forEach(position -> assertThat(line.hasLine(position)).isFalse());
    }

    @Test
    @DisplayName("라인에 대한 객체는 외부에서는 불변 리스트 이다.")
    void getPointsUnmodifiableCollectionTest() {
        Line line = new Line(participants, unDrawLine());

        List<Point> points = line.getPoints();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> points.add(new Point(true)));
    }

    @Test
    @DisplayName("사다리 선에 따라 잘 이동 하는지 테스트")
    void moveTest() {
        Line line = new Line(4, drawLine());
        /*
        사다리 모양
        0     1     2     3     4
        |-----|     |-----|     |
         */
        assertThat(line.move(0)).isEqualTo(1);
        assertThat(line.move(1)).isEqualTo(0);

        assertThat(line.move(2)).isEqualTo(3);
        assertThat(line.move(3)).isEqualTo(2);

    }

    private Random drawLine() {
        return new Random() {
            @Override
            public boolean nextBoolean() {
                return true;
            }
        };
    }

    private Random unDrawLine() {
        return new Random() {
            @Override
            public boolean nextBoolean() {
                return false;
            }
        };
    }
}
