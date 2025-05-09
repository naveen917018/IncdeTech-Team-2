package com.incede.Controller.taxCategory;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.incede.Dto.taxCategory.TaxCategoryDto;
import com.incede.Service.taxCategory.TaxCategoryService;

//import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/masterdata/taxcategory") 
public class TaxCategoryController {
	
	private final TaxCategoryService service;
	
	public TaxCategoryController(TaxCategoryService service) {
		// TODO Auto-generated constructor stub
		this.service=service;
	}
	
    @PostMapping
    public ResponseEntity<TaxCategoryDto> create(@RequestBody TaxCategoryDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("tenant/{tenantId}")
    public List<TaxCategoryDto> getAll(@PathVariable("tenantId") Integer tenantId) {
//    	System.out.println("Entered");
        return service.getAll(tenantId);
    }
    
    @PutMapping
    public ResponseEntity<TaxCategoryDto> update(@RequestBody TaxCategoryDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping
    public TaxCategoryDto getById(@RequestParam Integer tenantId,@RequestParam Integer taxId) {
        return service.getById(tenantId, taxId);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Integer tenantId, @RequestParam Integer taxId) {
    	service.delete(tenantId, taxId);
        return ResponseEntity.noContent().build();
    }

}
