package com.incede.Controller.language;

import java.util.List;
import java.util.stream.Collectors;

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

import com.incede.Dto.address.AddressTypeDto;
import com.incede.Dto.language.LanguageDto;
import com.incede.Service.language.LanguageService;

@RestController
@RequestMapping("/v1/masterdata/language")
public class LanguageController {
	private final LanguageService languageService;

	public LanguageController(LanguageService languageService) {
		this.languageService = languageService;
	}
	
	// Create a new language
	@PostMapping
	public ResponseEntity<LanguageDto> createAddressType(@RequestBody LanguageDto languageDto) {
		LanguageDto created = languageService.createLanguage(languageDto);
		
		return ResponseEntity.status(201).body(created);
	}
	
	// Getting Specific Language
	@GetMapping("/{id}")
	public ResponseEntity<LanguageDto> getLanguageById(@PathVariable Integer id){
		System.out.println("Entered");
		LanguageDto languages = languageService.getLanguageById(id);
		return ResponseEntity.ok(languages);
		
	}
	
	//Getting all
	@GetMapping
	public ResponseEntity<List<LanguageDto>> getAllLanguages() {
		return ResponseEntity.ok(languageService.findAllLanguages());
	}
	
	//Getting all with tenant id
	@GetMapping("getAll/{id}")
	public ResponseEntity<List<LanguageDto>> getAllLanguagesWithTenantId(@PathVariable Integer id) {
		return ResponseEntity.ok(languageService.findAllLanguagesWithTenantId(id));
	}
	
	//Update Language
	@PutMapping
	public ResponseEntity<LanguageDto> updateLanguage(@RequestBody LanguageDto languageDto){
		return ResponseEntity.ok(languageService.updateLanguage(languageDto));
	}
	
	//delete Language
	@DeleteMapping("/{languageId}")
	public ResponseEntity<?> deleteLanguage(@PathVariable Integer languageId){
		return ResponseEntity.ok(languageService.deleteLanguage(languageId));
	}

}
