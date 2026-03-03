package fitnessApp.User.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponse {
    @NotBlank(message = "This field is required")
    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "This field is required")
    private String password;

    private String firstName;
    private String lastName;

}
