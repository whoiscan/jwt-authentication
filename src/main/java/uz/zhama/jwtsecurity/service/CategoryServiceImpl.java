package uz.zhama.jwtsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.zhama.jwtsecurity.entity.Category;
import uz.zhama.jwtsecurity.models.CategoryReq;
import uz.zhama.jwtsecurity.models.Result;
import uz.zhama.jwtsecurity.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories == null)
            return ResponseEntity.status(500).build();
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<Result> saveCategory(CategoryReq categoryReq) {
        Category category = new Category();
        category.setName(categoryReq.getName());
        categoryRepository.save(category);
        return ResponseEntity.ok(new Result(true, categoryReq.getName() + " successfully saved"));
    }

    @Override
    public ResponseEntity<Result> editCategory(Integer id, CategoryReq categoryReq) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setName(categoryReq.getName());
            categoryRepository.save(category);
            return ResponseEntity.ok(new Result(true, categoryReq.getName() + "successfully saved"));
        }
        return ResponseEntity.status(400).body(new Result(false, categoryReq.getName() + "not saved.Id not sended in response"));
    }

    @Override
    public ResponseEntity<Result> deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(new Result(true, "Successfully deleted"));
    }
}
