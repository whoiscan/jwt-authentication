package uz.zhama.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zhama.jwtsecurity.entity.CartItems;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cart_itemsRepository extends JpaRepository<CartItems, Integer> {
    List<CartItems> getAllProductByCartId(Integer cart_id);

    Optional<CartItems> findByCartIdAndProductId(Integer cart_id, Integer product_id);

    CartItems getByCartIdAndProductId(Integer cart_id, Integer product_id);

    Optional<CartItems> findOneByCartIdAndProductId(Integer cart_id, Integer product_id);

    Boolean existsByCartIdAndProductId(Integer cartId, Integer productId);
}
