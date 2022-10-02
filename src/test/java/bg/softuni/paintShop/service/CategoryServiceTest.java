package bg.softuni.paintShop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.dto.CategoryDTO;
import bg.softuni.paintShop.model.entity.CategoryEntity;
import bg.softuni.paintShop.repository.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService mockedService;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper modelMapper;

    private CategoryEntity categoryOne;

    private CategoryEntity categoryTwo;


    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        modelMapper = new ModelMapper();
        categoryOne = new CategoryEntity();
        categoryOne.setName("CATONE");
        categoryOne.setImageUrl("BLABLA");
        categoryOne.setDeleted(false);
        categoryTwo = new CategoryEntity();
        categoryTwo.setName("CATTWO");
        categoryTwo.setImageUrl("BLABLABLA");
        categoryTwo.setDeleted(false);

    }


    @Test
    void getAllCategories() {

        when(categoryRepository.findAll()).thenReturn(List.of(categoryOne, categoryTwo));
        List<CategoryDTO> allCategories = mockedService.getAllCategories();
//        assertThat(allCategories).isNotNull();
        assertThat(allCategories.size()).isEqualTo(2);

    }

    @Test
    void getAllCategoriesThrowsException() {
        when(categoryRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            mockedService.getAllCategories();
        });

    }

    @Test
    void getCategoryDTOThrowsException() {
        when(categoryRepository.findById(1l)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            mockedService.getCategoryDTO(1L);
        });
    }


    @Test
    void getCategoryDTO() {
        categoryRepository.save(categoryOne);
        when(categoryRepository.findById(1l)).thenReturn(Optional.of(categoryOne));
        CategoryDTO categoryDTO = mockedService.getCategoryDTO(1L);
        Assert.notNull(categoryDTO);
    }

    @Test
    void changeCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryOne));
        when(categoryRepository.save(categoryOne)).thenReturn(categoryOne);
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("CLEAR");
        categoryDTO.setImageUrl("lasdasdasd");
        mockedService.changeCategory(categoryDTO, 1L);
        assertThat(categoryOne.getName()).isEqualTo("CLEAR");
        verify(categoryRepository, times(1)).save(any());

    }

    @Test
    void changeCategory_ThrowsException() {
        Assertions.assertThrows(ObjectNotFoundException.class, () ->
                mockedService.changeCategory(new CategoryDTO(), 1L));
    }

    @Test
    void deleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryOne));
        mockedService.deleteCategory(1L);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void addCategory() {

        when(categoryRepository.findByName("CATONE")).thenReturn(Optional.of(categoryOne));
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("CATONE");
        categoryDTO.setImageUrl("IMAGEURL");

        mockedService.addCategory(categoryDTO);
        CategoryEntity save = verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void passedCategoryExists() {
        when(categoryRepository.existsByName("Name")).thenReturn(true);
        mockedService.passedCategoryExists("Name");
    }

    @Test
    void filterDeleteDCategories() {
    }

    @Test
    void findCategoryByName() {
        when(categoryRepository.findByName("CATONE")).thenReturn(Optional.of(categoryOne));
        CategoryEntity catOne = mockedService.findCategoryByName("CATONE");
        assertThat(catOne).isNotNull();
    }
}