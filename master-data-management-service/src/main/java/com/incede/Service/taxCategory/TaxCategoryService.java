package com.incede.Service.taxCategory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.taxCategory.TaxCategoryDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.taxCategory.TaxCategory;
import com.incede.Repository.taxCategory.TaxCategoryRepository;

@Service
public class TaxCategoryService {
	
	private final TaxCategoryRepository taxCategoryRepository;
	
	public TaxCategoryService(TaxCategoryRepository taxCategoryRepository) {
		// TODO Auto-generated constructor stub
		this.taxCategoryRepository = taxCategoryRepository;
	}
	
	private TaxCategory toEntity(TaxCategoryDto taxDto) {
		TaxCategory tax = new TaxCategory();
		tax.setTaxCategoryId(taxDto.getTaxCategoryId());
		tax.setTenantId(taxDto.getTenantId());
		tax.setName(taxDto.getName());
		tax.setActive(taxDto.getActive());
		tax.setCreatedBy(taxDto.getCreatedBy());
		tax.setUpdatedBy(taxDto.getUpdatedBy());
		return tax;
	}
	
	private TaxCategoryDto toDto(TaxCategory tax) {
		TaxCategoryDto taxDto = new TaxCategoryDto();
		taxDto.setTaxCategoryId(tax.getTaxCategoryId());
		taxDto.setTenantId(tax.getTenantId());
		taxDto.setName(tax.getName());
		taxDto.setActive(tax.getActive());
		taxDto.setCreatedBy(tax.getCreatedBy());
		taxDto.setUpdatedBy(tax.getUpdatedBy());
		taxDto.setIsDeleted(tax.getIsDeleted());
		return taxDto;
	}
	
	@Transactional(readOnly = true)
	public TaxCategoryDto getById(Integer tenantId, Integer id) {
		if(tenantId == null || !(tenantId instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a valid format");
		}
		if(id == null || !(id instanceof Integer)) {
			throw new BusinessException("Tax Category Id Must be a valid format");
		}
        TaxCategory taxCategory = taxCategoryRepository.findByTenantIdAndTaxCategoryIdAndIsDeletedFalse(tenantId, id)
                .orElseThrow(() -> new BusinessException("Tax category not found"));
        ////////////////////////////////////////////////////ij8iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
        ////////////////qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
        return toDto(taxCategory); 
        ////111111111111111111111
        ///222222222222222222222222
        ///333333333333333333333333
        
    }
	
	@Transactional(readOnly = true)
	public List<TaxCategoryDto> getAll(Integer tenantId) {
		if(tenantId == null || !(tenantId instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a valid format");
		}

	    List<TaxCategory> entities = taxCategoryRepository
	        .findAllByTenantIdAndIsDeletedFalse(tenantId);

	    if (entities.isEmpty()) {
	        throw new BusinessException("No tax categories found for tenantId: " + tenantId);
	    }

	    return entities.stream()
	                   .map(this::toDto)
	                   .collect(Collectors.toList());
	}
	
	@Transactional
	public TaxCategoryDto create(TaxCategoryDto dto) {
		if(dto.getCreatedBy() == null || !(dto.getCreatedBy() instanceof Integer)) {
			throw new BusinessException("Created By not be null");
		}
//        if (taxCategoryRepository.existsByTenantIdAndNameIgnoreCaseAndIsDeletedFalse(dto.getTenantId(), dto.getName())) {
//            throw new BusinessException("Tax category already exists for this tenant.");
//        }
		Optional<TaxCategory> taxCategoryOpt = taxCategoryRepository.findByTenantIdAndNameIgnoreCase(dto.getTenantId(), dto.getName());
		TaxCategory taxCategory = toEntity(dto);
		if(taxCategoryOpt.isPresent()) {
			if(taxCategoryOpt.get().getIsDeleted() == true) {
				taxCategory.setTaxCategoryId(taxCategoryOpt.get().getTaxCategoryId());
				taxCategory.setUpdatedBy(dto.getCreatedBy());
				taxCategory.setCreatedBy(taxCategoryOpt.get().getCreatedBy());
			}
			else {
				throw new BusinessException("Tax Category already exists for this tenant: " + dto.getName());
			}
		}
		else {
			taxCategory.setTaxCategoryId(null);
			taxCategory.setUpdatedBy(null);
		}
        taxCategory.setActive(true);
        taxCategory.setIsDeleted(false);
        taxCategory = taxCategoryRepository.save(taxCategory);
        return toDto(taxCategory);
    }
	
	@Transactional
	public TaxCategoryDto update(TaxCategoryDto dto) {
//		System.out.println(1);
		if(dto.getTenantId() == null || !(dto.getTenantId() instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a valid format");
		}
//		System.out.println(2);
		if(dto.getUpdatedBy() == null || !(dto.getUpdatedBy() instanceof Integer)) {
			throw new BusinessException("Updated By not be null");
		}
//		System.out.println(3);
		TaxCategory taxCategory = taxCategoryRepository.findByTenantIdAndTaxCategoryIdAndIsDeletedFalse(dto.getTenantId(), dto.getTaxCategoryId())
                .orElseThrow(() -> new BusinessException("Tax category not found"));
//		System.out.println(4);
		if (taxCategoryRepository.existsByTenantIdAndNameIgnoreCaseAndIsDeletedFalse(taxCategory.getTenantId(), dto.getName())) {
            throw new BusinessException("Tax category already exists for this tenant. Dulpication is not allowed While updating");
        }
//		System.out.println(5);

        taxCategory.setName(dto.getName());
        taxCategory.setUpdatedBy(dto.getUpdatedBy());
        taxCategory.setActive(dto.getActive() != null ? dto.getActive() : taxCategory.getActive());
//        System.out.println(6);
        return toDto(taxCategoryRepository.save(taxCategory));
    }
	
	@Transactional
	public void delete(Integer tenantid,Integer taxCategoryId) {
		
		if(tenantid == null || !(tenantid instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a valid format");
		}
		
		if(taxCategoryId == null || !(taxCategoryId instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a valid format");
		}
		
		TaxCategory taxCategory = taxCategoryRepository.findByTenantIdAndTaxCategoryIdAndIsDeletedFalse(tenantid, taxCategoryId)
                .orElseThrow(() -> new BusinessException("Tax category not found"));
		

        taxCategory.setIsDeleted(true);

        taxCategoryRepository.save(taxCategory);
    }


}
