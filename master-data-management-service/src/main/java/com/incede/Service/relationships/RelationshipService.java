package com.incede.Service.relationships;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.relationships.RelationshipDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.relationships.Relationship;
import com.incede.Repository.relationships.RelationshipRepository;

@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;

    public RelationshipService(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }

    private RelationshipDto toDto(Relationship entity) {
        RelationshipDto dto = new RelationshipDto();
        dto.setRelationshipId(entity.getRelationshipId());
        dto.setTenantId(entity.getTenantId());
        dto.setRelationship(entity.getRelationship());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIsDeleted(entity.getIsDeleted());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<RelationshipDto> getAllRelationships() {
        return relationshipRepository.findAll().stream()
                .filter(r -> !Boolean.TRUE.equals(r.getIsDeleted()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Relationship createRelationship(RelationshipDto relationshipDto) {
        boolean exists = relationshipRepository
                .existsByTenantIdAndRelationshipIgnoreCaseAndIsDeletedFalse(
                        relationshipDto.getTenantId(),
                        relationshipDto.getRelationship());

        if (exists) {
            throw new BusinessException("Relationship name must be unique (case-insensitive) for the tenant.");
        }

        Relationship relationship = new Relationship();
        relationship.setTenantId(relationshipDto.getTenantId());
        relationship.setRelationship(relationshipDto.getRelationship());
        relationship.setIsActive(relationshipDto.getIsActive() != null ? relationshipDto.getIsActive() : true);
        relationship.setCreatedBy(relationshipDto.getCreatedBy());
        relationship.setIsDeleted(false);

        return relationshipRepository.save(relationship);
    }

    @Transactional(readOnly = true)
    public RelationshipDto getRelationship(Integer relationshipId) {
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .filter(r -> !Boolean.TRUE.equals(r.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Relationship not found with id: " + relationshipId));
        return toDto(relationship);
    }

    @Transactional
    public Relationship updateRelationship(Integer relationshipId, RelationshipDto relationshipDto) {
        Relationship existing = relationshipRepository.findById(relationshipId)
                .filter(r -> !Boolean.TRUE.equals(r.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot update. Relationship not found with id: " + relationshipId));

        // Check uniqueness only if relationship name is changed
        if (!existing.getRelationship().equalsIgnoreCase(relationshipDto.getRelationship())) {
            boolean exists = relationshipRepository
                    .existsByTenantIdAndRelationshipIgnoreCaseAndIsDeletedFalse(
                            relationshipDto.getTenantId(),
                            relationshipDto.getRelationship());

            if (exists) {
                throw new BusinessException("Duplicate relationship name for this tenant is not allowed.");
            }
        }

        existing.setRelationship(relationshipDto.getRelationship());
        existing.setIsActive(relationshipDto.getIsActive());
        existing.setUpdatedBy(relationshipDto.getUpdatedBy());

        return relationshipRepository.save(existing);
    }

    @Transactional
    public void deleteRelationship(Integer relationshipId) {
        Relationship relationship = relationshipRepository.findById(relationshipId)
                .filter(r -> !Boolean.TRUE.equals(r.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot delete. Relationship not found with id: " + relationshipId));

        relationship.setIsDeleted(true);
        relationshipRepository.save(relationship);
    }
}
