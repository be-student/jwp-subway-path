package subway.station.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.station.application.StationCommandService;
import subway.station.application.StationQueryService;
import subway.station.application.dto.request.StationCreateRequestDto;
import subway.station.application.dto.request.StationInfoResponseDto;
import subway.station.ui.dto.reqest.StationCreateRequest;
import subway.station.ui.dto.reqest.StationUpdateInfoRequest;
import subway.station.ui.dto.response.StationInfoResponse;
import subway.station.ui.dto.response.StationInfosResponse;

@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationQueryService stationQueryService;
    private final StationCommandService stationCommandService;

    public StationController(StationQueryService stationQueryService, StationCommandService stationCommandService) {
        this.stationQueryService = stationQueryService;
        this.stationCommandService = stationCommandService;
    }

    @GetMapping
    public ResponseEntity<StationInfosResponse> findAll() {
        List<StationInfoResponseDto> result = stationQueryService.findAll();
        StationInfosResponse stationInfosResponse = StationAssembler.toStationInfosResponse(result);
        return ResponseEntity.ok(stationInfosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationInfoResponse> findStationInfoById(@PathVariable Long id) {
        StationInfoResponse stationInfoResponse = StationAssembler.toStationInfoResponse(
                stationQueryService.findStationInfoById(id));
        return ResponseEntity.ok(stationInfoResponse);
    }

    @PostMapping
    public ResponseEntity<StationInfoResponse> create(@RequestBody @Valid StationCreateRequest request) {
        StationCreateRequestDto requestDto = StationAssembler.toStationCreateRequestDto(request);
        StationInfoResponseDto stationInfoResponseDto = stationCommandService.create(requestDto);
        StationInfoResponse response = StationAssembler.toStationInfoResponse(stationInfoResponseDto);
        return ResponseEntity.created(URI.create("/stations/" + response.getId())).body(response);
    }

    @DeleteMapping("/{stationId}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long stationId) {
        stationCommandService.deleteById(stationId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStationInfo(@PathVariable Long id,
            @RequestBody @Valid StationUpdateInfoRequest request) {
        stationCommandService.updateStationInfo(StationAssembler.toUpdateStationInfoRequestDto(id, request));
        return ResponseEntity.noContent()
                .build();
    }
}
