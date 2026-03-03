package fitnessApp.AiService.repository;

import fitnessApp.AiService.entity.Recommendation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends MongoRepository<Recommendation, ObjectId> {
    Optional<Recommendation> findByActivityId(String activityId);

    List<Recommendation> findByUserId(Long userId);
}
