package com.incede.Controller.address;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.address.AddressTypeDto;
import com.incede.Model.address.AddressType;
import com.incede.Service.address.AddressTypeService;

@RestController
@RequestMapping("/v1/masterdata/address/address-types")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressTypeController {

private final AddressTypeService addressTypeService;
  
	public AddressTypeController(AddressTypeService addressTypeService) {
		this.addressTypeService = addressTypeService;
	}

	
	// Get all address types
	@GetMapping("/getAll-")
	public ResponseEntity<List<AddressTypeDto>> getAllAddressTypes() {
		List<AddressTypeDto> addressTypes = addressTypeService.getAllAddressTypes();
		return ResponseEntity.ok(addressTypes);
	}

	// Get address type by ID
	@GetMapping("/{addressTypeId}")
	public ResponseEntity<AddressType> getAddressTypeById(@PathVariable Integer addressTypeId) {
		return ResponseEntity.ok(addressTypeService.getAddressTypeById(addressTypeId));
	}

	// Create a new address type
	@PostMapping("/")
	public ResponseEntity<AddressType> createAddressType(@RequestBody AddressTypeDto addressTypeDto) {
		AddressType created = addressTypeService.createAddressType(addressTypeDto);
		return ResponseEntity.status(201).body(created);
	}

	// Update an existing address type
	@PutMapping("/{addressTypeId}")
	public ResponseEntity<AddressType> updateAddressType(
			@PathVariable Integer addressTypeId,
			@RequestBody AddressTypeDto addressTypeDto) {
		AddressType updated = addressTypeService.updateAddressType(addressTypeId, addressTypeDto);
		return ResponseEntity.ok(updated);
	}

	// Delete address type by ID
	@DeleteMapping("/{addressTypeId}")
	public ResponseEntity<String> deleteAddressType(@PathVariable Integer addressTypeId) {
	    addressTypeService.deleteAddressType(addressTypeId);
	    return ResponseEntity.ok("Address type deleted successfully");
	}

}
