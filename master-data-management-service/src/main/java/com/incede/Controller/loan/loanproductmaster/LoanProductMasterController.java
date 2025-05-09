package com.incede.Controller.loan.loanproductmaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.caratmaster.CaratMasterDTO;
import com.incede.Dto.loan.loanproductmaster.LoanProductMasterDto;
import com.incede.Service.loan.loanpurposemaster.LoanPurposeMasterService;
import com.incede.Service.loanproductmaster.LoanProductMasterService;

@RestController
@RequestMapping("/v1/masterdata/loan/product")
public class LoanProductMasterController {

    private final LoanProductMasterService loanProductMasterServices;

    public  LoanProductMasterController(LoanProductMasterService loanProductMasterServices) {
        this.loanProductMasterServices = loanProductMasterServices;
    }
    
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> createLoanProductMaster(@RequestBody LoanProductMasterDto loanProductMasterDTO){
		LoanProductMasterDto dto = loanProductMasterServices.createLoanProductMaster(loanProductMasterDTO);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Loan Product Master created successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllLoanProductMaster(){
		List<LoanProductMasterDto> dto = loanProductMasterServices.getAllLoanProductMasters();
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Loan Product Master fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> getByLoanProductMasterId(@PathVariable Integer productId){
		List<LoanProductMasterDto> dto = loanProductMasterServices.getByLoanProductMasterId(productId);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Loan Product Master fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/{tenantId}")
	public ResponseEntity<Map<String, Object>> getByTenantId(@PathVariable Integer tenantId){
		List<LoanProductMasterDto> dto = loanProductMasterServices.getByTenantId(tenantId);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Loan Product Master fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/{loanCategoryId}")
	public ResponseEntity<Map<String, Object>> getByLoanCategoryId(@PathVariable Integer loanCategoryId){
		List<LoanProductMasterDto> dto = loanProductMasterServices.getByLoanCategoryId(loanCategoryId);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Loan Product Master fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	
	@PutMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> updateLoanProductMaster(@PathVariable Integer productId, @RequestAttribute LoanProductMasterDto loanProductMasterDto){
		LoanProductMasterDto dto = loanProductMasterServices.updateLoanProductMaster(productId,loanProductMasterDto);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Loan Product Master fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteLoanProduct(@PathVariable Integer productId){
		loanProductMasterServices.deleteLoanProduct(productId);
		return ResponseEntity.ok("Loan Product deleted successfully");
	}
	
	
	
}
