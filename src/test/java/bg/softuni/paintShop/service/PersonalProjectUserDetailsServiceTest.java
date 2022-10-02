package bg.softuni.paintShop.service;


import bg.softuni.paintShop.model.entity.RoleEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.model.enums.RoleEnum;
import bg.softuni.paintShop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonalProjectUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepo;

    private PaintShopDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new PaintShopDetailsService(
                mockUserRepo
        );
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(RoleEnum.ADMIN);
        UserEntity testUserEntity = new UserEntity();
        testUserEntity.setUsername("testcho");
        testUserEntity.setEmail("test@test.com");
        testUserEntity.setFirstName("Test");
        testUserEntity.setLastName("Testov");
        testUserEntity.setAddress("Test Street 1");
        testUserEntity.setActive(true);
        testUserEntity.setPassword("12345");
        testUserEntity.setRole(roleEntity);

        when(mockUserRepo.findByEmail(testUserEntity.getEmail())).
                thenReturn(Optional.of(testUserEntity));

        UserDetails userDetails = toTest.loadUserByUsername(testUserEntity.getEmail());

        Assertions.assertEquals(testUserEntity.getEmail(), userDetails.getUsername());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(1, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + RoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());


    }


    @Test
    void testLoadUserByUsername_UserDoesNotExists() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@example.com")
        );
    }
}
