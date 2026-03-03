package fitnessApp.ActivityMicroservice.controller;

import fitnessApp.ActivityMicroservice.dto.UserActivityResponse;
import fitnessApp.ActivityMicroservice.entity.Activity;
import fitnessApp.ActivityMicroservice.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class activityController {

    @Autowired
    ActivityService activityService;

    @PostMapping("/add-activity")
    public ResponseEntity<String> addActivity(@RequestBody UserActivityResponse response){
        return activityService.addActivity(response);
    }
    @GetMapping("/track-activity")
    public ResponseEntity<List<Activity>> trackUserActivity(@RequestHeader("X-userid") Long id){
        return activityService.getActivity(id);
    }
}
