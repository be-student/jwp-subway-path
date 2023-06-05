package subway.route.ui;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.route.RouteAssembler;
import subway.route.application.dto.response.RouteFindResponse;
import subway.route.application.dto.response.RouteFindResponseDto;
import subway.route.domain.RouteFindUseCase;
import subway.route.ui.dto.request.RouteFindRequest;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteFindUseCase routeFindUseCase;

    public RouteController(RouteFindUseCase routeFindUseCase) {
        this.routeFindUseCase = routeFindUseCase;
    }

    @PostMapping
    public ResponseEntity<RouteFindResponse> findRoute(@RequestBody @Valid RouteFindRequest request) {
        RouteFindResponseDto responseDto = routeFindUseCase.findRoute(
                RouteAssembler.toRouteFindRequestDto(request));
        return ResponseEntity.ok(RouteAssembler.toRouteFindResponse(responseDto));
    }
}
