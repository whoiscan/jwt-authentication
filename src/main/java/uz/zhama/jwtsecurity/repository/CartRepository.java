package uz.zhama.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zhama.jwtsecurity.entity.Cart;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.CartReq;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> getAllCart_itemsByUserId(Integer id);
    @Transactional
    @Override
    <S extends Cart> S save(S s);
    Optional<Cart> findIdByUserId(Integer userId);
    Optional<Cart> findByUserId(Integer userId);
    Cart getIdByUserId(Integer userId);
    Cart getCartIdByUserId(Integer userId);
    Boolean existsCartByUserId(Integer userId);
}
