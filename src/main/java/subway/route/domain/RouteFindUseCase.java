package subway.route.domain;

import subway.route.application.dto.request.RouteFindRequestDto;
import subway.route.application.dto.response.RouteFindResponseDto;

public interface RouteFindUseCase {

    RouteFindResponseDto findRoute(RouteFindRequestDto requestDto);
}
