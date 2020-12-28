package uz.zhama.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zhama.jwtsecurity.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
