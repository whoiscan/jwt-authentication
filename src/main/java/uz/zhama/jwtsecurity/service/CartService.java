package uz.zhama.jwtsecurity.service;

import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.models.*;

public interface CartService {
    ResponseEntity<Result> addProductToCart(CartReq cartReq);
    JsonSend getProductsOfCart(Integer userId);
    ResponseEntity<Result> removeProductFromCart(CartDelReq cartDelReq);
}
