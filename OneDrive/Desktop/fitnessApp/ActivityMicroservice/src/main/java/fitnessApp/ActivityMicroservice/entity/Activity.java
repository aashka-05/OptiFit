package fitnessApp.ActivityMicroservice.entity;

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
@Document("Activities")
public class Activity {
    @Id
    private ObjectId id;
    private Long userid;
    private ActivityType type;
    private Integer duration;
    private Integer calories;
    private Integer startTime;
    private Map<String,Object> additionalMetrics = new HashMap<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
