package com.incede.Service.language;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.language.LanguageDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.language.Language;
import com.incede.Repository.language.LanguageRepository;

@Service
public class LanguageService {
	
	private final LanguageRepository languageRepository;
	
	public LanguageService(LanguageRepository languageRepository) {
		// TODO Auto-generated constructor stub
		this.languageRepository = languageRepository;
	}
	
	private Language toEntity(LanguageDto languageDto) {
		Language language = new Language();
		language.setLanguageId(languageDto.getLanguageId());
		language.setTenantId(languageDto.getTenantId());
		language.setLanguageName(languageDto.getLanguageName());
		language.setIsActive(languageDto.getIsActive() != null ? languageDto.getIsActive() : true);
		language.setIsDeleted(languageDto.getIsDeleted() != null ? languageDto.getIsDeleted() : false);

		if (languageDto.getCreatedBy() != null) {
			language.setCreatedBy(languageDto.getCreatedBy());
		}
		if (languageDto.getUpdatedBy() != null) {
			language.setUpdatedBy(languageDto.getUpdatedBy());
		}
		
		return language;
	}
	
	LanguageDto toDto(Language language) {
		LanguageDto languageDto = new LanguageDto();
		languageDto.setLanguageId(language.getLanguageId());
		languageDto.setTenantId(language.getTenantId());
		languageDto.setLanguageName(language.getLanguageName());
		languageDto.setIsActive(language.getIsActive());
		languageDto.setIsDeleted(language.getIsDeleted());
		languageDto.setCreatedBy(language.getCreatedBy());
		languageDto.setUpdatedBy(language.getUpdatedBy());

		return languageDto;
	}

	@Transactional
	public LanguageDto createLanguage(LanguageDto languageDto) {
		// TODO Auto-generated method stub
		
		if(languageDto == null) {
			throw new BusinessException("LanguageDto is null.");
		}
		if(languageDto.getCreatedBy() == null) {
			throw new BusinessException("Created By  is null.");
		}
		if(languageDto.getTenantId() == null) {
			throw new BusinessException("Tenant Id cannot be null.");
		}
		  
		
		   // Case-insensitive duplicate check
	    languageRepository.findByTenantIdAndLanguageNameIgnoreCase(
	        languageDto.getTenantId(), 
	        languageDto.getLanguageName()
	    ).ifPresent(lang -> {
	        throw new BusinessException("Language already exists for this tenant.");
	    });

		Language language = toEntity(languageDto);
		language.setLanguageId(null);
		try {
	        language = languageRepository.save(language);
	    } catch (DataIntegrityViolationException e) {
	        // This exception is thrown when a unique constraint is violated
	        throw new BusinessException("Language already exists for this tenant.");
	    }
		
		return toDto(language);
	}

	@Transactional(readOnly = true)
	public LanguageDto getLanguageById(Integer id) {
		// TODO Auto-generated method stub
		if(id == null) {
			throw new BusinessException("Id to get Language is null");
		}
		if(!(id instanceof Integer)) {
			throw new BusinessException("Expected Integer value for 'age', but received a another type.");
		}
		
		Language language = languageRepository.findById(id).filter(lang ->(lang.getIsDeleted() == null || !lang.getIsDeleted()))
		.orElseThrow(() -> new BusinessException("Language is not founded for the Id " + id));
		return toDto(language);
	}

	@Transactional(readOnly = true)
	public List<LanguageDto> findAllLanguages() {
		// TODO Auto-generated method stub
		try {
			List<LanguageDto> languagesDto = languageRepository.findAll().stream().filter(lang -> (lang.getIsDeleted() == null || !lang.getIsDeleted()) && lang.getIsActive())
					.map(this::toDto).collect(Collectors.toList());
			return languagesDto;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException("Error occured with find all method");
		}
	}

	@Transactional(readOnly = true)
	public List<LanguageDto> findAllLanguagesWithTenantId(Integer tenantId) {
		// TODO Auto-generated method stub
		if(!languageRepository.existsByTenantId(tenantId)) {
			throw new BusinessException("Invalid Tenent Id "+tenantId);
		}
		try {
			List<LanguageDto> languagesDto = languageRepository.findAll().stream()
					.filter(lang -> (
							(lang.getTenantId() != null && lang.getTenantId().intValue() == tenantId.intValue())
							&& lang.getIsActive()
							&& lang.getIsDeleted() != null && !lang.getIsDeleted()))
					.map(this::toDto).collect(Collectors.toList());
			return languagesDto;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException("Error occured with find all method");
		}
	}

	@Transactional
	public LanguageDto updateLanguage(LanguageDto languageDto) {
		// TODO Auto-generated method stub
		System.out.println(languageDto);
		if(languageDto.getLanguageId() == null || !languageRepository.existsById(languageDto.getLanguageId())) {
			throw new BusinessException("Error occured with update language, language id is null");
		}
		if(languageDto.getUpdatedBy()== null) {
			throw new BusinessException("Error occured with update language, updated by is null");	
		}
		if(languageDto.getTenantId()== null || !languageRepository.existsByTenantId(languageDto.getTenantId())) {
			throw new BusinessException("Error occured with update language, tenant id is null");	
		}
		if(languageRepository.existsByLanguageName( languageDto.getLanguageName())) {
			throw new BusinessException("Error occured with update language and Language name "+
					languageDto.getLanguageName()+" is already existing");
		}
		
		
		
		if(languageRepository.existsByTenantIdAndLanguageName(languageDto.getTenantId(), languageDto.getLanguageName())) {
			throw new BusinessException("Error occured with update language, tenant id "+
					languageDto.getTenantId()+" and Language name "+
					languageDto.getLanguageName()+" is already existing");
		}
		
		
	    // Check case-insensitive uniqueness excluding the same record
	    languageRepository.findByTenantIdAndLanguageNameIgnoreCase(languageDto.getTenantId(), languageDto.getLanguageName())
	        .ifPresent(existing -> {
	            if (!existing.getLanguageId().equals(languageDto.getLanguageId())) {
	                throw new BusinessException("Language name already exists for this tenant.");
	            }
	        });
		
		
		
		Language language = languageRepository.findById(languageDto.getLanguageId()).orElseThrow(
			()->new BusinessException("Error in updating Language with language id "+languageDto.getLanguageId())
		);
		
		if(languageDto.getLanguageName()!= null && !languageDto.getLanguageName().isBlank()) {
			language.setLanguageName(languageDto.getLanguageName());
		}
		language.setUpdatedBy(languageDto.getUpdatedBy());
	
		language = languageRepository.save(language);
		
		return toDto(language);
	}

	@Transactional
	public Integer deleteLanguage(Integer languageId) {
		// TODO Auto-generated method stub
		if(languageId == null) {
			throw new BusinessException("Id to get Language is null");
		}
		if(!(languageId instanceof Integer)) {
			throw new BusinessException("Expected Integer value for 'age', but received a another type.");
		}
		
		Language language = languageRepository.findById(languageId).orElseThrow(
				()->new BusinessException("Error in Delete Language with language id "+languageId)
			);
		language.setIsDeleted(true);
		
		try {
			language = languageRepository.save(language);
			return language.getLanguageId();
		} catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException("Error in Delete Language with language id "+languageId);
		}
	}

}
