package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.CategoryDTO;
import bg.softuni.paintShop.model.entity.CategoryEntity;
import bg.softuni.paintShop.repository.CategoryRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categoryDTOS = categoryRepository.findAll().stream().
              map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class)).collect(Collectors.toList());
        if (categoryDTOS.isEmpty()) {
            throw new ObjectNotFoundException();
        }
        return categoryDTOS;
    }

    public CategoryDTO getCategoryDTO(Long id) {
        return categoryRepository.findById(id)
              .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
              .orElseThrow(ObjectNotFoundException::new);
    }

    public void changeCategory(CategoryDTO categoryDto, Long productId) {
        CategoryEntity categoryEntity = categoryRepository.findById(productId).orElseThrow(ObjectNotFoundException::new);
        categoryEntity.setName(categoryDto.getName().toUpperCase(Locale.ROOT));
        categoryEntity.setImageUrl(categoryDto.getImageUrl());
        categoryRepository.save(categoryEntity);
    }

    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        categoryEntity.setDeleted(true);
        categoryRepository.save(categoryEntity);
    }

    public void addCategory(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> category = categoryRepository.findByName(categoryDTO.getName());
        if (!category.isEmpty()) {
            category.get().setImageUrl(categoryDTO.getImageUrl());
            category.get().setDeleted(false);
        }
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        categoryEntity.setName(categoryDTO.getName().toUpperCase(Locale.ROOT));
        categoryEntity.setDeleted(false);
        categoryRepository.save(categoryEntity);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean passedCategoryExists(String category) {
        return categoryRepository.existsByName(category);
    }


    public List<CategoryDTO> filterDeleteDCategories() {
        return getAllCategories().stream().filter(categoryDTO -> !categoryDTO.isDeleted())
              .collect(Collectors.toList());
    }

    public CategoryEntity findCategoryByName(String name) {

        return categoryRepository.findByName(name).orElseThrow(ObjectNotFoundException::new);
    }
}
