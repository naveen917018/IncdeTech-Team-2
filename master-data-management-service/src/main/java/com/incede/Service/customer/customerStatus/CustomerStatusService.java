package com.incede.Service.customer.customerStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.customer.customerStatus.CustomerStatusDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.customer.customerStatus.CustomerStatus;
import com.incede.Repository.customer.customerStatus.CustomerStatusRepository;

@Service
public class CustomerStatusService {

	private final CustomerStatusRepository customerStatusRepository;

	public CustomerStatusService(CustomerStatusRepository customerStatusRepository) {
		this.customerStatusRepository = customerStatusRepository;
	}

	private CustomerStatusDto toDto(CustomerStatus cs) {
		CustomerStatusDto dto = new CustomerStatusDto();
		dto.setStatusId(cs.getStatusId());
		dto.setCreatedBy(cs.getCreatedBy());
		dto.setCreatedAt(cs.getCreatedAt());
		dto.setIsActive(cs.getIsActive());
		dto.setStatusName(cs.getStatusName());
		dto.setTenantId(cs.getTenantId());
		dto.setIsDeleted(cs.getIsDeleted());
		dto.setUpdatedBy(cs.getUpdatedBy());
		return dto;
	}

	private CustomerStatus toEntity(CustomerStatusDto dto) {
		CustomerStatus entity = new CustomerStatus();
		entity.setTenantId(dto.getTenantId());
		entity.setStatusName(dto.getStatusName());
		entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
		entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);

		// Assuming createdBy and updatedBy in BaseEntity are of type Integer
		if (dto.getCreatedBy() != null) {
			entity.setCreatedBy((Integer) (dto.getCreatedBy()));
		}
//		if (dto.getUpdatedBy() != null) {
//			entity.setUpdatedBy((Integer) (dto.getUpdatedBy()));
//		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<CustomerStatusDto> getAllCustomerStatuses() {
		return customerStatusRepository.findAll().stream().map(this::toDto) 
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CustomerStatus getCustomerStatus(Integer statusId) {
		return customerStatusRepository.findById(statusId)
				.orElseThrow(() -> new BusinessException("Customer status not found with id: " + statusId));
	}

	@Transactional
	public CustomerStatus createCustomerStatus(CustomerStatusDto dto) {
		boolean exists = customerStatusRepository.existsByTenantIdAndStatusName(dto.getTenantId(), dto.getStatusName());
		if (exists) {
			throw new BusinessException(
					"Status already exists for this tenant. Please choose a different status name.");
		}
        
		CustomerStatus customerStatus = toEntity(dto);
		return customerStatusRepository.save(customerStatus);
	}

	@Transactional
	public CustomerStatus updateCustomerStatus(Integer statusId, CustomerStatusDto dto) {
		CustomerStatus existing = customerStatusRepository.findById(statusId).orElseThrow(
				() -> new BusinessException("Cannot update. Customer status not found with id: " + statusId));

		if (!existing.getStatusName().equalsIgnoreCase(dto.getStatusName())) {
			boolean exists = customerStatusRepository.existsByTenantIdAndStatusName(dto.getTenantId(),
					dto.getStatusName());
			if (exists) {
				throw new BusinessException("Status name already exists for this tenant.");
			}
		}
        
		existing.setStatusName(dto.getStatusName());
		existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
		existing.setUpdatedBy(dto.getUpdatedBy());
		existing.setUpdatedAt(LocalDateTime.now());
		

		return customerStatusRepository.save(existing); // this will update the row
	}
	@Transactional
	public void deleteCustomerStatus(Integer statusId) {
	    CustomerStatus customerStatus = customerStatusRepository.findById(statusId)
	        .orElseThrow(() -> new BusinessException("Cannot delete. Customer status not found with id: " + statusId));

	    customerStatus.setIsDeleted(true); // 👈 Mark as deleted
	//    customerStatus.setUpdatedAt(LocalDateTime.now()); // Optional: update timestamp
	    customerStatusRepository.save(customerStatus); // 👈 Save instead of delete
	}

}
