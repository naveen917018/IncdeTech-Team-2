package com.incede.Service.liability.liabilityglheadconfiguration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.liability.liabilityglheadconfiguration.LiabilitySchemeGlHeadConfigurationDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.liability.liabilityglheadconfiguration.LiabilitySchemeGlHeadConfiguration;
import com.incede.Repository.liability.liabilityglheadconfiguration.LiabilitySchemeGlHeadConfigurationRepository;

import jakarta.validation.Valid;

@Service
public class LiabilitySchemeGlHeadConfigurationService {

	private final LiabilitySchemeGlHeadConfigurationRepository repository;

	public LiabilitySchemeGlHeadConfigurationService(LiabilitySchemeGlHeadConfigurationRepository repository) {
		this.repository = repository;
	}

	private LiabilitySchemeGlHeadConfigurationDto toDto(LiabilitySchemeGlHeadConfiguration entity) {
		LiabilitySchemeGlHeadConfigurationDto dto = new LiabilitySchemeGlHeadConfigurationDto();

		dto.setGlAccountId(entity.getGlAccountId());
		dto.setGlAccountType(entity.getGlAccountType());
		dto.setGlConfigId(entity.getGlConfigId());
		dto.setSchemeId(entity.getSchemeId());
		dto.setIsDeleted(entity.getIsDeleted() != null ? entity.getIsDeleted() : false);
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setUpdatedBy(entity.getUpdatedBy());
		return dto;
	}

	private LiabilitySchemeGlHeadConfiguration toEntity(LiabilitySchemeGlHeadConfigurationDto dto) {
		LiabilitySchemeGlHeadConfiguration entity = new LiabilitySchemeGlHeadConfiguration();
		entity.setGlAccountId(dto.getGlAccountId());
		entity.setGlAccountType(dto.getGlAccountType());
		entity.setGlConfigId(dto.getGlConfigId());
		entity.setSchemeId(dto.getSchemeId());
		entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);
		entity.setCreatedBy(dto.getCreatedBy());
		entity.setUpdatedBy(dto.getUpdatedBy());
		return entity;
	}

	@Transactional(readOnly = true)
	public List<LiabilitySchemeGlHeadConfigurationDto> getAll() {
		return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public LiabilitySchemeGlHeadConfigurationDto getById(Integer id) {
		LiabilitySchemeGlHeadConfiguration liability = repository.findById(id)
				.orElseThrow(() -> new BusinessException("Liability scheme not found with id:" + id));
		return toDto(liability);

	}

	@Transactional
	public LiabilitySchemeGlHeadConfigurationDto create(LiabilitySchemeGlHeadConfigurationDto dto) {
		boolean exists = repository.existsByGlAccountTypeAndSchemeId(dto.getGlAccountType(), dto.getSchemeId());

		if (dto.getCreatedBy() == null) {
			throw new BusinessException("CreatedBy cannot be null.");
		}

		if (exists) {
			throw new BusinessException("Duplicate GL account type for the selected scheme id");
		}

		LiabilitySchemeGlHeadConfiguration liability = toEntity(dto);
		if (Boolean.TRUE.equals(dto.getIsDeleted())) {
			throw new BusinessException("Cannot create entity with isDeleted = true.");
		}
		repository.save(liability);

		return toDto(liability);
	}

	@Transactional
	public LiabilitySchemeGlHeadConfigurationDto update(Integer id, LiabilitySchemeGlHeadConfigurationDto dto) {
		LiabilitySchemeGlHeadConfiguration existing = repository.findById(id)
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				.orElseThrow(() -> new BusinessException("Cannot update, liability scheme not found with id:" + id));

		boolean exists = repository.existsByGlAccountTypeAndSchemeIdAndIsDeletedFalse(dto.getGlAccountType(),
				dto.getSchemeId());
		if (exists) {
			throw new BusinessException("Duplicate GL account type for the selected scheme id not allowed");
		}

		if (Boolean.TRUE.equals(dto.getIsDeleted())) {
			throw new BusinessException("Cannot update with isDeleted = true.");
		}

		if (dto.getCreatedBy() != null) {
			throw new BusinessException("No need to pass createdBy while updating");
		}

		if (dto.getUpdatedBy() == null) {
			throw new BusinessException("Please provide updatedBy.");
		}

		existing.setGlAccountId(dto.getGlAccountId());
		existing.setGlAccountType(dto.getGlAccountType());
		existing.setSchemeId(dto.getSchemeId());
		existing.setUpdatedBy(dto.getUpdatedBy());

		repository.save(existing);

		return toDto(existing);
	}

	@Transactional
	public void delete(Integer id) {
		LiabilitySchemeGlHeadConfiguration liability = repository.findById(id)
				.filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
				.orElseThrow(() -> new BusinessException("Cannot delete Lialbility scheme not found with id:" + id));
		liability.setIsDeleted(true);
		repository.save(liability);

	}

}
