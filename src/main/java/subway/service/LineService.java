package subway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.controller.request.LineRequest;
import subway.controller.response.LineResponse;
import subway.controller.response.StationResponse;
import subway.repository.LineRepository;
import subway.repository.StationRepository;
import subway.repository.entity.Line;
import subway.repository.entity.Station;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LineService {

    private final LineRepository lineRepository;

    private final StationRepository stationRepository;

    @Transactional
    public LineResponse createLine(LineRequest req) {
        Line line = lineRepository.save(req.toEntity());
        return getStationsInLine(line);
    }

    private LineResponse getStationsInLine(Line stationLine) {
        List<Station> stations = stationRepository.findAllByIdIn(List.of(stationLine.getDownStationId(), stationLine.getUpStationId()));
        return LineResponse.from(
                stationLine,
                stations.stream()
                        .map(StationResponse::from)
                        .collect(Collectors.toList())
        );
    }
}