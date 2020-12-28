package uz.zhama.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zhama.jwtsecurity.entity.Cart_items;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cart_itemsRepository extends JpaRepository<Cart_items, Integer> {
    List<Cart_items> getAllProductByCartId(Integer cart_id);
    Optional<Cart_items> findByCartIdAndProductId(Integer cart_id, Integer product_id);
    Optional<Cart_items> getOneByCartIdAndProductId(Integer cart_id, Integer product_id);
    Boolean existsProductByCartId(Integer cart_id);
}
