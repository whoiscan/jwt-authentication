package uz.zhama.jwtsecurity.service;

import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.models.CartDelReq;
import uz.zhama.jwtsecurity.models.CartReq;
import uz.zhama.jwtsecurity.models.JsonSend;
import uz.zhama.jwtsecurity.models.Result;

public interface CartService {
    ResponseEntity<Result> addProductToCart(CartReq cartReq);

    JsonSend getProductsOfCart(Integer userId);

    ResponseEntity<Result> removeProductFromCart(CartDelReq cartDelReq);
}
