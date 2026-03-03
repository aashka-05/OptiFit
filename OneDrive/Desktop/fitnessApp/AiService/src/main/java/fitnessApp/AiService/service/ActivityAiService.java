package fitnessApp.AiService.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fitnessApp.AiService.entity.Activity;
import fitnessApp.AiService.entity.Recommendation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ActivityAiService {

    @Autowired
    GeminiService geminiService;
     public Recommendation generateRecommendation(Activity activity){
         String prompt = createPrompt(activity);
         String aiResponse = geminiService.getAnswer(prompt);
         return processAiResponse(activity,aiResponse);
     }
     public Recommendation processAiResponse(Activity activity,String aiResponse){
         try {
             ObjectMapper mapper = new ObjectMapper();
             JsonNode rootNode = mapper.readTree(aiResponse);
             JsonNode textNode = rootNode.path("candidates")
                     .get(0)
                     .path("content")
                     .path("parts")
                     .get(0)
                     .path("text");
             String rawText = textNode.asText().trim();

             int jsonStart = rawText.indexOf('{');
             int jsonEnd = rawText.lastIndexOf('}');

             if (jsonStart == -1 || jsonEnd == -1 || jsonEnd <= jsonStart) {
                 throw new IllegalArgumentException("No valid JSON found in AI response");
             }

             String content = rawText.substring(jsonStart, jsonEnd + 1);


             JsonNode analysisJson = mapper.readTree(content);
             JsonNode analysisNode = analysisJson.path("analysis");
             StringBuilder fullAnalysis = new StringBuilder();
             addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall:");
             addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace:");
             addAnalysisSection(fullAnalysis,analysisNode,"heartRate","Heart Rate:");
             addAnalysisSection(fullAnalysis,analysisNode,"CaloriesBurned","Calories:");

             List<String> improvements = extractImprovements(analysisJson.path("improvements"));
             List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
             List<String> safety = extractSafetyGuidelines(analysisJson.path("safety"));

             return Recommendation.builder()
                     .activityId(activity.getId())
                     .userId(activity.getUserid())
                     .recommendation(fullAnalysis.toString().trim())
                     .improvements(improvements)
                     .safety(safety)
                     .suggestions(suggestions)
                     .createdAt(LocalDateTime.now())
                     .build();
         }
         catch(Exception e){
             e.printStackTrace();
             return createDefaultRecommendation(activity);
         }

     }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserid())
                .recommendation("Unable to generate detailed analysis")
                .improvements(Collections.singletonList("Continue with your current routine"))
                .safety(Arrays.asList(
                        "Always warm up before exercising",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .suggestions(Collections.singletonList("Consider consulting your fitness trainer."))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
         List<String> safety = new ArrayList<>();
         if(safetyNode.isArray()){
             safetyNode.forEach((item) -> safety.add(item.asText()));
         }
         return safety.isEmpty()?
                 Collections.singletonList("Follow general safety guidelines"):
                 safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray()){
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s",workout,description));
            });
        }
        return suggestions.isEmpty()?
                Collections.singletonList("No specific suggestions provided"):
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
         List<String> improvements = new ArrayList<>();
         if(improvementsNode.isArray()){
             improvementsNode.forEach(improvement -> {
                 String area = improvement.path("area").asText();
                 String detail = improvement.path("recommendation").asText();
                 improvements.add(String.format("%s: %s",area,detail));
             });
         }
         return improvements.isEmpty() ?
                 Collections.singletonList("No specific improvements provided"):
                 improvements;
    }

    public void addAnalysisSection(StringBuilder fullAnalysis , JsonNode analysisNode,String key,String prefix){
         if(!analysisNode.path(key).isMissingNode()){
             fullAnalysis.append(prefix)
                     .append(analysisNode.path(key).asText())
                     .append("\n\n");
         }
     }
     public String createPrompt(Activity activity){
         return String.format(""" 
                  Analyze this fitness activity and provide detailed recommendations in the following format
                  {
                      "analysis" : {
                          "overall": "Overall analysis here",
                          "pace": "Pace analysis here",
                          "heartRate": "Heart rate analysis here",
                          "CaloriesBurned": "Calories Burned here"
                      },
                      "improvements": [
                          {
                              "area": "Area name",
                              "recommendation": "Detailed Recommendation"
                          }
                      ],
                      "suggestions" : [
                          {
                              "workout": "Workout name",
                              "description": "Detailed workout description"
                          }
                      ],
                      "safety": [
                          "Safety point 1",
                          "Safety point 2"
                      ]
                  }
                
                  Analyze this activity:
                  Activity Type: %s
                  Duration: %d minutes
                  calories Burned: %d
                  Additional Metrics: %s
                
                  provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines
                  Ensure the response follows the EXACT JSON format shown above.              
                """,
                 activity.getType(),
                 activity.getDuration(),
                 activity.getCalories(),
                 activity.getAdditionalMetrics()
         );
     }
}
