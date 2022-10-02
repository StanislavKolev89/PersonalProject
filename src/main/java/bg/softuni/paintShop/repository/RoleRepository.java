package bg.softuni.paintShop.repository;

import bg.softuni.paintShop.model.entity.RoleEntity;
import bg.softuni.paintShop.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findRoleEntityByName(RoleEnum name);


}
