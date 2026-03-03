package fitnessApp.ActivityMicroservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class UserValidationService {
    @Autowired
    WebClient userServiceWebClient;
    public boolean validateUser(Long userid) {
        try{
            return userServiceWebClient.get().uri("/user/validate/{userid}",userid).retrieve().bodyToMono(Boolean.class).block();
        }
        catch(WebClientResponseException e){
            if(e.getStatusCode()== HttpStatus.NOT_FOUND){
                throw new RuntimeException("User not found");
            }
            if(e.getStatusCode()== HttpStatus.BAD_REQUEST){
                throw new RuntimeException("Invalid user id");
            }
        }
        return false;
    }
}
