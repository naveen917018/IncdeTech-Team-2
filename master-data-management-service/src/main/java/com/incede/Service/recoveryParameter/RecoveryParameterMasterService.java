package com.incede.Service.recoveryParameter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incede.Dto.recoveryParameter.RecoveryParameterMasterDTO;
import com.incede.Exception.BusinessException;
import com.incede.Model.recoveryParameter.RecoveryParameter;
import com.incede.Model.recoveryParameter.RecoveryParameterMaster;
import com.incede.Repository.recoveryParameter.RecoveryParameterRepository;
import com.incede.validation.validParam.ParamValueValidator;

@Service
public class RecoveryParameterMasterService {
	
	private final RecoveryParameterRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public RecoveryParameterMasterService(RecoveryParameterRepository repository) {
		// TODO Auto-generated constructor stub
    	this.repository = repository;
	}
    
    @Transactional
    public RecoveryParameterMasterDTO createParameter(RecoveryParameterMasterDTO dto) {
        RecoveryParameter paramEnum = RecoveryParameter.fromName(dto.getParamName());
        if(! paramEnum.getValidationType().equals(dto.getParamDataType())) {
        	throw new BusinessException(
                  String.format("Incompatible parameter datatype, with value")
              );
        }
        ParamValueValidator.validate(dto.getParamDataType(), dto.getParamValue());
        RecoveryParameterMaster entity = toEntity(dto);
        Optional<RecoveryParameterMaster> recoveryParamOpt = repository.findByTenantIdAndParamNameIgnoreCase(dto.getTenantId(), dto.getParamName());
        if(recoveryParamOpt.isPresent()) {
        	if(recoveryParamOpt.get().getIsDeleted() == true) {
        		entity.setRecoveryParamId(recoveryParamOpt.get().getRecoveryParamId());
        		entity.setCreatedBy(recoveryParamOpt.get().getCreatedBy());
        		entity.setIdentity(recoveryParamOpt.get().getIdentity());
        		entity.setUpdatedBy(dto.getCreatedBy());
        	}
        	else {
        		throw new BusinessException("Parameter already exists for this tenant: " + dto.getParamName());
        	}
        }
        else {
	        entity.setRecoveryParamId(null);
	        entity.setUpdatedBy(null);
        }
	    entity.setIsDeleted(false);
		entity.setIsActive(true);
		entity = repository.save(entity);
	    return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public RecoveryParameterMasterDTO getParameterById(Integer id) {
        if (id == null|| !(id instanceof Integer)) {
            throw new BusinessException("Recovery Param Id must be a valid format");
        }

        return repository.findByRecoveryParamIdAndIsDeletedFalse(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Parameter not found or has been deleted"));
    }


    @Transactional(readOnly = true)
    public List<RecoveryParameterMasterDTO> getAllParameters() {
        return repository.findAllByIsDeletedFalse().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public RecoveryParameterMasterDTO updateParameter(RecoveryParameterMasterDTO dto) {
    	if(!(dto.getTenantId() instanceof Integer)) {
			throw new BusinessException("Tenant Id Must be a valid format");
		}
    	
    	if( !(dto.getRecoveryParamId() instanceof Integer)) {
			throw new BusinessException("Recovery Param Id Must be a valid format");
		}
    
    	if(dto.getUpdatedBy() == null || !(dto.getUpdatedBy() instanceof Integer)) {
			throw new BusinessException("Updated By not be null");
		}
    	RecoveryParameterMaster existing = repository.findByRecoveryParamId(dto.getRecoveryParamId())
                .orElseThrow(() -> new RuntimeException("Parameter not found"));
    	
    	RecoveryParameter paramEnum;
        try {
//        	System.out.println(1);
//        	if (repository.existsByTenantIdAndParamNameIgnoreCaseAndIsDeletedFalse(dto.getTenantId(), dto.getParamName())) {
//              throw new BusinessException("Recovery Parameter already exists for this tenant.");
//        	}
            paramEnum = RecoveryParameter.fromName(dto.getParamName());
            System.out.println("x"+paramEnum);
        } catch (IllegalArgumentException ex) {
        	System.out.println(1.1);
            throw new BusinessException("Unknown parameter name: " + dto.getParamName());
        }

        // 1) Validate paramValue format
        try {
        	System.out.println(2 +"---"+paramEnum.getValidationType()+"-----"+ dto.getParamValue());
            ParamValueValidator.validate(paramEnum.getValidationType(), dto.getParamValue());
        } catch (IllegalArgumentException ex) {
        	System.out.println(2.1);
            throw new BusinessException(
                String.format("Invalid value for '%s': %s", dto.getParamName(), ex.getMessage())
            );
        }
        System.out.println(2.5);


        existing.setParamName(dto.getParamName());
        existing.setParamValue(dto.getParamValue());
        existing.setParamDataType(dto.getParamDataType());
        existing.setUpdatedBy(dto.getUpdatedBy());

        return toDTO(repository.save(existing));
    }

    @Transactional
    public void deleteParameter(Integer id) {
    	if(id == null || !(id instanceof Integer)) {
			throw new BusinessException("Recovery Param Id Must be a valid format");
		}
    	RecoveryParameterMaster existing = repository.findByRecoveryParamId(id)
                .orElseThrow(() -> new BusinessException("Parameter not found"));
    	existing.setIsDeleted(true);
    	repository.save(existing);
    }

//    public static void validate(String dataType, String value) {
//        switch (dataType.toLowerCase()) {
//            case "string":
//                break; // no-op
//            case "number":
//                try {
//                    new BigDecimal(value);
//                } catch (NumberFormatException e) {
//                    throw new BusinessException("Value must be numeric");
//                }
//                break;
//            case "boolean":
//                if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
//                    throw new BusinessException("Value must be true or false");
//                }
//                break;
//            case "date":
//                try {
//                    LocalDate.parse(value); // ISO format
//                } catch (DateTimeParseException e) {
//                    throw new BusinessException("Value must be a valid date (yyyy-MM-dd)");
//                }
//                break;
//            case "array":
//                try {
//                    new ObjectMapper().readTree(value);
//                    if (!value.trim().startsWith("[")) {
//                        throw new BusinessException("Value must be a JSON array");
//                    }
//                } catch (IOException e) {
//                    throw new BusinessException("Invalid JSON array");
//                }
//                break;
//            case "object":
//                try {
//                    new ObjectMapper().readTree(value);
//                    if (!value.trim().startsWith("{")) {
//                        throw new BusinessException("Value must be a JSON object");
//                    }
//                } catch (IOException e) {
//                    throw new BusinessException("Invalid JSON object");
//                }
//                break;
//            default:
//                throw new BusinessException("Unsupported data type");
//        }
//    }

    private RecoveryParameterMaster toEntity(RecoveryParameterMasterDTO dto) {
    	RecoveryParameterMaster entity = new RecoveryParameterMaster();
        entity.setRecoveryParamId(dto.getRecoveryParamId());
        entity.setParamName(dto.getParamName());
        entity.setParamValue(dto.getParamValue());
        entity.setParamDataType(dto.getParamDataType());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setIsActive(dto.getIsActive());
        entity.setTenantId(dto.getTenantId());
//        entity.setIdentity(dto.getIdentity());
        return entity;
    }

    private RecoveryParameterMasterDTO toDTO(RecoveryParameterMaster entity) {
    	RecoveryParameterMasterDTO dto = new RecoveryParameterMasterDTO();
        dto.setRecoveryParamId(entity.getRecoveryParamId());
        dto.setParamName(entity.getParamName());
        dto.setParamValue(entity.getParamValue());
        dto.setParamDataType(entity.getParamDataType());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIsActive(entity.getIsActive());
        dto.setTenantId(entity.getTenantId());
//        dto.setIdentity(entity.getIdentity());
        return dto;
    }

}
