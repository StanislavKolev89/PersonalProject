package bg.softuni.paintShop.model.dto;

import bg.softuni.paintShop.model.entity.RoleEntity;
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
public class UserDTO {

    private Long id;

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
    @Size(min = 10, max = 50, message = "Address too short!")
    private String address;

    private boolean active;

    private RoleEntity roleEntity;
}
