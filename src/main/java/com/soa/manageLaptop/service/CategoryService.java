package com.soa.manageLaptop.service;

import com.soa.manageLaptop.model.Category;
import com.soa.manageLaptop.model.Product;
import com.soa.manageLaptop.repository.CategoryRepository;
import com.soa.manageLaptop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả các danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Tạo danh mục mới hoặc cập nhật danh mục nếu danh mục đã tồn tại
    public Category saveCategory(String name, long[] ids) {
        // Tìm danh sách các sản phẩm bằng cách sử dụng findByIdIn
        List<Product> products = productRepository.findByIdIn(Arrays.stream(ids).boxed().collect(Collectors.toList()));

        // Tạo hoặc lấy danh mục theo tên
        Category category = categoryRepository.findByName(name);

        if (category == null) {
            // Nếu không tìm thấy danh mục, tạo danh mục mới
            category = new Category();
            category.setName(name);
        }

        // Gán danh sách sản phẩm vào danh mục
        category.setProducts(products);

        // Lưu danh mục (với cascade, sản phẩm cũng sẽ được lưu)
        category = categoryRepository.save(category);

        // Sau khi lưu danh mục, bạn phải cập nhật các sản phẩm để chúng tham chiếu đến danh mục này
        for (Product product : products) {
            product.setCategory(category);
            productRepository.save(product);
        }

        return category;
    }


    public boolean deleteCategory(Long categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();

            // Cập nhật tất cả sản phẩm gắn với danh mục này để gỡ bỏ liên kết (nếu muốn giữ sản phẩm trong CSDL)
            List<Product> products = category.getProducts();
            for (Product product : products) {
                product.setCategory(null);  // Gỡ bỏ liên kết category
                productRepository.save(product);  // Cập nhật lại sản phẩm
            }

            // Xóa danh mục
            categoryRepository.delete(category);
            return true;
        } else {
            return false;  // Không tìm thấy danh mục
        }
    }

    // Cập nhật danh mục
    public Category updateCategory(Long categoryId, String name, long[] ids) {
        // Tìm danh mục theo ID
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            return null;  // Nếu không tìm thấy danh mục, trả về null
        }

        Category category = optionalCategory.get();
        category.setName(name);

        // Tìm danh sách sản phẩm cần gắn vào danh mục
        List<Product> products = productRepository.findByIdIn(Arrays.stream(ids).boxed().collect(Collectors.toList()));

        // Thêm các sản phẩm vào danh mục (không thay thế sản phẩm cũ)
        List<Product> newProducts = category.getProducts();
        newProducts.addAll(products);  // Thêm sản phẩm mới vào danh mục

        category.setProducts(newProducts);

        // Cập nhật lại thông tin sản phẩm trong cơ sở dữ liệu
        for (Product product : newProducts) {
            product.setCategory(category);
            productRepository.save(product);  // Lưu lại sản phẩm
        }

        return categoryRepository.save(category);  // Lưu lại danh mục đã cập nhật
    }

    // Lấy danh mục theo ID
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
