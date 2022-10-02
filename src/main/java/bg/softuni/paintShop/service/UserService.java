package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.UserDTO;
import bg.softuni.paintShop.model.dto.UserEditDTO;
import bg.softuni.paintShop.model.dto.UserRegisterDTO;
import bg.softuni.paintShop.model.entity.UserEntity;
import bg.softuni.paintShop.model.view.UserViewModel;
import bg.softuni.paintShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final OrderProductService orderProductService;

    private void loginUser(UserEntity userEntity) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
              userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    public void registerAndLoginUser(UserRegisterDTO userRegisterDto) {
        UserEntity user = modelMapper.map(userRegisterDto, UserEntity.class);
        user.setRole(roleService.getUserRole());

        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);

        loginUser(user);
    }


    public UserEntity findByName(String principalName) {
        return userRepository.findByEmail(principalName).orElseThrow(ObjectNotFoundException::new);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().filter(userEntity -> !userEntity.getEmail().contains("DELETED"))
              .map(userEntity -> modelMapper.map(userEntity, UserDTO.class)).collect(Collectors.toList());
    }

    @SuppressWarnings("UnusedReturnValue")
    public BigDecimal userPurchaseTotal(UserViewModel userViewModel) {
        return orderProductService.findAllUsersProducts(userViewModel.getId()).stream()
              .map(order -> order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
              .reduce(BigDecimal::add)
              .orElse(BigDecimal.ZERO);
    }

    //Usage in template
    public BigDecimal grossSales() {
        return orderProductService.findAll().stream().filter(order -> order.getOrder().getUser().getId() != 1)
              .map(order -> order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
              .reduce(BigDecimal::add)
              .orElse(BigDecimal.ZERO);
    }


    public void makeUserNotActive(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        user.setActive(false);
        userRepository.save(user);
    }

    public void makeUserActive(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        user.setActive(true);
        userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setUsername("*** " + user.getUsername() + " DELETED");
            user.setEmail("*** "+ user.getEmail()+" DELETED");
            user.setActive(false);
            userRepository.save(user);
        });
    }


    public UserDTO getLoggedUserDetails(Principal principal) {
        return userRepository.findByEmail(principal.getName())
              .map(user -> modelMapper.map(user, UserDTO.class))
              .orElse(null);
    }

    public void changeUserData(UserEditDTO userEditDTO, Principal principal) {
        userRepository.findByEmail(principal.getName()).ifPresent(user -> {
            user.setUsername(userEditDTO.getUsername());
            user.setFirstName(userEditDTO.getFirstName());
            user.setLastName(userEditDTO.getLastName());
            user.setAddress(userEditDTO.getAddress());
            userRepository.save(user);
        });
    }


    public void removeUserAdminRights(Long id) {
        userRepository.findById(id).ifPresent(userEntity -> {
            userEntity.setRole(roleService.getUserRole());
            userRepository.save(userEntity);
        });
    }

    public void giveUserAdminRights(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole(roleService.getAdminRole());
            userRepository.save(user);
        });
    }

    public Long loggedUserId(Principal principal) {
        return userRepository.findByEmail(principal.getName()).map(UserEntity::getId).orElse(null);
    }

    public String getPrincipalUsername(Principal principal) {
        return userRepository.findByEmail(principal.getName()).map(UserEntity::getUsername).orElse(null);
    }

}
