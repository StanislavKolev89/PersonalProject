package bg.softuni.paintShop.repository;

import bg.softuni.paintShop.model.entity.UsedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedProductRepository extends JpaRepository<UsedProductEntity, Long> {
}
