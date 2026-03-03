package fitnessApp.ActivityMicroservice.services;

import fitnessApp.ActivityMicroservice.dto.UserActivityResponse;
import fitnessApp.ActivityMicroservice.entity.Activity;
import fitnessApp.ActivityMicroservice.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    UserValidationService userValidationService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    String queue;
    @Value("${rabbitmq.exchange.name}")
    String exchange;
    @Value("${rabbitmq.routing.key}")
    String routingKey;

    public ResponseEntity<String> addActivity(UserActivityResponse response)
    {
        if(userValidationService.validateUser(response.getUserid())){
            return mapTo(response);
        }
        return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> mapTo(UserActivityResponse response){
        Activity activity = new Activity();
        activity.setUserid(response.getUserid());
        activity.setType(response.getType());
        activity.setDuration(response.getDuration());
        activity.setCalories(response.getCalories());
        activity.setStartTime(response.getStartTime());
        activity.setAdditionalMetrics(response.getAdditionalMetrics());
        Activity savedActivity = activityRepository.save(activity);
        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
        }catch(Exception e){
            log.error("Unable to send the activity to RabbitMQ : "+e);
        }
        return new ResponseEntity<>("Activity inserted",HttpStatus.OK);
    }

    public ResponseEntity<List<Activity>> getActivity(Long userid) {
        List<Activity> activities = activityRepository.findByUserid(userid);
        return new ResponseEntity<>(activities,HttpStatus.OK);
    }

}
