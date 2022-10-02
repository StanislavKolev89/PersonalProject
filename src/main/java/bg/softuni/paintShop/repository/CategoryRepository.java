package bg.softuni.paintShop.repository;

import bg.softuni.paintShop.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByName(String name);

    Optional<CategoryEntity> findByName(String name);
}
