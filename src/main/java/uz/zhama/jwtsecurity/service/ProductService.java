package uz.zhama.jwtsecurity.service;

import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.ProductReq;
import uz.zhama.jwtsecurity.models.ProductResponse;
import uz.zhama.jwtsecurity.models.Result;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ResponseEntity<Optional<Product>> getProductById(Integer productId);

    ResponseEntity<Result> addProduct(ProductReq productReq);

    ResponseEntity<Product> editProduct(Integer id, ProductReq productReq);

    ResponseEntity<Result> deleteProduct(Integer id);

    ResponseEntity<List<ProductResponse>> getProductsByCategoryId(Integer id);
}
