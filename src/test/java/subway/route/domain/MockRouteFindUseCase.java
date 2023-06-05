package subway.route.domain;

import java.util.List;
import subway.route.application.dto.request.RouteFindRequestDto;
import subway.route.application.dto.response.RouteEdgeResponseDto;
import subway.route.application.dto.response.RouteFindResponseDto;

public class MockRouteFindUseCase implements RouteFindUseCase {

    private int callCount;
    private RouteFindRequestDto requestDto;

    @Override
    public RouteFindResponseDto findRoute(RouteFindRequestDto requestDto) {
        callCount++;
        this.requestDto = requestDto;
        return new RouteFindResponseDto(List.of(new RouteEdgeResponseDto(1L, 2L, 3L, 4L)), 100, 100);
    }

    public int getCallCount() {
        return callCount;
    }

    public RouteFindRequestDto getRequestDto() {
        return requestDto;
    }
}
