package bg.softuni.paintShop.web;


import bg.softuni.paintShop.model.dto.CategoryDTO;
import bg.softuni.paintShop.model.dto.ProductDTO;
import bg.softuni.paintShop.model.view.CategoryViewModel;
import bg.softuni.paintShop.model.view.OrderViewModel;
import bg.softuni.paintShop.model.view.ProductViewModel;
import bg.softuni.paintShop.model.view.UserViewModel;
import bg.softuni.paintShop.service.*;
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
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final OrderProductService orderProductService;

    @GetMapping
    public String adminPage() {
        return "admin-panel";
    }

    @GetMapping("/categories/edit/{id}")
    private String categoryEditPage(@PathVariable("id") Long id, Model model) {

        model.addAttribute("categoryData", categoryService.getCategoryDTO(id));
        return "change-category";
    }

    @PostMapping("/categories/edit/{id}")
    private String categoryEditConfirm(@PathVariable("id") Long id, @Valid CategoryDTO categoryDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryDTO", categoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryDTO", bindingResult);

            return "redirect:/admin/categories/edit/{id}";
        }
        categoryService.changeCategory(categoryDTO, id);
        return "redirect:/admin";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories/all";
    }

    @GetMapping("/categories/add")
    public String categoryAddPage() {
        return "add-category";
    }

    @GetMapping("/categories/all")
    public String allCategoriesPage(Model model) {
        model.addAttribute("categories", categoryService.filterDeleteDCategories()
                .stream().map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return "category-admin";
    }

    @PostMapping("/categories/add")
    public String categoryAddConfirm(@Valid CategoryDTO categoryDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryDTO", categoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryDTO", bindingResult);

            return "redirect:/admin/categories/add";
        }
        categoryService.addCategory(categoryDTO);
        return "redirect:/admin/categories/all";
    }


    @GetMapping("/users/all")
    public String allUsersPage(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll()
                .stream().map(userDTO -> modelMapper.map(userDTO, UserViewModel.class)).collect(Collectors.toList()));
        model.addAttribute("userService", userService);
        model.addAttribute("count", userService.findAll().size());
        model.addAttribute("loggedUserId", userService.loggedUserId(principal));
        return "users-admin";
    }

    @GetMapping("/users/deactivate/{id}")
    public String makeUserNotActive(@PathVariable("id") Long id) {
        userService.makeUserNotActive(id);
        return "redirect:/admin/users/all";
    }

    @GetMapping("/users/activate/{id}")
    public String makeUserActive(@PathVariable("id") Long id) {
        userService.makeUserActive(id);
        return "redirect:/admin/users/all";
    }

    @GetMapping("/users/makeAdmin/{id}")
    public String giveUserAdminRights(@PathVariable("id") Long id) {
        userService.giveUserAdminRights(id);
        return "redirect:/admin/users/all";
    }

    @GetMapping("/users/removeAdmin/{id}")
    public String removeUserAdminRights(@PathVariable("id") Long id) {
        userService.removeUserAdminRights(id);
        return "redirect:/admin/users/all";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/all";
    }


    @GetMapping("/orders/all")
    public String allOrdersPage(Model model) {
        model.addAttribute("allOrders", orderService.getAllOrders().stream()
                .map(orderDTO -> modelMapper.map(orderDTO, OrderViewModel.class)).
                collect(Collectors.toList()));
        model.addAttribute("count", orderService.getAllOrders().size());
        model.addAttribute("orderService", orderService);
        model.addAttribute("totalTurnover", orderProductService.findTurnover());
        return "orders-admin";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders/all";
    }

    @GetMapping("/orders/details/{id}")
    public String orderDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("allProductsInOrder", orderProductService.findAllOrderProducts(id));
        model.addAttribute("service", orderProductService);

        return "order-products-details";
    }


    @GetMapping("/products/all")
    public String allProductsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts()
                .stream().map(productDTO -> modelMapper.map(productDTO, ProductViewModel.class)).collect(Collectors.toList()));
        model.addAttribute("count", productService.getAllProducts().size());
        return "products-admin";
    }

    @GetMapping("/products/edit/{id}")
    private String editProductPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("productData",
                modelMapper.map(productService.getViewModel(id), ProductViewModel.class));
        model.addAttribute("categories", categoryService.getAllCategories().
                stream().filter(categoryDTO -> !categoryDTO.isDeleted())
                .map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return "edit-product";
    }

    @PostMapping("/products/edit/{id}")
    private String editProductConfirm(@PathVariable("id") Long id, Model model, @Valid ProductDTO productDTO
            , BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        model.addAttribute("categories", categoryService.getAllCategories().
                stream().filter(categoryDTO -> !categoryDTO.isDeleted())
                .map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productDTO", productDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productDTO", bindingResult);

            return "redirect:/admin/products/edit/{id}";
        }
        productService.editProduct(productDTO, id);
        return "redirect:/admin/products/all";
    }

    @GetMapping("/products/add")
    private String addProductPage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories().
                stream().filter(categoryDTO -> !categoryDTO.isDeleted())
                .map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));
        return "add-new-product";
    }

    @PostMapping("/products/add")
    public String addProductConfirm(@Valid ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productDTO", productDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productDTO", bindingResult);

            return "redirect:/admin/products/add";
        }
        productService.addNewProduct(productDTO);
        return "redirect:/admin/products/all";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products/all";
    }

    @ModelAttribute
    public CategoryDTO categoryDTO() {
        return new CategoryDTO();
    }

    @ModelAttribute
    public ProductDTO productDTO() {
        return new ProductDTO();
    }
}
