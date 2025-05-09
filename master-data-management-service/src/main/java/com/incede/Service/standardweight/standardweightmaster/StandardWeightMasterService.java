package com.incede.Service.standardweight.standardweightmaster;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.standardweight.standardweightmaster.StandardWeightMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.standardweight.standardweightmaster.StandardWeightMaster;
import com.incede.Repository.standardweight.standardweightmaster.StandardWeightMasterRepository;


@Service
public class StandardWeightMasterService {
	
	private final StandardWeightMasterRepository repository;
	
	 public StandardWeightMasterService(StandardWeightMasterRepository repository) {
	        this.repository = repository;
	    }
	 
	 private StandardWeightMasterDto toDto(StandardWeightMaster entity) {
		 StandardWeightMasterDto dto = new StandardWeightMasterDto();
		 dto.setTenantId(entity.getTenantId());
		 dto.setOrnamentId(entity.getOrnamentId());
		 dto.setWeightId(entity.getWeightId());
		 dto.setStandardWeight(entity.getStandardWeight());
		 dto.setIsActive(entity.getIsActive());
		 dto.setIsDeleted(entity.getIsDeleted());
		 dto.setCreatedBy(entity.getCreatedBy());
		 dto.setUpdatedBy(entity.getUpdatedBy());
		 dto.setUuid(entity.getUuid());
		 return dto; 
	 }
	 
	 private StandardWeightMaster toEntity(StandardWeightMasterDto dto) {
		 StandardWeightMaster entity= new StandardWeightMaster();
		 entity.setTenantId(dto.getTenantId());
		 entity.setOrnamentId(dto.getOrnamentId());
		 entity.setWeightId(dto.getWeightId());
		 entity.setStandardWeight(dto.getStandardWeight());
		 entity.setIsActive(dto.getIsActive());
		 entity.setIsDeleted(dto.getIsDeleted());
		 entity.setCreatedBy(dto.getCreatedBy());
		 entity.setUpdatedBy(dto.getUpdatedBy());
		 entity.setUuid(dto.getUuid());
		 return entity;
	 }
	 
	 public List<StandardWeightMasterDto> getAllStandardWeights() {
		 return repository.findAll().stream()
				 .filter(o -> 
				    !Boolean.TRUE.equals(o.getIsDeleted()) && 
				     Boolean.TRUE.equals(o.getIsActive())
				)
				 .map(this::toDto)
				 .collect(Collectors.toList());
	 }
	 
	 @Transactional(readOnly = true)
	 public List<StandardWeightMasterDto> getAllStandardWeights(Integer tenantId){
		 return repository.findByTenantId(tenantId).stream()
				 .filter(o -> 
				    !Boolean.TRUE.equals(o.getIsDeleted()) && 
				     Boolean.TRUE.equals(o.getIsActive())
				)
				 .map(this::toDto)
				 .collect(Collectors.toList());
	 }
	 
	 @Transactional(readOnly = true)
	 public StandardWeightMasterDto getStandardWeightById(Integer id) {
		 StandardWeightMaster standardWeight = repository.findById(id)
				 .filter(o -> 
				    !Boolean.TRUE.equals(o.getIsDeleted()) && 
				     Boolean.TRUE.equals(o.getIsActive())
				)
				 .orElseThrow(() -> new BusinessException("Standard weight not found with id: " + id));
		 return toDto(standardWeight);
	 }
	 
	 @Transactional
	 public StandardWeightMasterDto createStandardWeight(StandardWeightMasterDto dto) {
		 boolean exists = repository.existsByOrnamentIdAndStandardWeight(
				 dto.getOrnamentId(),dto.getStandardWeight());
		 
		 if(exists) {
			 throw new BusinessException("Duplicate standard weight for this ornament not allowed");
		 }
		 
		 StandardWeightMaster standardWeight = toEntity(dto);
		  repository.save(standardWeight);
		  
		  return toDto(standardWeight);
	 }
	 
	 @Transactional
	 public StandardWeightMasterDto updateStandardWeight(Integer id, StandardWeightMasterDto dto) {
		 StandardWeightMaster existing = repository.findById(id)
				 .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				 .orElseThrow(() -> new BusinessException("Cannot update. Standard weight not found with id: " + id));
		 
		 if(!existing.getStandardWeight().equals(dto.getStandardWeight())) {
			 boolean exists = repository.existsByOrnamentIdAndStandardWeightAndIsDeletedFalse(
					 dto.getOrnamentId(),dto.getStandardWeight());
			 if(exists) {
				 throw new BusinessException("Duplicate standard weight for this ornament not allowed.");
			 }
		 }
		 
		 existing.setTenantId(dto.getTenantId());
		 existing.setStandardWeight(dto.getStandardWeight());
		 existing.setIsActive(dto.getIsActive());
		 existing.setUpdatedBy(dto.getUpdatedBy());
		 existing.setOrnamentId(dto.getOrnamentId());
		 
		 repository.save(existing);
		 
		 return toDto(existing);
	 }
	 
	 @Transactional
	 public void deleteStandardWeight(Integer id) {
		 StandardWeightMaster standardWeight = repository.findById(id)
				 .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				 .orElseThrow(() -> new BusinessException("Cannot delete. Standard weight not found with id: "+ id));
		 standardWeight.setIsDeleted(true);
		 repository.save(standardWeight);
	 }


	 

}
