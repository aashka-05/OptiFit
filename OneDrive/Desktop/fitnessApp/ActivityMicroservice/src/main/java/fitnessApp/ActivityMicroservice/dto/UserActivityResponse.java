package fitnessApp.ActivityMicroservice.dto;

import fitnessApp.ActivityMicroservice.entity.ActivityType;
import lombok.Data;

import java.util.Map;

@Data
public class UserActivityResponse {
    private Long userid;
    private ActivityType type;
    private Integer duration;
    private Integer calories;
    private Integer startTime;
    private Map<String,Object> additionalMetrics;
}
