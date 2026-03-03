package fitnessApp.User.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserData {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String userRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
