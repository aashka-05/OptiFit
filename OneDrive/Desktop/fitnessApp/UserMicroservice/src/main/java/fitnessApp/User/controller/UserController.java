package fitnessApp.User.controller;

import fitnessApp.User.dto.UserResponse;
import fitnessApp.User.services.UserService;
import fitnessApp.User.dto.UserData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserResponse userResponse){
        return userService.registerUser(userResponse);
    }
    @GetMapping("/viewUser/{id}")
    public ResponseEntity<UserData> viewUser(@PathVariable Long id){
        return userService.findUser(id);
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable Long userId){
        return userService.existByUserId(userId);
    }
}
