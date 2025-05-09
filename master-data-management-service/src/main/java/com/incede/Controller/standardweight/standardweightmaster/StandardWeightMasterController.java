package com.incede.Controller.standardweightmaster.standardweight;

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

import com.incede.Dto.standardweight.standardweightmaster.StandardWeightMasterDto;
import com.incede.Service.standardweight.standardweightmaster.StandardWeightMasterService;

@RestController
@RequestMapping("/v1/masterdata/standardweight/standardweightmaster")
public class StandardWeightMasterController {
	
	private final StandardWeightMasterService standardWeightMasterService;
	
	public StandardWeightMasterController(StandardWeightMasterService standardWeightMasterService) {
		this.standardWeightMasterService = standardWeightMasterService;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<StandardWeightMasterDto>> getAllStandardWeights(){
		List<StandardWeightMasterDto> standardWeights = standardWeightMasterService.getAllStandardWeights();
		return ResponseEntity.ok(standardWeights);
	}
	
	@GetMapping("/getAll/{tenantId}")
	public ResponseEntity<List<StandardWeightMasterDto>> getAllStandardWeights(@PathVariable Integer tenantId){
		List<StandardWeightMasterDto> standardWeights = standardWeightMasterService.getAllStandardWeights(tenantId);
		return ResponseEntity.ok(standardWeights);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StandardWeightMasterDto> getStandardWeightById(@PathVariable Integer id){
		return ResponseEntity.ok(standardWeightMasterService.getStandardWeightById(id));
	}
	
	@PostMapping("/")
	public  ResponseEntity<StandardWeightMasterDto> createStandardWeight(@RequestBody StandardWeightMasterDto dto){
		StandardWeightMasterDto standardWeightDto = standardWeightMasterService.createStandardWeight(dto);
		return ResponseEntity.status(201).body(standardWeightDto);
	}
	
	@PutMapping("/{id}")
	public  ResponseEntity<StandardWeightMasterDto> updateStandardWeight(
			@PathVariable Integer id, @RequestBody StandardWeightMasterDto  dto){
		StandardWeightMasterDto updatedStandardWeightDto = standardWeightMasterService.updateStandardWeight(id, dto);
		return ResponseEntity.ok(updatedStandardWeightDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStandardWeight(@PathVariable Integer id){
		standardWeightMasterService.deleteStandardWeight(id);
		return ResponseEntity.ok("Standard weight deleted successfully");
	}

}
