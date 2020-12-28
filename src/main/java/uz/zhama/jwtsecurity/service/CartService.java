package uz.zhama.jwtsecurity.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.models.*;

import java.util.List;

public interface CartService {
    ResponseEntity<Result> addProductToCart(CartReq cartReq);
    JsonSend getProductsOfCart(Integer userId);
    ResponseEntity<Result> removeProductFromCart(CartDelReq cartDelReq);
//    ResponseEntity<Result> deleteProduct(Integer id);
}
