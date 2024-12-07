package com.soa.manageLaptop.controller;

import com.soa.manageLaptop.dto.request.CategoryRequest;
import com.soa.manageLaptop.model.Category;
import com.soa.manageLaptop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest categoryRequest) {
        // Gọi service để tạo danh mục mới
        Category createdCategory = categoryService.saveCategory(categoryRequest.getName(), categoryRequest.getIds());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        // Gọi service để cập nhật danh mục
        Category updatedCategory = categoryService.updateCategory(id, categoryRequest.getName(), categoryRequest.getIds());
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu danh mục không tồn tại
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategory(id);

        if (isDeleted) {
            return ResponseEntity.ok().build();  // Trả về HTTP 200 OK nếu xóa thành công
        } else {
            return ResponseEntity.status(404).body("Danh mục không tồn tại");  // Trả về HTTP 404 nếu không tìm thấy danh mục
        }
    }
}
