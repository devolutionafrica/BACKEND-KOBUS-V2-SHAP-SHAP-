package com.nsia.cobus.api.controllers.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nsia.cobus.domain.ReadAllProduct;
import com.nsia.cobus.domain.ReadAllProductCategory;
import com.nsia.cobus.domain.ReadProductByCategory;
import com.nsia.cobus.domain.ReadProductBykeyword;
import com.nsia.cobus.domain.models.Product;
import com.nsia.cobus.domain.models.ProductCategory;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kobus/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ReadAllProduct readProduct;
    private final ReadAllProductCategory readAllProductCategory;
    private final ReadProductByCategory readProductByCategory;
    private final ReadProductBykeyword readProductBykeyword;

    @GetMapping("/products")
    private ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(readProduct.doReadAllProduct());
    }

    @GetMapping("/product-category")
    private ResponseEntity<List<ProductCategory>> getProductFamily() {
        return ResponseEntity.ok(readAllProductCategory.readAllProductCategory());
    }

    @GetMapping("/product/{category}/list-product")
    private ResponseEntity<List<Product>> getProductByFamily(@PathVariable String category) {
        return ResponseEntity.ok(readProductByCategory.readProductByCategory(category));
    }

    /*
     * Cette méthode prend en paramètre le mot clé à rechercher dans la liste des
     * paramètre
     */
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProductByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(readProductBykeyword.readProductByKeyword(keyword));
    }

}
