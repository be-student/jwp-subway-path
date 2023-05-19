package subway.application.line.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import subway.adapter.line.out.FakeLineRepository;
import subway.application.line.port.in.update.LineUpdateRequestDto;
import subway.domain.interstation.InterStation;
import subway.domain.line.Line;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("지하철 노선 수정 기능 테스트")
class LineUpdateServiceTest {

    private FakeLineRepository fakeLineRepository;
    private LineUpdateService lineUpdateService;

    @BeforeEach
    void setUp() {
        fakeLineRepository = new FakeLineRepository();
        lineUpdateService = new LineUpdateService(fakeLineRepository);
    }

    @Test
    void 정상적으로_수정된다() {
        //given
        final Line savedLine = fakeLineRepository.save(new Line(1L, "2호선", "green", List.of(
                new InterStation(1L, 1L, 2L, 3),
                new InterStation(2L, 2L, 3L, 3)
        )));

        //when
        lineUpdateService.updateLine(new LineUpdateRequestDto(savedLine.getId(), "3호선", "orange"));

        //then
        final Line result = fakeLineRepository.findById(savedLine.getId()).get();
        assertSoftly(
                softly -> {
                    softly.assertThat(result.getName().getValue()).isEqualTo("3호선");
                    softly.assertThat(result.getColor().getValue()).isEqualTo("orange");
                }
        );
    }

}
