package bg.softuni.paintShop.model.view;

import bg.softuni.paintShop.model.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserViewModel {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private boolean active;

    private RoleEntity roleEntity;
}
