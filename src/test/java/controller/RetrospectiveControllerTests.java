@RunWith(SpringRunner.class)
@WebMvcTest(RetrospectiveController.class)
public class RetrospectiveControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RetrospectiveService retrospectiveService;

    @Test
    public void createRetrospective_ValidInput_ReturnsCreated() throws Exception {
        Retrospective retrospective = // create a valid retrospective object

                mockMvc.perform(post("/retrospective/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(retrospective)))
                        .andExpect(status().isCreated())
                        .andExpect(content().string("Retrospective created successfully"));
    }

    @Test
    public void createRetrospective_InvalidInput_ReturnsBadRequest() throws Exception {
        Retrospective retrospective = new Retrospective(); // create an invalid retrospective object

        mockMvc.perform(post("/retrospective/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(retrospective)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFeedbackItem_ValidInput_ReturnsCreated() throws Exception {
        String participantName = "Gareth";
        FeedbackItem feedbackItem = // create a valid feedback item object

                mockMvc.perform(post("/retrospective/{name}/feedback", participantName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(feedbackItem)))
                        .andExpect(status().isCreated())
                        .andExpect(content().string("Feedback item added successfully"));
    }

    @Test
    public void addFeedbackItem_ParticipantNotFound_ReturnsNotFound() throws Exception {
        String participantName = "NonExistentParticipant";
        FeedbackItem feedbackItem = // create a valid feedback item object

                mockMvc.perform(post("/retrospective/{name}/feedback", participantName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(feedbackItem)))
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("Retrospective not found"));
    }

    @Test
    public void updateFeedbackItem_ValidInput_ReturnsOk() throws Exception {
        String participantName = "John";
        Long feedbackItemId = 1L;
        FeedbackItem updatedFeedbackItem = // create an updated feedback item object

                mockMvc.perform(put("/retrospective/{name}/feedback/{feedbackName}", participantName, feedbackItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(updatedFeedbackItem)))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Feedback item updated successfully"));
    }

    @Test
    public void updateFeedbackItem_ParticipantNotFound_ReturnsNotFound() throws Exception {
        String participantName = "NonExistentParticipant";
        Long feedbackItemId = 1L;
        FeedbackItem updatedFeedbackItem = // create an updated feedback item object

                mockMvc.perform(put("/retrospective/{name}/feedback/{feedbackName}", participantName, feedbackItemId)
                                .contentType(MediaType.APPLICATION_JSON)
                         git add        .content(asJsonString(updatedFeedbackItem)))
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("Retrospective not found"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
