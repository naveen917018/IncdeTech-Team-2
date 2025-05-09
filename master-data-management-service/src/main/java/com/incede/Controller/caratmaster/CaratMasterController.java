package com.incede.Controller.caratmaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.caratmaster.CaratMasterDTO;
import com.incede.Service.caratmaster.CaratMasterServices;

@RestController
@RequestMapping("/v1/masterdata/caratmaster")
public class CaratMasterController {

	private final CaratMasterServices caratMasterServices;
	
	public CaratMasterController(CaratMasterServices caratMasterServices) {
		this.caratMasterServices = caratMasterServices;
	}
	
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> createCartMaster(@RequestBody CaratMasterDTO caratMasterDTO){
		System.out.println(caratMasterDTO);
		CaratMasterDTO dto = caratMasterServices.createCaratMaster(caratMasterDTO);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Carat Master created successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getAllCaratMasters(){
		List<CaratMasterDTO> dto = caratMasterServices.getAllCaratMasters();
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Carat Masters fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/{caratId}")
	public ResponseEntity<Map<String, Object>> getCaratMasterById(@PathVariable Integer caratId){
		CaratMasterDTO dto  = caratMasterServices.getCaratMaster(caratId);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Carat Masters fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@GetMapping("/getAll/{tenantId}")
	public ResponseEntity<Map<String, Object>> getAllCaratMastersInTenant(@PathVariable Integer tenantId){
		List<CaratMasterDTO> dto = caratMasterServices.getAllCaratMastersInTenant(tenantId);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Carat Masters fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@PutMapping("/{caratId}")
	public ResponseEntity<Map<String, Object>> updateCaratMaster(@PathVariable Integer caratId, @RequestBody CaratMasterDTO caratMasterDTO){
		CaratMasterDTO dto  = caratMasterServices.updateCaratMaster(caratId,caratMasterDTO);
		Map<String, Object> responce = new HashMap<>();
		responce.put("Status","Success");
		responce.put("data",dto );
		responce.put("Message","Carat Masters fetched successfully");
		return ResponseEntity.status(201).body(responce);
	}
	
	@DeleteMapping("/{caratId}")
	public ResponseEntity<String> deleteCaratMaster(@PathVariable Integer caratId){
		caratMasterServices.deleteCaratMaster(caratId);
		return ResponseEntity.ok("Carat Master deleted successfully");
	}

}
