package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.models.CartDelReq;
import uz.zhama.jwtsecurity.models.CartReq;
import uz.zhama.jwtsecurity.models.JsonSend;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addProductToCart(@RequestBody CartReq cartReq) {
        return cartService.addProductToCart(cartReq);
    }

    @GetMapping("/get/all/{id}")
    public JsonSend getProductsOfCart(@PathVariable Integer id) {
        return cartService.getProductsOfCart(id);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Result> removeProductFromCart(@RequestBody CartDelReq cartDelReq) {
        return cartService.removeProductFromCart(cartDelReq);
    }
}