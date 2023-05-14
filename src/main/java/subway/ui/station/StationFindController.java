package subway.ui.station;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.application.station.port.in.StationFindAllUseCase;
import subway.application.station.port.in.StationInfoResponseDto;
import subway.ui.station.dto.out.StationInfosResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stations")
public class StationFindController {

    private final StationFindAllUseCase stationFindAllUseCase;

    @GetMapping
    public ResponseEntity<StationInfosResponse> findAll() {
        final List<StationInfoResponseDto> result = stationFindAllUseCase.findAll();
        final StationInfosResponse stationInfosResponse = StationAssembler.toStationInfosResponse(result);
        return ResponseEntity.ok(stationInfosResponse);
    }
}
