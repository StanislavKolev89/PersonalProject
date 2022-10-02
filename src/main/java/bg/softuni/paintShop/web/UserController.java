package bg.softuni.paintShop.web;

import bg.softuni.paintShop.model.dto.UserDTO;
import bg.softuni.paintShop.model.dto.UserEditDTO;
import bg.softuni.paintShop.model.dto.UserRegisterDTO;
import bg.softuni.paintShop.model.view.UserViewModel;
import bg.softuni.paintShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, userName);
        redirectAttributes.addFlashAttribute("bad_credentials",
                true);

        return "redirect:/users/login";
    }


    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);

            return "redirect:/users/register";
        }

        userService.registerAndLoginUser(userRegisterDTO);
        return "redirect:/";
    }


    @GetMapping("/profile")
    public String myProfilePage(Principal principal, Model model) {
        if (!model.containsAttribute("userEditDTO")) {
            model.addAttribute("userEditDTO", new UserEditDTO());
        }
        UserDTO loggedUserDetails = userService.getLoggedUserDetails(principal);
        model.addAttribute("loggedUser", modelMapper.map(loggedUserDetails, UserViewModel.class));
        return "profile";
    }

    @PostMapping("/profile")
    public String profileChangeConfirm(Principal principal, @Valid UserEditDTO userEditDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO", bindingResult);

            return "redirect:/users/profile";
        }
        userService.changeUserData(userEditDTO, principal);

        return "redirect:/users/profile";
    }


    @ModelAttribute
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

}

