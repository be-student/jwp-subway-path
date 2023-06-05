package subway.route.find;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import subway.route.application.dto.request.RouteFindRequestDto;
import subway.route.application.dto.response.RouteFindResponse;
import subway.route.domain.MockRouteFindUseCase;
import subway.route.ui.RouteController;
import subway.route.ui.dto.request.RouteFindRequest;
import subway.route.ui.dto.response.RouteEdgeResponse;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("경로 조회 기능은")
class RouteControllerTest {

    private MockRouteFindUseCase mockRouteFindUseCase;
    private RouteController routeController;

    @BeforeEach
    void setUp() {
        mockRouteFindUseCase = new MockRouteFindUseCase();
        routeController = new RouteController(mockRouteFindUseCase);
    }

    @Test
    void 경로_조회_요청시_출발역과_도착역_사이의_거리를_응답한다() {
        // given
        RouteFindRequest routeFindRequest = new RouteFindRequest(1L, 2L);

        // when
        ResponseEntity<RouteFindResponse> result = routeController.findRoute(routeFindRequest);

        // then
        assertSoftly(softly -> {
                    softly.assertThat(result.getStatusCodeValue()).isEqualTo(200);
                    softly.assertThat(result.getBody().getDistance()).isEqualTo(100);
                    softly.assertThat(result.getBody().getFare()).isEqualTo(100);
                    softly.assertThat(result.getBody().getStations()).usingRecursiveComparison()
                            .isEqualTo(List.of(new RouteEdgeResponse(1L, 2L, 3L, 4L)));
                    softly.assertThat(mockRouteFindUseCase.getCallCount()).isOne();
                    softly.assertThat(mockRouteFindUseCase.getRequestDto()).usingRecursiveComparison()
                            .isEqualTo(new RouteFindRequestDto(1L, 2L));
                }
        );
    }
}
