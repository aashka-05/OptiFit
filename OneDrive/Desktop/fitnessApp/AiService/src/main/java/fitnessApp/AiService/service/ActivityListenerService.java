package fitnessApp.AiService.service;

import fitnessApp.AiService.entity.Activity;
import fitnessApp.AiService.entity.Recommendation;
import fitnessApp.AiService.repository.RecommendationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivityListenerService {
    @Autowired
    ActivityAiService activityAiService;
    @Autowired
    RecommendationRepository recommendationRepository;


    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        log.info("Receiver activity for recommendation generation of id : {}",activity.getId());
        Recommendation recommendation = activityAiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }
}
