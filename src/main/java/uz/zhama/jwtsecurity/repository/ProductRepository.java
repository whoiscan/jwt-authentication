package uz.zhama.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zhama.jwtsecurity.entity.Product;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Transactional()
    @Override
    <S extends Product> S save(S s);

    List<Product> findAllByCategoryId(Integer id);

    Optional<Product> findById(Integer id);

    Optional<Product> findByName(String name);
}
