package fitnessApp.AiService.controller;

import fitnessApp.AiService.entity.Recommendation;
import fitnessApp.AiService.service.RecommendationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/user-rec/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable Long userId){
        return new ResponseEntity<>(recommendationService.getUserRecommendation(userId), HttpStatus.OK);

    }

    @GetMapping("/activity-rec/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId){
        return new ResponseEntity<>(recommendationService.getActivityRecommendation(activityId), HttpStatus.OK);
    }

}
