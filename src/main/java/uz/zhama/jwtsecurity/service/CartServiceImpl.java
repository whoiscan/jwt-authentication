package uz.zhama.jwtsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.zhama.jwtsecurity.entity.Cart;
import uz.zhama.jwtsecurity.entity.Cart_items;
import uz.zhama.jwtsecurity.models.*;
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
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final Cart_itemsRepository cart_itemsRepository;
    private final SecurityUtil securityUtil;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, Cart_itemsRepository cart_itemsRepository, SecurityUtil securityUtil) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cart_itemsRepository = cart_itemsRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    public ResponseEntity<Result> addProductToCart(CartReq cartReq) {
        Result result = new Result();

        if (cartRepository.existsCartByUserId(securityUtil.getCurrentUser().getId())) {
            Cart cartId = cartRepository.getCartIdByUserId(securityUtil.getCurrentUser().getId());
            if (cartId != null && cart_itemsRepository.existsByCartIdAndProductId(cartId.getId(), cartReq.getProductId())) {
                Cart_items cart_items = cart_itemsRepository.getByCartIdAndProductId(cartId.getId(), cartReq.getProductId());
                cart_items.setQuantity(cart_items.getQuantity() + cartReq.getQuantity());
                cart_itemsRepository.save(cart_items);
                return getResultResponseEntity(cartReq, result, cart_items);
            } else {
                Cart_items cart_items = new Cart_items();
                cart_items.setCreatedDate(Date.from(Instant.now()));
                cart_items.setQuantity(cartReq.getQuantity());
                Cart cart = cartRepository.getIdByUserId(securityUtil.getCurrentUser().getId());
                if (cart != null)
                    cart_items.setCart(cart);
                else {
                    result.setSuccess(false);
                    result.setMessage("No cart found by this user id");
                }
                return getResultResponseEntity(cartReq, result, cart_items);
            }
        } else {
            Cart cart = new Cart();
            if (securityUtil.getCurrentUser().toString() != null && cartReq.getProductId() != null) {
                cart.setUser(securityUtil.getCurrentUser());
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
        if (securityUtil.getCurrentUser().toString() != null && cartReq.getProductId() != null) {

            if (productRepository.findById(cartReq.getProductId()).isPresent()) {
                cart_items.setProduct(productRepository.getOne(cartReq.getProductId()));
                cart_items.setUpdatedDate(Date.from(Instant.now()));
            } else {
                result.setSuccess(false);
                result.setMessage("Product not found");
                return ResponseEntity.status(400).body(result);
            }
            cart_itemsRepository.save(cart_items);
            result.setSuccess(true);
            result.setMessage(productRepository.getOne(cartReq.getProductId()).getName() + " is successfully added");
            return ResponseEntity.ok(result);
        } else {
            result.setSuccess(false);
            result.setMessage("Product Id or User Id is not found");
            return ResponseEntity.status(400).body(result);
        }
    }

    @Override
    public JsonSend getProductsOfCart(Integer userId) {
        List<Cart_items> products = null;
        Optional<Cart> cart = cartRepository.findIdByUserId(userId);
        if (cart.isEmpty())
            return JsonSend.error("No user found", "500");
        else {
            products = cart_itemsRepository.getAllProductByCartId(cart.get().getId());
            if (products != null && products.size() != 0) {
                List<OrderResponse> orderResponses = new ArrayList<>();
                for (Cart_items product : products) {
                    OrderResponse orderResponse = new OrderResponse();
                    orderResponse.setId(product.getId());
                    orderResponse.setQuantity(product.getQuantity());
                    orderResponse.setName(product.getProduct().getName());
                    orderResponse.setPrice(product.getProduct().getPrice());
                    orderResponses.add(orderResponse);
                }
                return JsonSend.success("200", orderResponses);
            } else {
                return JsonSend.error("No products in cart!", "500");
            }
        }
    }

    @Override
    public ResponseEntity<Result> removeProductFromCart(CartDelReq cartDelReq) {
        Result result = new Result();
        Optional<Cart> cart = cartRepository.findIdByUserId(securityUtil.getCurrentUser().getId());
        Optional<Cart_items> optional = cart_itemsRepository.findByCartIdAndProductId(cart.get().getId(), cartDelReq.getProductId());
        if (cart.isPresent() && cart != null) {
            if (optional.isPresent()) {
                Cart_items product = optional.get();
                cart_itemsRepository.delete(product);
                Optional<Cart_items> deletedProduct = optional;
                if (deletedProduct.isPresent()) {
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
                return ResponseEntity.status(500).body(result);
            }
        } else {
            result.setMessage("No user found!");
            result.setSuccess(false);
            return ResponseEntity.status(500).body(result);
        }
    }
}

