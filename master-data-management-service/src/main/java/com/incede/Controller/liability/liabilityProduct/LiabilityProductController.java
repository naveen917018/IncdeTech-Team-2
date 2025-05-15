package com.incede.Controller.liability.liabilityProduct;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.liability.liabilityProduct.LiabilityProductDto;
import com.incede.Model.liability.liabilityProductMaster.LiabilityProductMaster;
import com.incede.Service.liability.liabilityProduct.LiabilityProductService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/v1/masterdata/liability/product-master")
public class LiabilityProductController {

	

    private final LiabilityProductService liabilityproductservice;

    public LiabilityProductController(LiabilityProductService liabilityproductservice) {
        this.liabilityproductservice = liabilityproductservice;
    }

    
    // Create a new product
    @PostMapping
    public ResponseEntity<LiabilityProductDto> createLiabilityProduct(@Valid @RequestBody LiabilityProductDto dto) {
        return ResponseEntity.status(201).body(liabilityproductservice.createLiabilityProduct(dto));
    }

    

    // Update existing product
    @PutMapping("/{id}")
    public ResponseEntity<LiabilityProductDto> updateLiabilityProduct(@PathVariable Integer id, @RequestBody LiabilityProductDto dto) {
        return ResponseEntity.ok(liabilityproductservice.updateLiabilityProduct(id, dto));
    }


    
    
    // Get all active products
    @GetMapping("/getAll")
    public ResponseEntity<List<LiabilityProductDto>> getAllLiabilityProducts() {
        return ResponseEntity.ok(liabilityproductservice.getAllLiabilityProducts());
    }

    //Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<LiabilityProductMaster> getLiabilityProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(liabilityproductservice.getLiabilityProductById(id));
    }

    
    //get all by tenant id
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<LiabilityProductDto>> getProductsByTenantId(@PathVariable Integer tenantId) {
        List<LiabilityProductDto> products = liabilityproductservice.getProductsByTenantId(tenantId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    
    //Get all by product code
    @GetMapping("/productCode/{productCode}")
    public ResponseEntity<List<LiabilityProductDto>> getProductsByProductCode(@PathVariable String productCode) {
        List<LiabilityProductDto> products = liabilityproductservice.getProductsByProductCode(productCode);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    
    // Soft delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
    	liabilityproductservice.deleteProduct(id);
        return ResponseEntity.ok("Liability product soft deleted successfully");
    }
    
    
	
}
