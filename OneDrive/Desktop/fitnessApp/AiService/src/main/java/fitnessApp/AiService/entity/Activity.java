package fitnessApp.AiService.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data

public class Activity {
    private ObjectId id;
    private Long userid;
    private Integer duration;
    private Integer calories;
    private String type;
    private Integer startTime;
    private Map<String,Object> additionalMetrics = new HashMap<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
