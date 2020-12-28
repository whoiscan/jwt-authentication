package uz.zhama.jwtsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.zhama.jwtsecurity.entity.Product;
import uz.zhama.jwtsecurity.models.ProductCategoryReq;
import uz.zhama.jwtsecurity.models.ProductReq;
import uz.zhama.jwtsecurity.models.ProductResponse;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<Result> addProduct(ProductReq product) {
        Result result = new Result();
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setCategory(product.getCategory());
        newProduct.setPrice(product.getPrice());
        newProduct.setDescription(product.getDescription());
        Optional<Product> optional = productRepository.findByName(product.getName());
        if (optional.isEmpty()) {
            if (product.getCategory() != null) {
                newProduct.setCategory(product.getCategory());
                Product savedProduct = productRepository.save(newProduct);
                if (savedProduct != null) {
                    result.setMessage(savedProduct.getName() + " saved successfully");
                    result.setSuccess(true);
                    return ResponseEntity.ok(result);
                } else {
                    result.setMessage(savedProduct.getName() + " not saved. Server Error");
                    result.setSuccess(false);
                    return ResponseEntity.status(500).body(result);
                }
            } else {
                result.setMessage(product.getName() + " not saved. Incorrect response");
                result.setSuccess(false);
                return ResponseEntity.status(400).body(result);
            }
        } else {
            result.setMessage(product.getName() + " already exists. Product should have a unique name");
            result.setSuccess(false);
            return ResponseEntity.status(409).body(result);
        }
    }

    @Override
    public ResponseEntity<Product> editProduct(Integer id, ProductReq productReq) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product baseProduct = optional.get();
            baseProduct.setName(productReq.getName());
            baseProduct.setPrice(productReq.getPrice());
            baseProduct.setCategory(productReq.getCategory());
            Product changedProduct = productRepository.save(baseProduct);
            if (changedProduct != null) {
                return ResponseEntity.ok(changedProduct);
            } else {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @Override
    public ResponseEntity<Result> deleteProduct(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        Result result = new Result();
        if (optional.isPresent()) {
            Product product = optional.get();
            productRepository.delete(product);
            Product deletedProduct = productRepository.getOne(id);
            if (deletedProduct == null) {
                result.setSuccess(true);
                result.setMessage(product.getName() + "successfully deleted");
                return ResponseEntity.ok(result);
            } else {
                result.setSuccess(false);
                result.setMessage(product.getName() + "not deleted");
                return ResponseEntity.status(500).body(result);
            }
        } else {
            result.setSuccess(false);
            result.setMessage("Product not found");
            return ResponseEntity.status(400).body(result);
        }
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductsByCategoryId(Integer id) {
        List<Product> products = productRepository.findAllByCategoryId(id);
        if (products != null) {
            List<ProductResponse> productResponses = new ArrayList<>();
            products.forEach(product -> {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(product.getId());
                productResponse.setName(product.getName());
                productResponse.setPrice(product.getPrice());
                productResponse.setCategory(product.getCategory());
                productResponses.add(productResponse);
            });
            return ResponseEntity.ok(productResponses);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Optional<Product>> getProductById(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty())
            return ResponseEntity.status(404).build();
        return ResponseEntity.ok(product);
    }
}
