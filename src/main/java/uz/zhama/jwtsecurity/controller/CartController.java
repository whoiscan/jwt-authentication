package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import uz.zhama.jwtsecurity.entity.User;
import uz.zhama.jwtsecurity.models.*;
import uz.zhama.jwtsecurity.repository.UserRepository;
import uz.zhama.jwtsecurity.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
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