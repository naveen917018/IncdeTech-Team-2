package com.incede.Service.loan.loanproductmaster;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.caratmaster.CaratMasterDTO;
import com.incede.Dto.loan.loanproductmaster.LoanProductMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.loan.loanproductmaster.LoanProductMaster;
import com.incede.Repository.loan.loanproductmaster.LoanProductMasterRepository;

@Service
public class LoanProductMasterService {

	private final LoanProductMasterRepository loanProductMasterRepository;
	
	public LoanProductMasterService (LoanProductMasterRepository loanProductMasterRepository) {
		this.loanProductMasterRepository = loanProductMasterRepository;
	}
	
	public LoanProductMaster toEntity(LoanProductMasterDto dto) {
		LoanProductMaster entity = new LoanProductMaster();
		entity.setProductId(dto.getProductId());
	    entity.setProductId(dto.getProductId());
	    entity.setTenantId(dto.getTenantId());
	    entity.setProductName(dto.getProductName());
	    entity.setProductCode(dto.getProductCode());
	    entity.setLoanCategoryId(dto.getLoanCategoryId());
	    entity.setUUID(dto.getUUID());
	    
	    entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
	    entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : true);
		
		if (dto.getCreatedBy() != null) {
			entity.setCreatedBy(dto.getCreatedBy());
			entity.setUpdatedBy(dto.getUpdatedBy());
		}

	    return entity;
	}
	
	public LoanProductMasterDto toDto(LoanProductMaster entity) {
		LoanProductMasterDto dto = new LoanProductMasterDto();
		dto.setProductId(entity.getProductId());
		dto.setProductId(entity.getProductId());
		dto.setTenantId(entity.getTenantId());
		dto.setProductName(entity.getProductName());
		dto.setProductCode(entity.getProductCode());
		dto.setLoanCategoryId(entity.getLoanCategoryId());
		dto.setUUID(entity.getUUID());
	    
		dto.setIsActive(entity.getIsActive() != null ? entity.getIsActive() : true);
		dto.setIsDeleted(entity.getIsDeleted() != null ? entity.getIsDeleted() : true);
		
		if (entity.getCreatedBy() != null) {
			dto.setCreatedBy(dto.getCreatedBy());
			dto.setUpdatedBy(dto.getUpdatedBy());
		}

	    return dto;
	}
	
	@Transactional
	public LoanProductMasterDto createLoanProductMaster(LoanProductMasterDto loanProductMasterDTO) {
		boolean exists = loanProductMasterRepository.existsByTenantIdAndProductNameIgnoreCase(loanProductMasterDTO.getTenantId(),loanProductMasterDTO.getProductName());
		boolean exists_productCode = loanProductMasterRepository.existsByTenantIdAndProductCode(loanProductMasterDTO.getTenantId(),loanProductMasterDTO.getProductCode());
		if(exists && exists_productCode) {
			throw new BusinessException("Duplicate Loan Name or code for this tenant is not allowed");
		}
		LoanProductMaster entity = toEntity(loanProductMasterDTO);
		entity.setProductId(null);
		loanProductMasterRepository.save(entity);
		return toDto(entity);
	}

	@Transactional(readOnly = true)
	public List<LoanProductMasterDto> getAllLoanProductMasters() {
		return loanProductMasterRepository.findAll().stream()
				.filter(o-> !Boolean.TRUE.equals(o.getIsDeleted()) && 
						Boolean.TRUE.equals(o.getIsActive()))
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public LoanProductMasterDto getByLoanProductMasterId(Integer productId) {
		LoanProductMaster entity = loanProductMasterRepository.findById(productId)
				.filter(e -> !Boolean.TRUE.equals(e.getIsDeleted()) && Boolean.TRUE.equals(e.getIsActive()))
				.orElseThrow(() -> new BusinessException("Loan product master not found with id :"+productId));
		return toDto(entity);
	}

	@Transactional(readOnly = true)
	public List<LoanProductMasterDto> getByTenantId(Integer tenantId) {
		return loanProductMasterRepository.findByTenantId(tenantId).stream()
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()) && 
						Boolean.TRUE.equals(o.getIsActive()))
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<LoanProductMasterDto> getByLoanCategoryId(Integer loanCategoryId) {
		return loanProductMasterRepository.findByLoanCategoryId(loanCategoryId).stream()
				.filter(o-> !Boolean.TRUE.equals(o.getIsDeleted()) && 
						Boolean.TRUE.equals(o.getIsActive()))
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Transactional
	public LoanProductMasterDto updateLoanProductMaster(Integer productId, LoanProductMasterDto loanProductMasterDto) {
		LoanProductMaster entity = loanProductMasterRepository.findById(productId)
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()) && 
						Boolean.TRUE.equals(o.getIsActive()))
				.orElseThrow(()-> new BusinessException("Cannot update.Loan Product not found with id: "+productId));
		if(!entity.getProductCode().equalsIgnoreCase(loanProductMasterDto.getProductCode()) && !entity.getProductName().equalsIgnoreCase(loanProductMasterDto.getProductName())) {
			throw new BusinessException("Duplicate Product name and code for this tenant is not allowed");
			 
		}
		
		entity.setTenantId(loanProductMasterDto.getTenantId());
		entity.setLoanCategoryId(loanProductMasterDto.getLoanCategoryId());
		entity.setProductCode(loanProductMasterDto.getProductCode());
		entity.setProductName(loanProductMasterDto.getProductName());
		entity.setIsActive(loanProductMasterDto.getIsActive());
		entity.setUpdatedBy(loanProductMasterDto.getUpdatedBy());
		loanProductMasterRepository.save(entity);
		return toDto(entity);
	}

	@Transactional
	public void deleteLoanProduct(Integer productId) {
		LoanProductMaster loanProductMaster = loanProductMasterRepository.findById(productId)
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				.orElseThrow(()-> new BusinessException("Cannot delete. Loan Product not found with id: "+productId));
		loanProductMaster.setIsDeleted(true);
		loanProductMasterRepository.save(loanProductMaster);
	}
	
	
	
}
