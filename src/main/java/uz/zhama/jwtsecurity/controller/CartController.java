package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uz.zhama.jwtsecurity.models.CartReq;
import uz.zhama.jwtsecurity.models.OrderResponse;
import uz.zhama.jwtsecurity.models.ProductReq;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Result> addProductToCart(@RequestBody CartReq cartReq) {
        return cartService.addProductToCart(cartReq);
    }
    @GetMapping("/get/all/{id}")
    public ResponseEntity<List<OrderResponse>> getProductsOfCart(@PathVariable Integer id){
        return cartService.getProductsOfCart(id);
    }
    @GetMapping("/remove/{user_id}/{product_id}")
    public ResponseEntity<Result> removeProductFromCart(@PathVariable Integer user_id, @PathVariable Integer product_id){
        return cartService.removeProductFromCart(user_id,product_id);
    }
}