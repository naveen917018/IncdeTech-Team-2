package com.incede.Controller.customer.customerStatus;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.customer.customerStatus.CustomerStatusDto;
import com.incede.Model.customer.customerStatus.CustomerStatus;
import com.incede.Service.customer.customerStatus.CustomerStatusService;
import com.incede.payload.CommonResult;

@RestController
@RequestMapping("/v1/masterdata/customers/customer-statuses")
public class CustomerStatusController {

	private final CustomerStatusService customerStatusService;

	public CustomerStatusController(CustomerStatusService customerStatusService) {
		this.customerStatusService = customerStatusService;
	}

	@GetMapping("/getAll")
	public CommonResult<List<CustomerStatusDto>> getAllCustomerStatuses() {
	    List<CustomerStatusDto> statuses = customerStatusService.getAllCustomerStatuses();
	    return CommonResult.success(statuses, "Fetched all customer statuses successfully");
	}


	// Get a customer status by ID
	@GetMapping("/{statusId}")
	public ResponseEntity<CustomerStatus> getCustomerStatusById(@PathVariable Integer statusId) {
		return ResponseEntity.ok(customerStatusService.getCustomerStatus(statusId));
	}

	// Create a new customer status
	@PostMapping("/")
	public ResponseEntity<CustomerStatus> createCustomerStatus(@RequestBody CustomerStatusDto dto) {
		return ResponseEntity.status(201).body(customerStatusService.createCustomerStatus(dto));
	}

	// Update an existing customer status
	@PutMapping("/{statusId}")
	public ResponseEntity<CustomerStatus> updateCustomerStatus(@PathVariable Integer statusId,
			@RequestBody CustomerStatusDto dto) {
		return ResponseEntity.ok(customerStatusService.updateCustomerStatus(statusId, dto));
	}

	// Soft delete a customer status
	@DeleteMapping("/{statusId}")
	public ResponseEntity<String> deleteCustomerStatus(@PathVariable Integer statusId) {
		customerStatusService.deleteCustomerStatus(statusId);
		return ResponseEntity.ok("Customer status deleted");
	}
}
