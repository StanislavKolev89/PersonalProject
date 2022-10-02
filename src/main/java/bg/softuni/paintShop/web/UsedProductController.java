package bg.softuni.paintShop.web;

import bg.softuni.paintShop.model.dto.UsedProductDTO;
import bg.softuni.paintShop.model.view.CategoryViewModel;
import bg.softuni.paintShop.model.view.UsedProductViewModel;
import bg.softuni.paintShop.service.CategoryService;
import bg.softuni.paintShop.service.UsedProductService;
import bg.softuni.paintShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/used")
public class UsedProductController {

    private final UsedProductService usedProductService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping("/products/forSale")
    private String secondHandProducts(Model model) {
        model.addAttribute("usedProducts", usedProductService.getAllProducts()
                .stream().map(usedProductDTO -> modelMapper.map(usedProductDTO, UsedProductViewModel.class))
                .collect(Collectors.toList()));
        model.addAttribute("allCategories", categoryService.getAllCategories()
                .stream().map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));

        return "second-hand-products";
    }


    @GetMapping("/products/details/{id}")
    private String secondHandProducts(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("sellerName", usedProductService.getSellerName(id));
        model.addAttribute("productViewModel", modelMapper
                .map(usedProductService.getProductById(id), UsedProductViewModel.class));
        model.addAttribute("allCategories", categoryService.getAllCategories()
                .stream().map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));
        model.addAttribute("principalUsername", userService.getPrincipalUsername(principal));
        model.addAttribute("adminIsLogged",userService.loggedUserId(principal));
        return "used-product-details";
    }


    @PostMapping("/products/details/{id}")
    public String confirmEdit(@PathVariable("id") Long id, Model model, @Valid UsedProductDTO usedProductDTO, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usedProductDTO", usedProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usedProductDTO", bindingResult);

            return "redirect:/used/products/details/{id}";
        }
        usedProductService.editProducts(usedProductDTO, id);
        return "redirect:/used/products/forSale";
    }


    @GetMapping("/products/add")
    public String secondHandProductAddPage(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "add-used-product";
    }

    @PostMapping("/products/add")
    private String editProductConfirm(Principal principal, Model model, @Valid UsedProductDTO usedProductDTO
            , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("usedProductDTO", usedProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.usedProductDTO", bindingResult);

            return "redirect:/used/products/add";
        }
        usedProductService.addNewProduct(usedProductDTO, principal);
        return "redirect:/used/products/forSale";
    }

    @GetMapping("/products/delete/{id}")
    public String secondHandProductADeletePage(@PathVariable("id") Long id) {
        usedProductService.deleteProduct(id);

        return "redirect:/used/products/forSale";
    }

    @ModelAttribute
    public UsedProductDTO usedProductDTO() {
        return new UsedProductDTO();
    }

}
