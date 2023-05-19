package subway.ui.line.dto.in;

import java.util.List;

public class LineResponse {

    private Long id;
    private String name;
    private String color;
    private List<InterStationResponse> interStations;

    private LineResponse() {
    }

    public LineResponse(final Long id, final String name, final String color,
            final List<InterStationResponse> interStations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.interStations = interStations;
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

    public List<InterStationResponse> getInterStations() {
        return interStations;
    }
}
