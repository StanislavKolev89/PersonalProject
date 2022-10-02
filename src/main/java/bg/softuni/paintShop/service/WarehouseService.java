package bg.softuni.paintShop.service;

import bg.softuni.paintShop.exception.ObjectNotFoundException;
import bg.softuni.paintShop.model.entity.ProductEntity;
import bg.softuni.paintShop.model.entity.ProductQuantityTrackerEntity;
import bg.softuni.paintShop.repository.WarehouseTrackerRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class WarehouseService {

    private final WarehouseTrackerRepository warehouseTrackerRepository;

    public void decreaseStock(Long id, Integer value) {
        warehouseTrackerRepository.findById(id)
                .ifPresent(quantityTracker -> {
                    quantityTracker.setQuantity(quantityTracker.getQuantity() - value);
                    warehouseTrackerRepository.save(quantityTracker);
                });
    }

    public int itemsLeft(Long id) {
        return warehouseTrackerRepository.findById(id)
                .map(ProductQuantityTrackerEntity::getQuantity)
                .orElse(0);
    }

    @Scheduled(cron = "* */1  * * * *")
    public void alertIfInventoryLow() {

        List<ProductQuantityTrackerEntity> trackers = warehouseTrackerRepository.findAll().stream()
                .filter(productQuantityTrackerEntity -> productQuantityTrackerEntity.getQuantity() < 20)
                .toList();
        if (!trackers.isEmpty()) {
            throw new IllegalArgumentException("You have to check stock on hand and reorder if needed ");
        }
    }

    @Scheduled(cron = "* */5 * * * *")
    public void trackInventory() {
        StringBuilder builder = new StringBuilder();
        warehouseTrackerRepository.findAll()
                .forEach(product -> builder.append(String.format("PRODUCT %s -> Quantity %d ", product.getProduct().getTitle(), product.getQuantity()))
                        .append(System.lineSeparator()));
        log.info(builder.toString());
    }

    public void saveNewProduct(ProductEntity productEntity) {
        ProductQuantityTrackerEntity productQuantityTrackerEntity = new ProductQuantityTrackerEntity();
        productQuantityTrackerEntity.setProduct(productEntity);
        productQuantityTrackerEntity.setQuantity(100);
        warehouseTrackerRepository.save(productQuantityTrackerEntity);
    }

    public void deleteProduct(Long id) {
        warehouseTrackerRepository.deleteById(id);
    }


    public void restoreQuantityOfProduct(ProductEntity product, int quantity) {
        ProductQuantityTrackerEntity prTracker = warehouseTrackerRepository.findById(product.getId())
                .orElseThrow(ObjectNotFoundException::new);
        prTracker.setQuantity(prTracker.getQuantity() + quantity);
    }
}
