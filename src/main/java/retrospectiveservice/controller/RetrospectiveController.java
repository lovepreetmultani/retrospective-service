package retrospectiveservice.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrospectiveservice.dto.FeedbackItem;
import retrospectiveservice.dto.Retrospective;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// RetrospectiveController.java
@RestController
@RequestMapping("/retrospective")
public class RetrospectiveController {

    private List<Retrospective> retrospectives = new ArrayList<>();

    // Endpoint to create a new retrospective

    @GetMapping("/test")
    public String testing(){
        return "Hello world";
    }
    @ResponseBody
    @RequestMapping(value = "/create", headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
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
            @PathVariable String feedbackName,
            @RequestBody FeedbackItem updatedFeedbackItem) {
        // Find the retrospective and update the feedback item
        // ...

        return ResponseEntity.ok("Feedback item updated successfully");
    }

    // Endpoint to retrieve retrospectives with pagination
    @GetMapping
    public ResponseEntity<List<Retrospective>> getRetrospectives(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        // Implement pagination logic
        // ...

        return ResponseEntity.ok(retrospectives);
    }

    // Endpoint to search retrospectives by date
    @GetMapping("/search")
    public ResponseEntity<List<Retrospective>> searchRetrospectivesByDate(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {
        // Implement search logic
        // ...

        return ResponseEntity.ok(retrospectives);
    }
}

