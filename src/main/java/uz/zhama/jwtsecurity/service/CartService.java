package uz.zhama.jwtsecurity.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.models.CartReq;
import uz.zhama.jwtsecurity.models.OrderResponse;
import uz.zhama.jwtsecurity.models.Result;

import java.util.List;

public interface CartService {
    ResponseEntity<Result> addProductToCart(CartReq cartReq);
    ResponseEntity<List<OrderResponse>> getProductsOfCart(Integer userId);
    ResponseEntity<Result> removeProductFromCart(Integer user_id, Integer product_id);
//    ResponseEntity<Result> deleteProduct(Integer id);
}
