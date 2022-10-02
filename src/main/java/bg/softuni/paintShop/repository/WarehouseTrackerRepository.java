package bg.softuni.paintShop.repository;

import bg.softuni.paintShop.model.entity.ProductQuantityTrackerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseTrackerRepository extends JpaRepository<ProductQuantityTrackerEntity, Long> {
}
