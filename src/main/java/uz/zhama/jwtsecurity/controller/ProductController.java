package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.ProductCategoryReq;
import uz.zhama.jwtsecurity.models.ProductReq;
import uz.zhama.jwtsecurity.models.ProductResponse;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.repository.ProductRepository;
import uz.zhama.jwtsecurity.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @GetMapping("/product/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(500).build());
    }


    @PostMapping("/product/save")
    public ResponseEntity<Result> addProduct(@RequestBody ProductReq product) {
        return productService.addProduct(product);
    }


    @PutMapping("/product/edit/{id}")
    public ResponseEntity<Product> editProduct(@RequestBody ProductReq productReq, @PathVariable Integer id) {
        return productService.editProduct(id, productReq);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }


}
