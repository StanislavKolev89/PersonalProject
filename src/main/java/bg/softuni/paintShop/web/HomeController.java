package bg.softuni.paintShop.web;


import bg.softuni.paintShop.model.view.CategoryViewModel;
import bg.softuni.paintShop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String indexPage(Model model) {
        List<CategoryViewModel> collection = categoryService.getAllCategories()
                .stream().map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("firstCategoryItem", getFirstCategory(collection));
        model.addAttribute("allOtherItems", getOtherCategories(collection));

        return "index";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "contacts-page";
    }


    private CategoryViewModel getFirstCategory(List<CategoryViewModel> categories) {
        return categories.stream().map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class)).toList().get(0);
    }

    private List<CategoryViewModel> getOtherCategories(List<CategoryViewModel> categories) {
        return categories.stream().skip(1).map(categoryDTO -> modelMapper.map(categoryDTO, CategoryViewModel.class))
                .collect(Collectors.toList());

    }
}
