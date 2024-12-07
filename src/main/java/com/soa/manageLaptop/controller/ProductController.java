package com.soa.manageLaptop.controller;

import com.soa.manageLaptop.model.Product;
import com.soa.manageLaptop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products")
    public Page<Product> getProducts(
            @RequestParam(defaultValue = "") String search, // Tên sản phẩm (mặc định là rỗng)
            @RequestParam(defaultValue = "0") double minPrice, // Giá min (mặc định là 0)
            @RequestParam(defaultValue = "100000000") double maxPrice, // Giá max (mặc định là 10000)
            @RequestParam(defaultValue = "0") int page, // Trang hiện tại (mặc định là 0)
            @RequestParam(defaultValue = "10") int size // Kích thước trang (mặc định là 10)
    ) {

        return productService.getProducts(search, minPrice, maxPrice, page, size);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
