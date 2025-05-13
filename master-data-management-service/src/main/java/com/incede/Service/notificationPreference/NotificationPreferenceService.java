package com.incede.Service.notificationPreference;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.notificationPreference.NotificationPreferenceDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.notificationPreference.NotificationPreference;
import com.incede.Repository.notificationPreference.NotificationPreferenceRepository;

@Service
public class NotificationPreferenceService {
	
	private final NotificationPreferenceRepository notificationPreferenceRepository;

    public NotificationPreferenceService(NotificationPreferenceRepository repository) {
        this.notificationPreferenceRepository = repository;
    }
    
    private NotificationPreferenceDto toDto(NotificationPreference entity) {
        NotificationPreferenceDto dto = new NotificationPreferenceDto();
        dto.setPreferenceId(entity.getPreferenceId());
        dto.setTenantId(entity.getTenantId());
        dto.setPreferenceType(entity.getPreferenceType());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIsDeleted(entity.getIsDeleted());
        return dto;
    }

    private NotificationPreference toEntity(NotificationPreferenceDto dto) {
        NotificationPreference entity = new NotificationPreference();
        entity.setPreferenceId(dto.getPreferenceId() != null ? dto.getPreferenceId() : null);
        entity.setTenantId(dto.getTenantId());
        entity.setPreferenceType(dto.getPreferenceType());
//        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        return entity;
    }

    @Transactional(readOnly = true)
    public List<NotificationPreferenceDto> getAll() {
    	
    	List<NotificationPreference> list = notificationPreferenceRepository.findAllByIsDeletedFalse();
        if (list.isEmpty()) {
            throw new BusinessException("No preferences found ");
        }
        return list.stream() 
                   .map(this::toDto)
                   .collect(Collectors.toList());
    	
//        return notificationPreferenceRepository.findAll().stream().filter(noti -> !noti.getIsDeleted())
//                .map(this::toDto)
//                .collect(Collectors.toList());

    }
    
    @Transactional(readOnly = true)
    public List<NotificationPreferenceDto> getByTenantId(Integer tenantId) {
        List<NotificationPreference> list = notificationPreferenceRepository.findAllByTenantIdAndIsDeletedFalse(tenantId);
        if (list.isEmpty()) {
            throw new BusinessException("No preferences found for tenantId: " + tenantId);
        }
        return list.stream()
                   .map(this::toDto)
                   .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificationPreferenceDto getById(Integer id) {
        NotificationPreference preference = notificationPreferenceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Preference not found with id: " + id));
        return toDto(preference);
    }
    
    @Transactional(readOnly = true)
    public NotificationPreferenceDto getByIdIsDeletedFalse(Integer id) {
        NotificationPreference pref = notificationPreferenceRepository.findByPreferenceIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new BusinessException("Preference not found id=" + id));
        return toDto(pref);
    }

//    @Transactional
//    public NotificationPreferenceDto create(NotificationPreferenceDto dto) {
//        if (notificationPreferenceRepository.existsByTenantIdAndPreferenceType(dto.getTenantId(), dto.getPreferenceType())) {
//            throw new BusinessException("Preference already exists for tenant and type.");
//        }
//        NotificationPreference saved = notificationPreferenceRepository.save(toEntity(dto));
//        return toDto(saved);
//    }
    
    @Transactional
    public NotificationPreferenceDto createNotificationPreference(NotificationPreferenceDto dto) {
//    	if(dto.getTenantId() == null || !(dto.getTenantId() instanceof Integer)) {
//			throw new BusinessException("Tenant Id Must be a Not Null and in a valid format");
//		}
//    	if(dto.getPreferenceType() == null) {
//			throw new BusinessException("Preference Type Must be a Not Null");
//		}
//    	if(dto.getTenantId() == null || !(dto.getTenantId() instanceof Integer)) {
//			throw new BusinessException("Tenant Id Must be a valid format");
//		}
    	if(dto.getCreatedBy() == null ||!(dto.getCreatedBy() instanceof Integer)) {
    		throw new BusinessException("Created by Must be a Not Null and in a valid format");
    	}
        // CASE-INSENSITIVE duplicate check for active rows
        if (notificationPreferenceRepository.existsByTenantIdAndPreferenceTypeIgnoreCaseAndIsDeletedFalse(
                dto.getTenantId(),
                dto.getPreferenceType()
        )) {
            throw new BusinessException("A preference of type '" +
                dto.getPreferenceType() +
                "' already exists for tenant " + dto.getTenantId());
        }
        NotificationPreference entity = toEntity(dto);
        entity.setPreferenceId(null);
        entity.setUpdatedAt(null);
        NotificationPreference saved = notificationPreferenceRepository.save(entity);
        return toDto(saved);
    }

//    @Transactional
//    public NotificationPreferenceDto update(Integer id, NotificationPreferenceDto dto) {
//        NotificationPreference existing = notificationPreferenceRepository.findById(id)
//                .orElseThrow(() -> new BusinessException("Preference not found with id: " + id));
//        
//        if (notificationPreferenceRepository.existsByTenantIdAndPreferenceType(existing.getTenantId(), dto.getPreferenceType())) {
//            throw new BusinessException("Preference already exists for tenant and type.");
//        }
//        
//        existing.setPreferenceType(dto.getPreferenceType());
//        existing.setIsActive(dto.getIsActive());
//        return toDto(notificationPreferenceRepository.save(existing));
//    }
    
    @Transactional
    public NotificationPreferenceDto updateNotificationPreference(Integer id, NotificationPreferenceDto dto) {
    	if(dto.getUpdatedBy() == null || !(dto.getUpdatedBy() instanceof Integer)) {
    		throw new BusinessException("Updated by is Null or Not a valid datatype");
    	}
    	if(dto.getTenantId() == null || !(dto.getTenantId() instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a Not Null and in a valid format");
		}
        NotificationPreference existing = notificationPreferenceRepository.findByPreferenceIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new BusinessException("Preference not found with id: " + id));

        // EXCLUDE this record itself from the duplicate check:
        if (notificationPreferenceRepository.existsByTenantIdAndPreferenceTypeIgnoreCaseAndPreferenceIdNotAndIsDeletedFalse(
                existing.getTenantId(),
                dto.getPreferenceType(),
                id
        )) {
            throw new BusinessException("Cannot change to '" +
                dto.getPreferenceType() +
                "'; another active preference with that type exists for tenant " +
                existing.getTenantId());
        }

        existing.setPreferenceType(dto.getPreferenceType());
        existing.setIsActive(dto.getIsActive());
        existing.setUpdatedBy(dto.getUpdatedBy());
        NotificationPreference updated = notificationPreferenceRepository.save(existing);
        return toDto(updated);
    }

    @Transactional
    public void deleteNotificationPreference(Integer id) {
    	if(id == null || !(id instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a Not Null and in a valid format");
		}
        NotificationPreference existing = notificationPreferenceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Preference not found with id: " + id));
        existing.setIsDeleted(true);
        notificationPreferenceRepository.save(existing);
    }

}
