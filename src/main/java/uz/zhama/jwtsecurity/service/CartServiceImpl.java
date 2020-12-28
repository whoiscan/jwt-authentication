package uz.zhama.jwtsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.zhama.jwtsecurity.entity.Cart;
import uz.zhama.jwtsecurity.entity.Cart_items;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.CartReq;
import uz.zhama.jwtsecurity.models.OrderResponse;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.repository.CartRepository;
import uz.zhama.jwtsecurity.repository.Cart_itemsRepository;
import uz.zhama.jwtsecurity.repository.ProductRepository;
import uz.zhama.jwtsecurity.repository.UserRepository;


import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Cart_itemsRepository cart_itemsRepository;


    @Override
    public ResponseEntity<Result> addProductToCart(CartReq cartReq) {
        Result result = new Result();
        if (cartRepository.existsCartByUserId(cartReq.getUser().getId())) {
            Cart_items cart_items = new Cart_items();
            cart_items.setCreatedDate(Date.from(Instant.now()));
            cart_items.setQuantity(cartReq.getQuantity());
            cart_items.setCart(cartRepository.findIdByUserId(cartReq.getUser().getId()));
            return getResultResponseEntity(cartReq, result, cart_items);
        } else {
            Cart cart = new Cart();
            if (cartReq.getUser() != null && cartReq.getProduct() != null) {
                cart.setUser(cartReq.getUser());
                cart.setStatus(true);
                cart.setCreatedDate(Date.from(Instant.now()));
                cartRepository.save(cart);
                Cart_items cart_items = new Cart_items();
                cart_items.setCreatedDate(Date.from(Instant.now()));
                cart_items.setQuantity(cartReq.getQuantity());
                cart_items.setCart(cart);
                return getResultResponseEntity(cartReq, result, cart_items);
            } else {
                result.setSuccess(false);
                result.setMessage("Product Id or User Id is not found");
                return ResponseEntity.status(400).body(result);
            }
        }

    }

    private ResponseEntity<Result> getResultResponseEntity(CartReq cartReq, Result result, Cart_items cart_items) {
        if (cartReq.getUser() != null && cartReq.getProduct() != null) {
            if (productRepository.findById(cartReq.getProduct().getId()).isPresent())
                cart_items.setProduct(cartReq.getProduct());
            else{
                result.setSuccess(false);
                result.setMessage("Product not found");
                return ResponseEntity.status(400).body(result);
            }

            Cart_items savedCart = cart_itemsRepository.save(cart_items);
            if (savedCart != null) {
                result.setSuccess(true);
                result.setMessage(productRepository.getOne(cartReq.getProduct().getId()).getName() + " is successfully added");
                return ResponseEntity.ok(result);
            } else {
                result.setSuccess(false);
                result.setMessage("Not Saved");
                return ResponseEntity.status(400).body(result);
            }
        } else {
            result.setSuccess(false);
            result.setMessage("Product Id or User Id is not found");
            return ResponseEntity.status(400).body(result);
        }
    }

    @Override
    public ResponseEntity<List<OrderResponse>> getProductsOfCart(Integer userId) {
        List<Cart_items> products = cart_itemsRepository.getAllProductByCartId(cartRepository.findIdByUserId(userId).getId());
        if (products != null) {
            List<OrderResponse> orderResponses = new ArrayList<>();
            for (Cart_items product : products) {
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setId(product.getId());
                orderResponse.setQuantity(product.getQuantity());
                orderResponse.setName(product.getProduct().getName());
                orderResponse.setPrice(product.getProduct().getPrice());
                orderResponses.add(orderResponse);
            }
            return ResponseEntity.ok(orderResponses);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Result> removeProductFromCart(Integer user_id, Integer product_id) {
        Optional<Cart_items> optional = cart_itemsRepository.findByCartIdAndProductId(cartRepository.findIdByUserId(user_id).getId(), product_id);
        Result result = new Result();
        if (optional.isPresent()) {
            Cart_items product = optional.get();
            cart_itemsRepository.delete(product);
            Optional<Cart_items> deletedProduct = cart_itemsRepository.getOneByCartIdAndProductId(cartRepository.findIdByUserId(user_id).getId(), product_id);
            if (deletedProduct.isEmpty() || !deletedProduct.isPresent()) {
                result.setSuccess(true);
                result.setMessage(product.getProduct().getName() + " successfully deleted");
                return ResponseEntity.ok(result);
            } else {
                result.setSuccess(false);
                result.setMessage(product.getProduct().getName() + " not deleted");
                return ResponseEntity.status(500).body(result);
            }
        } else {
            result.setSuccess(false);
            result.setMessage("Product not found");
            return ResponseEntity.status(400).body(result);
        }
    }
}

