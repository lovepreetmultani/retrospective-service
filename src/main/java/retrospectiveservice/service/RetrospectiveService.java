package retrospectiveservice.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrospectiveservice.dto.FeedbackItem;
import retrospectiveservice.dto.Retrospective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;

@Service
public class RetrospectiveService {

    private static final Logger logger = LoggerFactory.getLogger(RetrospectiveService.class);

    private List<Retrospective> retrospectives = new ArrayList<>();

    public RetrospectiveService(List<Retrospective> retrospectives) {
        this.retrospectives = retrospectives;
    }

    public Retrospective createRetrospective(Retrospective retrospective) {
        if (retrospective.getDate() == null || retrospective.getParticipants() == null || retrospective.getParticipants().isEmpty()) {
            logger.error("Cannot create retrospective without date or participants.");
            throw new IllegalArgumentException("Cannot create retrospective without date or participants.");
        }

        retrospectives.add(retrospective);
        logger.info("Created a new retrospective with name: {}", retrospective.getName());
        return retrospective;
    }

    public Retrospective addFeedbackItem(String retrospectiveName, FeedbackItem feedbackItem) {
        Retrospective retrospective = findRetrospectiveByName(retrospectiveName);

        if (feedbackItem.getName() == null || feedbackItem.getBody() == null || feedbackItem.getFeedbackType() == null) {
            logger.error("Cannot add feedback item without required fields.");
            throw new IllegalArgumentException("Cannot add feedback item without required fields.");
        }

        retrospective.getFeedbackItems().add(feedbackItem);
        logger.info("Added a new feedback item to retrospective with name: {}", retrospective.getName());
        return retrospective;
    }

    public Retrospective updateFeedbackItem(String retrospectiveName, int index, FeedbackItem updatedFeedbackItem) {
        Retrospective retrospective = findRetrospectiveByName(retrospectiveName);
        List<FeedbackItem> feedbackItems = retrospective.getFeedbackItems();

        if (updatedFeedbackItem.getBody() == null || updatedFeedbackItem.getFeedbackType() == null) {
            logger.error("Cannot update feedback item without required fields.");
            throw new IllegalArgumentException("Cannot update feedback item without required fields.");
        }
        if (index >= 0 && index < feedbackItems.size()) {
            feedbackItems.set(index, updatedFeedbackItem);
        }

        return retrospective;
    }

    public List<Retrospective> getAllRetrospectives(int currentPage, int pageSize) {
        return retrospectives.subList((currentPage - 1) * pageSize, Math.min(currentPage * pageSize, retrospectives.size()));
    }

    public List<Retrospective> searchRetrospectivesByDate(String date, int currentPage, int pageSize) {
        List<Retrospective> matchedRetrospectives = retrospectives.stream()
                .filter(retrospective -> retrospective.getDate().equals(date))
                .collect(Collectors.toList());

        return matchedRetrospectives.subList((currentPage - 1) * pageSize, Math.min(currentPage * pageSize, matchedRetrospectives.size()));
    }
    public List<Retrospective> searchRetrospectivesByDate(String date) {
        // Implement date-based filtering logic
        // This is a simplified example, and you may want to use a Date object and proper date comparison
        List<Retrospective> filteredRetrospectives = new ArrayList<>();
        for (Retrospective retrospective : retrospectives) {
            if (date.equals(retrospective.getDate().toString())) {
                filteredRetrospectives.add(retrospective);
            }
        }

        return filteredRetrospectives;
    }

    private Retrospective findRetrospectiveByName(String name) {
        return retrospectives.stream()
                .filter(retrospective -> retrospective.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Retrospective not found with name: {}", name);
                    return new IllegalArgumentException("Retrospective not found with name: " + name);
                });
    }
}

