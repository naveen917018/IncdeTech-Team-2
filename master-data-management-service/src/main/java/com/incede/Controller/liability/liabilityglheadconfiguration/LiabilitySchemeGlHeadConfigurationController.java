package com.incede.Controller.liability.liabilityglheadconfiguration;

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

import com.incede.Dto.liability.liabilityglheadconfiguration.LiabilitySchemeGlHeadConfigurationDto;
import com.incede.Service.liability.liabilityglheadconfiguration.LiabilitySchemeGlHeadConfigurationService;

@RestController
@RequestMapping("/v1/masterdata/liability/glhead-configuration")
public class LiabilitySchemeGlHeadConfigurationController {
	
	private final LiabilitySchemeGlHeadConfigurationService service;
	
	public LiabilitySchemeGlHeadConfigurationController(LiabilitySchemeGlHeadConfigurationService service) {
		this.service = service;
	}
	
//get all
	@GetMapping
	public ResponseEntity<List<LiabilitySchemeGlHeadConfigurationDto>> getAll(){
		return ResponseEntity.ok(service.getAll());
	}

//get by id
	@GetMapping("/{id}")
	public ResponseEntity<LiabilitySchemeGlHeadConfigurationDto> getById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
//create
	@PostMapping
	public ResponseEntity<LiabilitySchemeGlHeadConfigurationDto> create(@RequestBody LiabilitySchemeGlHeadConfigurationDto dto){
		LiabilitySchemeGlHeadConfigurationDto liabilityDto = service.create(dto);
		return ResponseEntity.status(201).body(liabilityDto);
	}
	
//update
	@PutMapping("/{id}")
	public ResponseEntity<LiabilitySchemeGlHeadConfigurationDto> update(
			@PathVariable Integer id,
			@RequestBody LiabilitySchemeGlHeadConfigurationDto dto){
		return ResponseEntity.ok(service.update(id, dto));
	}
	
//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok("Configuration deleted successfully");
	}
	
	
}
