package retrospectiveservice.service;

import org.springframework.stereotype.Service;
import retrospectiveservice.dto.FeedbackItem;
import retrospectiveservice.dto.Retrospective;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetrospectiveService {

    private final List<Retrospective> retrospectives;

    public RetrospectiveService(List<Retrospective> retrospectives) {
        this.retrospectives = retrospectives;
    }

    public Retrospective createRetrospective(Retrospective retrospective) {
        // Add validation logic if needed
        retrospectives.add(retrospective);
        return retrospective;
    }

    public Retrospective addFeedbackItem(String retrospectiveName, FeedbackItem feedbackItem) {
        Retrospective retrospective = findRetrospectiveByName(retrospectiveName);
        retrospective.getFeedbackItems().add(feedbackItem);
        return retrospective;
    }

    public Retrospective updateFeedbackItem(String retrospectiveName, int index, FeedbackItem updatedFeedbackItem) {
        Retrospective retrospective = findRetrospectiveByName(retrospectiveName);
        List<FeedbackItem> feedbackItems = retrospective.getFeedbackItems();

        if (index >= 0 && index < feedbackItems.size()) {
            feedbackItems.set(index, updatedFeedbackItem);
        }

        return retrospective;
    }

    public List<Retrospective> getAllRetrospectives() {
        return retrospectives;
    }

    public List<Retrospective> getRetrospectivesByDate(String date) {
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
                .orElseThrow(() -> new IllegalArgumentException("Retrospective not found"));
    }
}

