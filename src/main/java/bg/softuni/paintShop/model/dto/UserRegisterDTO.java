package bg.softuni.paintShop.model.dto;

import bg.softuni.paintShop.model.validation.PasswordMatch;
import bg.softuni.paintShop.model.validation.UniqueEmail;
import bg.softuni.paintShop.model.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match!")
public class UserRegisterDTO {

    @NotBlank(message = "Username can not be blank!")
    @UniqueUsername(message = "Username already taken!")
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank
    @Size(min = 5, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 20)
    private String lastName;

    @NotBlank
    @Email
    @UniqueEmail(message = "Email already taken!")
    private String email;

    @NotBlank(message = "You must enter some address!")
    @Size(min = 10, max = 50)
    private String address;

    @NotBlank(message = "Password is too short.")
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters!")
    private String password;

    @NotBlank
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters!")
    private String confirmPassword;

}
