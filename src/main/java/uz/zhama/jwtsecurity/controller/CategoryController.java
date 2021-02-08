package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.entity.Category;
import uz.zhama.jwtsecurity.models.CategoryReq;
import uz.zhama.jwtsecurity.models.ProductResponse;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.repository.CategoryRepository;
import uz.zhama.jwtsecurity.service.CategoryService;
import uz.zhama.jwtsecurity.service.ProductService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, ProductService productService, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //Get all products by category id
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getAll(@PathVariable Integer id) {
        return productService.getProductsByCategoryId(id);
    }

    //Get all categories
    @GetMapping("/all")
    public ResponseEntity<List<Category>> allCategroies() {
        return categoryService.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Result> saveCategory(@RequestBody CategoryReq categoryReq) {
        return categoryService.saveCategory(categoryReq);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Result> editCategory(@PathVariable Integer id, @RequestBody CategoryReq categoryReq) {
        return categoryService.editCategory(id, categoryReq);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteCategory(@PathVariable Integer id) {
        return categoryService.deleteCategory(id);
    }

}
