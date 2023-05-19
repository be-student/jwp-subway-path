package subway.application.line.port.in;

import java.util.List;
import subway.domain.line.Line;

public class LineResponseDto {

    private final Long id;
    private final String name;
    private final String color;
    private final List<InterStationResponseDto> interStations;

    public LineResponseDto(final Long id, final String name, final String color, final List<InterStationResponseDto> interStations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.interStations = interStations;
    }

    public static LineResponseDto from(final Line savedLine) {
        return new LineResponseDto(savedLine.getId(), savedLine.getName().getValue(),
                savedLine.getColor().getValue(),
                InterStationResponseDto.from(savedLine.getInterStations().getInterStations()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<InterStationResponseDto> getInterStations() {
        return interStations;
    }
}
