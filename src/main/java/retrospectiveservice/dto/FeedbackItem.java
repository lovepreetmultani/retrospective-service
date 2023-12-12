package retrospectiveservice.dto;

import jakarta.persistence.*;

@Entity
public class FeedbackItem {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String body;
    private FeedbackType feedbackType;

    // Getters and setters
    public FeedbackItem() {
    }

    public FeedbackItem(Long id, String name, String body, FeedbackType feedbackType) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.feedbackType = feedbackType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }
}