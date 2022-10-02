package bg.softuni.paintShop.service;


import bg.softuni.paintShop.model.entity.RoleEntity;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PaintShopDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PaintShopDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.
                findByEmail(username).
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    private UserDetails map(UserEntity userEntity) {
        return
                User.builder().
                        username(userEntity.getEmail()).
                        password(userEntity.getPassword()).
                        authorities(map(userEntity.getRole())).
                        build();
    }

    private GrantedAuthority map(RoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.
                        getName().name());
    }
}
