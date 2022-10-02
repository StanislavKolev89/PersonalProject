package bg.softuni.paintShop.service;

import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.model.entity.ProductQuantityTrackerEntity;
import bg.softuni.paintShop.repository.WarehouseTrackerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @InjectMocks
    private WarehouseService mockedService;

    @Mock
    private WarehouseTrackerRepository warehouseTrackerRepository;

    private ProductQuantityTrackerEntity productQuantityTrackerEntityOne = new ProductQuantityTrackerEntity();

    private ProductQuantityTrackerEntity productQuantityTrackerEntityTwo = new ProductQuantityTrackerEntity();

    private Logger LOGGER = LoggerFactory.getLogger(WarehouseService.class);

    private ProductEntity productOne = new ProductEntity();

    private ProductEntity productTwo = new ProductEntity();


    @BeforeEach
    void setUp() {
        productOne.setId(1L);
        productOne.setTitle("DRILL");
        productQuantityTrackerEntityOne.setQuantity(100);
        productQuantityTrackerEntityOne.setId(1L);
        productQuantityTrackerEntityOne.setProduct(productOne);

        productQuantityTrackerEntityTwo.setQuantity(100);
        productQuantityTrackerEntityTwo.setId(2L);
        productQuantityTrackerEntityTwo.setProduct(productTwo);
    }

    @Test
    void decreaseStock() {
        Mockito.when(warehouseTrackerRepository.findById(1L)).thenReturn(Optional.of(productQuantityTrackerEntityOne));
        mockedService.decreaseStock(1L, 10);
    }

    @Test
    void itemsLeft() {
        Mockito.when(warehouseTrackerRepository.findById(1L)).thenReturn(Optional.of(productQuantityTrackerEntityOne));
        int itemsLeft = mockedService.itemsLeft(1L);
        Assertions.assertThat(itemsLeft).isEqualTo(100);
    }

    @Test
    void alertIfInventoryLowNotThrowingException() {
        Mockito.when(warehouseTrackerRepository.findAll()).thenReturn(List.of(productQuantityTrackerEntityOne, productQuantityTrackerEntityTwo));
        mockedService.alertIfInventoryLow();
    }

    @Test
    void alertIfInventoryLowThrowingException() {
        productQuantityTrackerEntityOne.setQuantity(15);
        productQuantityTrackerEntityTwo.setQuantity(17);
        Mockito.when(warehouseTrackerRepository.findAll()).thenReturn(List.of(productQuantityTrackerEntityOne, productQuantityTrackerEntityTwo));
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () ->
                mockedService.alertIfInventoryLow());
    }

    @Test
    void trackInventory() {
        Mockito.when(warehouseTrackerRepository.findAll()).thenReturn(List.of(productQuantityTrackerEntityOne, productQuantityTrackerEntityTwo));
        mockedService.trackInventory();
    }
}