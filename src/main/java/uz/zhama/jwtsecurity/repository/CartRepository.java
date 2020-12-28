package uz.zhama.jwtsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zhama.jwtsecurity.entity.Cart;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.CartReq;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> getAllCart_itemsByUserId(Integer id);
    @Transactional
    @Override
    <S extends Cart> S save(S s);
    Cart findIdByUserId(Integer userId);

    Boolean existsCartByUserId(Integer userId);
}
