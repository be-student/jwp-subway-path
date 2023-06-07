package subway.line.db.line;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import subway.line.db.interstation.InterStationEntity;
import subway.line.domain.interstation.InterStation;
import subway.line.domain.line.Line;
import subway.line.domain.line.LineRepository;
import subway.line.domain.line.exception.LineAlreadyRegisteredException;
import subway.line.domain.line.exception.LineNotFoundException;

@Repository
public class LineRepositoryImpl implements LineRepository {

    private final LineDao lineDao;

    public LineRepositoryImpl(JdbcTemplate jdbcTemplate) {
        lineDao = new LineDao(jdbcTemplate);
    }

    @Override
    public Line save(Line line) {
        try {
            LineEntity lineEntity = lineDao.insert(LineEntity.from(line));
            return toLineProxy(lineEntity);
        } catch (DuplicateKeyException e) {
            throw new LineAlreadyRegisteredException();
        }
    }

    @Override
    public List<Line> findAll() {
        return lineDao.findAll()
                .stream()
                .map(this::toLineProxy)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Line> findById(long id) {
        return lineDao.findById(id)
                .map(this::toLineProxy);
    }

    @Override
    public Line update(Line line) {
        if (!(line instanceof LineProxy)) {
            throw new LineProxyNotInitializedException();
        }
        LineProxy lineProxy = (LineProxy) line;
        updateInformation(line, lineProxy);
        addInterStations(lineProxy);
        removeInterStations(lineProxy);
        return findById(line.getId())
                .orElseThrow(LineNotFoundException::new);
    }

    private void addInterStations(LineProxy lineProxy) {
        if (!lineProxy.getAddedInterStations().isEmpty()) {
            lineDao.insertInterStations(toInterStationEntities(lineProxy.getAddedInterStations(), lineProxy.getId()));
        }
    }

    private void updateInformation(Line line, LineProxy lineProxy) {
        if (lineProxy.isInfoNeedToUpdated()) {
            lineDao.updateInformation(LineEntity.from(line));
        }
    }

    private void removeInterStations(LineProxy lineProxy) {
        if (!lineProxy.getRemovedInterStations().isEmpty()) {
            lineDao.deleteInterStations(toInterStationEntities(lineProxy.getRemovedInterStations(), lineProxy.getId()));
        }
    }

    private List<InterStationEntity> toInterStationEntities(List<InterStation> interStations,
            long id) {
        return interStations.stream()
                .map(interStation -> InterStationEntity.of(interStation, id))
                .collect(Collectors.toList());
    }

    private LineProxy toLineProxy(LineEntity lineEntity) {
        return new LineProxy(lineEntity.getId(), lineEntity.getName(), lineEntity.getColor(),
                toInterStations(lineEntity.getInterStationEntities()));
    }

    private List<InterStation> toInterStations(List<InterStationEntity> interStationEntities) {
        return interStationEntities.stream()
                .map(InterStationEntity::toInterStation)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        lineDao.deleteById(id);
    }
}
