package uz.zhama.jwtsecurity.service;

import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.entity.Category;
import uz.zhama.jwtsecurity.models.CategoryReq;
import uz.zhama.jwtsecurity.models.Result;

import java.util.List;


public interface CategoryService {
    ResponseEntity<List<Category>> getAll();

    ResponseEntity<Result> saveCategory(CategoryReq categoryReq);

    ResponseEntity<Result> editCategory(Integer id, CategoryReq categoryReq);

    ResponseEntity<Result> deleteCategory(Integer id);
}
