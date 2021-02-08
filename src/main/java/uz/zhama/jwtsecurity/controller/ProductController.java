package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.ProductReq;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.service.ProductService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/get/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
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
