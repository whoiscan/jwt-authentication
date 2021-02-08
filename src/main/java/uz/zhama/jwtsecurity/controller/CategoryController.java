package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.entity.Category;
import uz.zhama.jwtsecurity.models.CategoryReq;
import uz.zhama.jwtsecurity.models.ProductResponse;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.repository.CategoryRepository;
import uz.zhama.jwtsecurity.service.ProductService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    //Get all products by category id
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getAll(@PathVariable Integer id) {
        return productService.getProductsByCategoryId(id);
    }

    //Get all categories
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories != null) {
            return ResponseEntity.ok(categories);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/save")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Result> saveCategory(@RequestBody CategoryReq categoryReq) {
        Category category = new Category();
        category.setName(categoryReq.getName());
        Category savedCategory = categoryRepository.save(category);
        if (savedCategory != null) {
            Result result = new Result();
            result.setSuccess(true);
            result.setMessage(categoryReq.getName() + " successfully saved");
            return ResponseEntity.ok(result);
        } else {
            Result result = new Result();
            result.setSuccess(false);
            result.setMessage(categoryReq.getName() + " not saved.Server Error");
            return ResponseEntity.status(500).body(result);
        }
    }

    @PutMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Result> editCategory(@PathVariable Integer id, @RequestBody CategoryReq categoryReq) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setName(categoryReq.getName());

            Category savedCategory = categoryRepository.save(category);
            if (savedCategory != null) {
                Result result = new Result();
                result.setSuccess(true);
                result.setMessage(categoryReq.getName() + "successfully saved");
                return ResponseEntity.ok(result);
            } else {
                Result result = new Result();
                result.setSuccess(false);
                result.setMessage(categoryReq.getName() + "not saved.Server Error");
                return ResponseEntity.status(500).body(result);
            }

        } else {
            Result result = new Result();
            result.setSuccess(false);
            result.setMessage(categoryReq.getName() + "not saved.Id not sended in response");
            return ResponseEntity.status(400).body(result);
        }
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Result> deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        Category category = categoryRepository.getOne(id);
        if (category == null) {
            Result result = new Result();
            result.setSuccess(true);
            result.setMessage("successfully deleted");
            return ResponseEntity.ok(result);

        } else {
            Result result = new Result();
            result.setSuccess(false);
            result.setMessage(category.getName() + "not deleted.Server Error");
            return ResponseEntity.status(500).body(result);
        }
    }

//    @GetMapping(value = {"/auth/cabinet"})
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String getAdmin(@RequestHeader("Authorization") String authHeader) {
//
//        return "Admin page";
//    }
//
//    @GetMapping(value = {"/auth/user"})
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
//    public String getUser() {
//        return "User page";
//    }

}
