package retrospectiveservice.dto;

import java.time.LocalDate;
import java.util.List;

public class Retrospective {

    private String name;
    private String summary;
    private String date;
    private List<String> participants;
    private List<FeedbackItem> feedbackItems;

    public Retrospective() {
    }

    public Retrospective(String name, String summary, String date, List<String> participants, List<FeedbackItem> feedbackItems) {
        this.name = name;
        this.summary = summary;
        this.date = date;
        this.participants = participants;
        this.feedbackItems = feedbackItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<FeedbackItem> getFeedbackItems() {
        return feedbackItems;
    }

    public void setFeedbackItems(List<FeedbackItem> feedbackItems) {
        this.feedbackItems = feedbackItems;
    }
}
