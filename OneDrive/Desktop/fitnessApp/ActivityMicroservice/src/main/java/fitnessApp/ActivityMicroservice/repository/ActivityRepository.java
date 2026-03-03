package fitnessApp.ActivityMicroservice.repository;

import fitnessApp.ActivityMicroservice.entity.Activity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activity, ObjectId> {
    List<Activity> findByUserid(Long id);
}
