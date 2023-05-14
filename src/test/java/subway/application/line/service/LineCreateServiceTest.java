package subway.application.line.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import subway.adapter.line.out.FakeLineRepository;
import subway.application.line.port.in.InterStationResponseDto;
import subway.application.line.port.in.LineCreateRequestDto;
import subway.application.line.port.in.LineResponseDto;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("라인 추가 기능 테스트")
class LineCreateServiceTest {

    private FakeLineRepository fakeLineRepository;
    private LineCreateService lineCreateService;

    @BeforeEach
    void setUp() {
        fakeLineRepository = new FakeLineRepository();
        lineCreateService = new LineCreateService(fakeLineRepository);
    }

    @Test
    void 정상적으로_생성된다() {
        final LineResponseDto result = lineCreateService.createLine(new LineCreateRequestDto("2호선", "초록색", 1L, 2L, 10));
        final LineResponseDto expected = new LineResponseDto(1L, "2호선", "초록색", List.of(
            new InterStationResponseDto(1L, 1L, 2L, 10)
        ));

        assertSoftly(
            softly -> {
                assertThat(result).usingRecursiveComparison().isEqualTo(expected);
                assertThat(fakeLineRepository.findAll()).hasSize(1);
            }
        );
    }
}
