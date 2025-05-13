package com.incede.Service.caratmaster;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.caratmaster.CaratMasterDTO;
import com.incede.Exception.BusinessException;
import com.incede.Model.caratmaster.CaratMaster;
import com.incede.Repository.caratmaster.CaratMasterRepository;

@Service
public class CaratMasterServices {
	
	private CaratMasterRepository caratmasterRepository;
	
	public CaratMasterServices(CaratMasterRepository caratMasterRepository) {
		this.caratmasterRepository = caratMasterRepository;
	}
	
	public CaratMaster toEntity(CaratMasterDTO caratMasterDTO) {
		CaratMaster entity = new CaratMaster();
//		entity.setCaratId(caratMasterDTO.getCaratId());
		entity.setCaratValue(caratMasterDTO.getCaratValue());
		entity.setPurityPercentage(caratMasterDTO.getPurityPercentage());
		entity.setTenantId(caratMasterDTO.getTenantId());
		entity.setUUID(caratMasterDTO.getUUID());
		
		entity.setIsActive(caratMasterDTO.getIsActive() != null ? caratMasterDTO.getIsActive() : true);
		entity.setIsDeleted(caratMasterDTO.getIsDeleted() != null ? caratMasterDTO.getIsDeleted() : false);
		
		if (caratMasterDTO.getCreatedBy() != null) {
			entity.setCreatedBy(caratMasterDTO.getCreatedBy());
			entity.setUpdatedBy(caratMasterDTO.getUpdatedBy());
		}
		return entity;
	}
	
	public CaratMasterDTO toDTO(CaratMaster entity) {
		CaratMasterDTO dto = new CaratMasterDTO();
		dto.setCaratId(entity.getCaratId());
		dto.setCaratValue(entity.getCaratValue());
		dto.setPurityPercentage(entity.getPurityPercentage());
		dto.setTenantId(entity.getTenantId());
		dto.setUUID(entity.getUUID());
		
		dto.setIsActive(entity.getIsActive() != null ? entity.getIsActive() : true);
		dto.setIsDeleted(entity.getIsDeleted() != null ? entity.getIsDeleted() : false);
		
		if (entity.getCreatedBy() != null) {
			dto.setCreatedBy(entity.getCreatedBy());
			dto.setUpdatedBy(entity.getUpdatedBy());
		}
		return dto;
	}

	@Transactional
	public CaratMasterDTO createCaratMaster(CaratMasterDTO caratMasterDTO) {
		 if(caratMasterDTO.getCreatedBy() == null) {
			 throw new BusinessException("Carat Master with created By is null");
		 }
		 boolean entity =  caratmasterRepository.existsByTenantIdAndCaratValue(caratMasterDTO.getTenantId(),caratMasterDTO.getCaratValue());
		 System.out.println(entity);
		 if(entity) {
			 throw new BusinessException("Dupliacte carat value for this tenant is not allowed");
		 }
		CaratMaster caratmaster = toEntity(caratMasterDTO);
		caratmaster.setCaratId(null);
		caratmasterRepository.save(caratmaster);
		return toDTO(caratmaster);
	}

	@Transactional(readOnly =  true)
	public List<CaratMasterDTO> getAllCaratMasters() {
		return caratmasterRepository.findAll().stream()
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()) &&
						Boolean.TRUE.equals(o.getIsActive()))
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CaratMasterDTO getCaratMaster(Integer caratId) {
		 CaratMaster entity =  caratmasterRepository.findById(caratId)
				 .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()) &&
						 Boolean.TRUE.equals(o.getIsActive()))
				 .orElseThrow(() ->new BusinessException("carat not found with id: "+caratId));
		 return toDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public List<CaratMasterDTO> getAllCaratMastersInTenant(Integer tenantId) {
		return caratmasterRepository.findByTenantId(tenantId).stream()
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public CaratMasterDTO updateCaratMaster(Integer caratId, CaratMasterDTO caratMasterDTO) {
		CaratMaster entity = caratmasterRepository.findById(caratId)
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()) &&
						Boolean.TRUE.equals(o.getIsActive()))
				.orElseThrow(() -> new BusinessException("Cannot update. Carat not found with id: "+caratId));
		if(!entity.getCaratValue().equalsIgnoreCase(caratMasterDTO.getCaratValue())) {
			boolean exists = caratmasterRepository.existsByTenantIdAndCaratValueIgnoreCaseAndIsDeletedFalse(
					caratMasterDTO.getTenantId(),caratMasterDTO.getCaratValue());
			if(exists) {
				throw new BusinessException("Duplicate carat value for this tenant is not allowed");
			}
		}
		entity.setTenantId(caratMasterDTO.getTenantId());
		entity.setCaratValue(caratMasterDTO.getCaratValue());
		entity.setPurityPercentage(caratMasterDTO.getPurityPercentage());
		entity.setIsActive(caratMasterDTO.getIsActive());
		entity.setUpdatedBy(caratMasterDTO.getUpdatedBy());
		
		CaratMaster caratMaster =  caratmasterRepository.save(entity);
		return toDTO(caratMaster);
	}



	@Transactional
	public void deleteCaratMaster(Integer caratId) {
		CaratMaster caratMaster = caratmasterRepository.findById(caratId)
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				.orElseThrow(() -> new BusinessException("Cannot delete. Carat not found with id: "+caratId));
		caratMaster.setIsDeleted(true);
		caratmasterRepository.save(caratMaster);
	}

}
