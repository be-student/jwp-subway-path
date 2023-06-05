package subway.route.application;

import org.springframework.stereotype.Service;
import subway.route.application.dto.request.PathRequestDto;
import subway.route.application.dto.request.RouteFindRequestDto;
import subway.route.application.dto.response.PathResponseDto;
import subway.route.application.dto.response.RouteFindResponseDto;
import subway.route.domain.Edges;
import subway.route.domain.FareCalculator;
import subway.route.domain.PathCalculator;
import subway.route.domain.RouteAllEdgesUseCase;
import subway.route.domain.RouteFindUseCase;

@Service
public class RouteQueryService implements RouteFindUseCase {

    private final RouteAllEdgesUseCase routeAllEdgesUseCase;
    private final PathCalculator pathCalculator;

    public RouteQueryService(RouteAllEdgesUseCase routeAllEdgesUseCase, PathCalculator pathCalculator) {
        this.routeAllEdgesUseCase = routeAllEdgesUseCase;
        this.pathCalculator = pathCalculator;
    }

    @Override
    public RouteFindResponseDto findRoute(RouteFindRequestDto requestDto) {
        Edges allEdges = routeAllEdgesUseCase.findAllEdges();
        PathResponseDto pathResponseDto = pathCalculator.calculatePath(
                new PathRequestDto(requestDto.getSource(), requestDto.getTarget(), allEdges));
        FareCalculator fareCalculator = new FareCalculator();
        long fare = fareCalculator.calculateFare(pathResponseDto.getDistance());
        return new RouteFindResponseDto(RouteDtoAssembler.toRouteEdgeResponseDto(pathResponseDto.getStations()),
                pathResponseDto.getDistance(), fare);
    }
}
