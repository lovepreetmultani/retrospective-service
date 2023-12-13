package retrospectiveservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrospectiveservice.dto.FeedbackItem;
import retrospectiveservice.dto.Retrospective;
import retrospectiveservice.service.RetrospectiveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retrospective")
public class RetrospectiveController {

    private List<Retrospective> retrospectives = new ArrayList<>();

    // Endpoint to create a new retrospective
   @PostMapping("/create")
    public ResponseEntity<String> createRetrospective(@RequestBody Retrospective retrospective) {
        System.out.println("request body create is: "+retrospective.toString()+ "date"+retrospective.getDate()+"participant"+retrospective.getParticipants());
        if (retrospective.getDate() == null || retrospective.getParticipants() == null) {
            // Return a bad request response with an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and participants are required.");
        }

        // Ensure feedback items are initialized as empty
        retrospective.setFeedbackItems(new ArrayList<>());

        retrospectives.add(retrospective);

        // Return a success response with the retrospective ID or any relevant information
        return ResponseEntity.status(HttpStatus.CREATED).body("Retrospective created successfully.");
    }

    // Endpoint to add feedback item to a retrospective
    @PostMapping("/{name}/feedback")
    public ResponseEntity<String> addFeedbackItem(
            @PathVariable String name,
            @RequestBody FeedbackItem feedbackItem) {

        Optional<Retrospective> optionalRetrospective = retrospectives.stream()
                .filter(retrospective -> retrospective.getParticipants().contains(name))
                .findFirst();

        if (optionalRetrospective.isPresent()) {
            Retrospective retrospective = optionalRetrospective.get();

            // Add feedback item to the retrospective
            retrospective.getFeedbackItems().add(feedbackItem);

            // Return a success response with the ID or any relevant information
            return ResponseEntity.status(HttpStatus.CREATED).body("Feedback item added successfully.");
        } else {
            // Return a not found response with an error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retrospective not found.");
        }
    }

    // Endpoint to update feedback item
    @PutMapping("/{name}/feedback/{feedbackName}")
    public ResponseEntity<String> updateFeedbackItem(
            @PathVariable String name,
            @PathVariable Long feedbackName,
            @RequestBody FeedbackItem updatedFeedbackItem) {

        Optional<Retrospective> optionalRetrospective = retrospectives.stream()
                .filter(retrospective -> retrospective.getParticipants().contains(name))
                .findFirst();

        if (optionalRetrospective.isPresent()) {
            Retrospective retrospective = optionalRetrospective.get();

            // Find the feedback item by ID
            Optional<FeedbackItem> optionalFeedbackItem = retrospective.getFeedbackItems().stream()
                    .filter(item -> item.getId().equals(feedbackName))
                    .findFirst();

            if (optionalFeedbackItem.isPresent()) {
                FeedbackItem feedbackItem = optionalFeedbackItem.get();

                // Update feedback item properties
                feedbackItem.setBody(updatedFeedbackItem.getBody());
                feedbackItem.setFeedbackType(updatedFeedbackItem.getFeedbackType());

                // Return a success response with the ID or any relevant information
                return ResponseEntity.status(HttpStatus.OK).body("Feedback item updated successfully.");
            } else {
                // Return a not found response with an error message
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback item not found.");
            }
        } else {
            // Return a not found response with an error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Retrospective not found.");
        }
    }

    // Endpoint to retrieve retrospectives with pagination
    @GetMapping("/pagination")
    public ResponseEntity<List<Retrospective>> getAllRetrospectives(
            @RequestParam(value = "currentPage", defaultValue = "0", required = false) int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestHeader(name = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {

        // Apply pagination
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, retrospectives.size());

        // Get the retrospectives for the current page
        List<Retrospective> retrospectivesForPage = retrospectives.subList(start, end);

        // Return the retrospectives based on the content type specified in the Accept header
        return acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)
                ? ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_XML).body(retrospectivesForPage)
                : ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(retrospectivesForPage);
    }

    @GetMapping
    public List<Retrospective> getAllRetrospectives() {
        return retrospectives;
    }

    // Endpoint to search retrospectives by date
    @GetMapping("/search")
    public ResponseEntity<List<Retrospective>> searchRetrospectivesByDate(
            @RequestParam String date,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestHeader(name = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String acceptHeader) {

        // Filter retrospectives by date
        List<Retrospective> matchedRetrospectives = retrospectives.stream()
                .filter(retrospective -> retrospective.getDate().equals(date))
                .collect(Collectors.toList());

        // Apply pagination
        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, matchedRetrospectives.size());

        // Get the retrospectives for the current page
        List<Retrospective> retrospectivesForPage = matchedRetrospectives.subList(start, end);

        // Return the retrospectives based on the content type specified in the Accept header
        return acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)
                ? ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_XML).body(retrospectivesForPage)
                : ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(retrospectivesForPage);
    }
}