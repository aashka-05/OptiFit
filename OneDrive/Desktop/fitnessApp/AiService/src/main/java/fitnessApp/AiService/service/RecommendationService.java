package fitnessApp.AiService.service;

import fitnessApp.AiService.entity.Recommendation;
import fitnessApp.AiService.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    RecommendationRepository recommendationRepository;
    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId).orElseThrow(() -> new RuntimeException("Activity not found"));
    }

    public List<Recommendation> getUserRecommendation(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }
}
