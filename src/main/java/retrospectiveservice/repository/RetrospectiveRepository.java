package retrospectiveservice.repository;

import org.springframework.stereotype.Repository;
import retrospectiveservice.dto.Retrospective;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RetrospectiveRepository {

    private final List<Retrospective> retrospectives = new ArrayList<>();

    public List<Retrospective> getAllRetrospectives() {
        return retrospectives;
    }

    public Retrospective findRetrospectiveByName(String name) {
        return retrospectives.stream()
                .filter(retrospective -> retrospective.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void saveRetrospective(Retrospective retrospective) {
        retrospectives.add(retrospective);
    }
}
