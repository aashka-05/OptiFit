package fitnessApp.User.services;
import fitnessApp.User.dto.UserData;
import fitnessApp.User.dto.UserResponse;
import fitnessApp.User.entity.User;
import fitnessApp.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public ResponseEntity<String> registerUser(UserResponse userResponse) {

        User user = new User();
        user.setEmail(userResponse.getEmail());
        user.setPassword(userResponse.getPassword());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());

        userRepo.save(user);
        return new ResponseEntity<>("Registered successfully", HttpStatus.CREATED);
    }
    public ResponseEntity<UserData> findUser(Long id) {

        return userRepo.findById(id)
                .map(this::toUserData)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<UserData> toUserData(User user) {
        UserData data = new UserData();
        data.setId(user.getId());
        data.setEmail(user.getEmail());
        data.setFirstName(user.getFirstName());
        data.setLastName(user.getLastName());
        data.setUserRole(user.getUserRole().name());
        data.setCreatedAt(user.getCreatedAt());
        data.setUpdatedAt(user.getUpdatedAt());

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Boolean> existByUserId(Long userId) {
        System.out.println("Service called, results : "+userRepo.existsById(userId));
        return new ResponseEntity<>(userRepo.existsById(userId),HttpStatus.OK);
    }
}
