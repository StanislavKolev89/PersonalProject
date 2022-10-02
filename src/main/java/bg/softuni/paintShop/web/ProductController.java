package bg.softuni.paintShop.web;

import bg.softuni.paintShop.model.dto.QuantityHolderDTO;
import bg.softuni.paintShop.model.view.CategoryViewModel;
import bg.softuni.paintShop.model.view.ProductViewModel;
import bg.softuni.paintShop.service.CategoryService;
import bg.softuni.paintShop.service.ProductService;
import bg.softuni.paintShop.service.UserService;
import bg.softuni.paintShop.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final WarehouseService productQuantityService;
    private final ModelMapper modelMapper;


    @GetMapping("/{category}")
    public String oneCategoryPage(@PathVariable String category, Model model) {
        List<ProductViewModel> productViewModels = productService.getFilteredProducts(category)
                .stream().map(productDTO -> modelMapper.map(productDTO, ProductViewModel.class)).collect(Collectors.toList());
        model.addAttribute("chosenCategoryProducts", productViewModels);
        model.addAttribute("categoryName", category);
        model.addAttribute("itemCount", productViewModels.size());

        return "one-category-products";
    }

    @GetMapping("/all")
    public String productsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts()
                .stream().map(productDTO -> modelMapper.map(productDTO, ProductViewModel.class))
                .collect(Collectors.toList()));
        model.addAttribute("allCategories", categoryService.filterDeleteDCategories()
                .stream().map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList()));

        return "products";
    }


    @GetMapping("/all/{id}")
    public String productDetails(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("productViewModel", modelMapper.map(productService.findProductById(id), ProductViewModel.class));
        model.addAttribute("active", userService.findByName(principal.getName()).isActive());
        model.addAttribute("itemsLeft", productQuantityService.itemsLeft(id));
        return "product-details";
    }


    @ModelAttribute
    public QuantityHolderDTO quantityHolderDTO() {
        return new QuantityHolderDTO();
    }


}
