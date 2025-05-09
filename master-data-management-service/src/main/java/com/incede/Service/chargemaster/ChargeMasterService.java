package com.incede.Service.chargemaster;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.chargemaster.ChargeMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.chargemaster.ChargeMaster;
import com.incede.Repository.chargemaster.ChargeMasterRepository;

@Service
public class ChargeMasterService {

    private final ChargeMasterRepository chargeMasterRepository;

    public ChargeMasterService(ChargeMasterRepository chargeMasterRepository) {
        this.chargeMasterRepository = chargeMasterRepository;
    }

    private ChargeMasterDto toDto(ChargeMaster charge) {
        ChargeMasterDto dto = new ChargeMasterDto();
        dto.setChargeId(charge.getChargeId());
        dto.setTenantId(charge.getTenantId());
        dto.setChargeName(charge.getChargeName());
        dto.setChargeCode(charge.getChargeCode());
        dto.setChargeAmount(charge.getChargeAmount());
        dto.setChargeType(charge.getChargeType());
        dto.setGlAccountId(charge.getGlAccountId());
        dto.setIsActive(charge.getIsActive());
        dto.setIsDeleted(charge.getIsDeleted());
        dto.setCreatedBy(charge.getCreatedBy());
        dto.setUpdatedBy(charge.getUpdatedBy());
        dto.setIdentity(charge.getIdentity());
        return dto;
    }

    private ChargeMaster toEntity(ChargeMasterDto dto) {
        ChargeMaster charge = new ChargeMaster();
        charge.setTenantId(dto.getTenantId());
        charge.setChargeName(dto.getChargeName());
        charge.setChargeCode(dto.getChargeCode());
        charge.setChargeAmount(dto.getChargeAmount());
        charge.setChargeType(dto.getChargeType());
        charge.setGlAccountId(dto.getGlAccountId());
        charge.setIsActive(Boolean.TRUE.equals(dto.getIsActive()));
        charge.setIsDeleted(Boolean.TRUE.equals(dto.getIsDeleted()));
        charge.setCreatedBy(dto.getCreatedBy());
        charge.setIdentity(UUID.randomUUID());
        return charge;
    }

    private void validateChargeType(String type) {
        if (!"Fixed".equalsIgnoreCase(type) && !"Percentage".equalsIgnoreCase(type)) {
            throw new BusinessException("Invalid charge_type. Only 'Fixed' or 'Percentage' are allowed.");
        }
    }

    @Transactional(readOnly = true)
    public List<ChargeMasterDto> getAllCharges() {
        return chargeMasterRepository.findAll().stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChargeMasterDto getChargeById(Integer id) {
        ChargeMaster charge = chargeMasterRepository.findById(id)
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Charge not found with id: " + id));
        return toDto(charge);
    }

    @Transactional
    public ChargeMasterDto createCharge(ChargeMasterDto dto) {
        validateChargeType(dto.getChargeType());

        boolean exists = chargeMasterRepository.findByTenantIdAndChargeCodeIgnoreCase(
                dto.getTenantId(), dto.getChargeCode())
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .isPresent();

        if (exists) {
            throw new BusinessException("Charge code already exists for this tenant.");
        }

        ChargeMaster charge = toEntity(dto);
        return toDto(chargeMasterRepository.save(charge));
    }

    @Transactional
    public ChargeMasterDto updateCharge(Integer id, ChargeMasterDto dto) {
        validateChargeType(dto.getChargeType());

        ChargeMaster existing = chargeMasterRepository.findById(id)
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot update. Charge not found with id: " + id));

        if (!existing.getChargeCode().equalsIgnoreCase(dto.getChargeCode())) {
            boolean exists = chargeMasterRepository.findByTenantIdAndChargeCodeIgnoreCase(
                    dto.getTenantId(), dto.getChargeCode())
                    .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                    .isPresent();

            if (exists) {
                throw new BusinessException("Charge code already exists for this tenant.");
            }
        }

        existing.setChargeName(dto.getChargeName());
        existing.setChargeCode(dto.getChargeCode());
        existing.setChargeAmount(dto.getChargeAmount());
        existing.setChargeType(dto.getChargeType());
        existing.setGlAccountId(dto.getGlAccountId());
        existing.setIsActive(dto.getIsActive());
        existing.setUpdatedBy(dto.getUpdatedBy());

        return toDto(chargeMasterRepository.save(existing));
    }

    @Transactional
    public void deleteCharge(Integer id) {
        ChargeMaster charge = chargeMasterRepository.findById(id)
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot delete. Charge not found with id: " + id));

        charge.setIsDeleted(true);
        chargeMasterRepository.save(charge);
    }
}
