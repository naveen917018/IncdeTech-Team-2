package com.incede.Service.document.documentconfiguration;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.document.documentconfiguration.DocumentRequirementConfigurationDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.document.documentconfiguration.DocumentRequirementConfiguration;
import com.incede.Repository.document.documentconfiguration.DocumentRequirementConfigurationRepository;

@Service
public class DocumentRequirementConfigurationService {

    private final DocumentRequirementConfigurationRepository repository;

    public DocumentRequirementConfigurationService(DocumentRequirementConfigurationRepository repository) {
        this.repository = repository;
    }

    private DocumentRequirementConfigurationDto toDto(DocumentRequirementConfiguration entity) {
        DocumentRequirementConfigurationDto dto = new DocumentRequirementConfigurationDto();
        dto.setTenantId(entity.getTenantId());
        dto.setDocumentId(entity.getDocumentId());
        dto.setProductId(entity.getProductId());
        dto.setDocumentType(entity.getDocumentType());
        dto.setDocumentCategory(entity.getDocumentCategory());
        dto.setSequenceNumber(entity.getSequenceNumber());
        dto.setIsMandatory(entity.getIsMandatory());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIdentity(entity.getIdentity());
        return dto;
    }

    private DocumentRequirementConfiguration toEntity(DocumentRequirementConfigurationDto dto) {
        DocumentRequirementConfiguration entity = new DocumentRequirementConfiguration();
        entity.setTenantId(dto.getTenantId());
        entity.setProductId(dto.getProductId());
        entity.setDocumentType(dto.getDocumentType());
        entity.setDocumentCategory(dto.getDocumentCategory());
        entity.setSequenceNumber(dto.getSequenceNumber());
        entity.setIsMandatory(dto.getIsMandatory());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setIdentity(dto.getIdentity() != null ? dto.getIdentity() : UUID.randomUUID());
        return entity;
    }

    @Transactional(readOnly = true)
    public List<DocumentRequirementConfigurationDto> getAll() {
        return repository.findAll().stream()
                .filter(cfg -> cfg.getIsDeleted() == null || !cfg.getIsDeleted())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DocumentRequirementConfiguration getById(Integer id) {
        return repository.findById(id)
                .filter(cfg -> cfg.getIsDeleted() == null || !cfg.getIsDeleted())
                .orElseThrow(() -> new BusinessException("Configuration not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public DocumentRequirementConfiguration getByIdentity(UUID identity) {
        return repository.findByIdentity(identity)
                .filter(cfg -> cfg.getIsDeleted() == null || !cfg.getIsDeleted())
                .orElseThrow(() -> new BusinessException("Configuration not found with identity: " + identity));
    }

    @Transactional
    public DocumentRequirementConfiguration create(DocumentRequirementConfigurationDto dto) {
        boolean exists = repository.existsByProductIdAndDocumentType(dto.getProductId(), dto.getDocumentType());
        if (exists) {
            throw new BusinessException("Duplicate document type for the given product is not allowed.");
        }

        DocumentRequirementConfiguration config = toEntity(dto);
        return repository.save(config);
    }

    @Transactional
    public DocumentRequirementConfiguration update(Integer id, DocumentRequirementConfigurationDto dto) {
        DocumentRequirementConfiguration existing = repository.findById(id)
                .filter(cfg -> cfg.getIsDeleted() == null || !cfg.getIsDeleted())
                .orElseThrow(() -> new BusinessException("Cannot update. Configuration not found with id: " + id));

        if (!existing.getDocumentType().equalsIgnoreCase(dto.getDocumentType()) ||
            !existing.getProductId().equals(dto.getProductId())) {
            boolean exists = repository.existsByProductIdAndDocumentType(dto.getProductId(), dto.getDocumentType());
            if (exists) {
                throw new BusinessException("Duplicate document type for the given product is not allowed.");
            }
        }

        existing.setTenantId(dto.getTenantId());
        existing.setProductId(dto.getProductId());
        existing.setDocumentType(dto.getDocumentType());
        existing.setDocumentCategory(dto.getDocumentCategory());
        existing.setSequenceNumber(dto.getSequenceNumber());
        existing.setIsMandatory(dto.getIsMandatory());
        existing.setIsActive(dto.getIsActive());
        existing.setUpdatedBy(dto.getUpdatedBy());

        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        DocumentRequirementConfiguration config = repository.findById(id)
                .filter(cfg -> cfg.getIsDeleted() == null || !cfg.getIsDeleted())
                .orElseThrow(() -> new BusinessException("Cannot delete. Configuration not found with id: " + id));

        config.setIsDeleted(true);
        repository.save(config);
    }
}
