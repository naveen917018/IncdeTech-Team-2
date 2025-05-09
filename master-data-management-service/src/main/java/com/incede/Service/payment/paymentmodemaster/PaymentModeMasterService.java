package com.incede.Service.payment.paymentmodemaster;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.payment.paymentmodemaster.PaymentModeMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.payment.paymentmodemaster.PaymentModeMaster;
import com.incede.Repository.payment.paymentmodemaster.PaymentModeMasterRepository;

@Service
public class PaymentModeMasterService {

    private final PaymentModeMasterRepository paymentModeMasterRepository;

    public PaymentModeMasterService(PaymentModeMasterRepository paymentModeMasterRepository) {
        this.paymentModeMasterRepository = paymentModeMasterRepository;
    }

    private PaymentModeMasterDto toDto(PaymentModeMaster paymentModeMaster) {
        PaymentModeMasterDto dto = new PaymentModeMasterDto();
        dto.setPaymentModeId(paymentModeMaster.getPaymentModeId());
        dto.setTenantId(paymentModeMaster.getTenantId());
        dto.setPaymentModeName(paymentModeMaster.getPaymentModeName());
        dto.setPaymentModeCode(paymentModeMaster.getPaymentModeCode());
        dto.setIsOnline(paymentModeMaster.getIsOnline());
        dto.setRequiresReference(paymentModeMaster.getRequiresReference());
        dto.setIsActive(paymentModeMaster.getIsActive());
        dto.setIdentity(paymentModeMaster.getIdentity());
        dto.setCreatedBy(paymentModeMaster.getCreatedBy());
        dto.setCreatedAt(paymentModeMaster.getCreatedAt());
        dto.setUpdatedBy(paymentModeMaster.getUpdatedBy());
        dto.setUpdatedAt(paymentModeMaster.getUpdatedAt());
        return dto;
    }

    private PaymentModeMaster toEntity(PaymentModeMasterDto dto) {
        PaymentModeMaster entity = new PaymentModeMaster();
        entity.setTenantId(dto.getTenantId());
        entity.setPaymentModeName(dto.getPaymentModeName());
        entity.setPaymentModeCode(dto.getPaymentModeCode());
        entity.setIsOnline(dto.getIsOnline() != null ? dto.getIsOnline() : false);
        entity.setRequiresReference(dto.getRequiresReference() != null ? dto.getRequiresReference() : false);
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        // Assuming createdBy and updatedBy are of type Integer
        if (dto.getCreatedBy() != null) {
            entity.setCreatedBy(dto.getCreatedBy());
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public List<PaymentModeMasterDto> getAllPaymentModes() {
        return paymentModeMasterRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentModeMaster getPaymentMode(Integer paymentModeId) {
        return paymentModeMasterRepository.findById(paymentModeId)
                .orElseThrow(() -> new BusinessException("Payment mode not found with id: " + paymentModeId));
    }

    @Transactional
    public PaymentModeMaster createPaymentMode(PaymentModeMasterDto dto) {
        // Check if payment_mode_name already exists for the tenant (case-insensitive)
        boolean exists = paymentModeMasterRepository.existsByTenantIdAndPaymentModeNameIgnoreCase(dto.getTenantId(), dto.getPaymentModeName());
        if (exists) {
            throw new BusinessException("Payment mode name already exists for this tenant. Please choose a different name.");
        }

        PaymentModeMaster paymentModeMaster = toEntity(dto);
        paymentModeMaster.setIdentity(java.util.UUID.randomUUID()); // Set UUID identity
        return paymentModeMasterRepository.save(paymentModeMaster);
    }

    @Transactional
    public PaymentModeMaster updatePaymentMode(Integer paymentModeId, PaymentModeMasterDto dto) {
        PaymentModeMaster existing = paymentModeMasterRepository.findById(paymentModeId)
                .orElseThrow(() -> new BusinessException("Cannot update. Payment mode not found with id: " + paymentModeId));

        // Check if the payment mode name has changed and validate uniqueness
        if (!existing.getPaymentModeName().equalsIgnoreCase(dto.getPaymentModeName())) {
            boolean exists = paymentModeMasterRepository.existsByTenantIdAndPaymentModeNameIgnoreCase(dto.getTenantId(), dto.getPaymentModeName());
            if (exists) {
                throw new BusinessException("Payment mode name already exists for this tenant.");
            }
        }

        existing.setPaymentModeName(dto.getPaymentModeName());
        existing.setPaymentModeCode(dto.getPaymentModeCode());
        existing.setIsOnline(dto.getIsOnline() != null ? dto.getIsOnline() : false);
        existing.setRequiresReference(dto.getRequiresReference() != null ? dto.getRequiresReference() : false);
        existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        existing.setUpdatedBy(dto.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        return paymentModeMasterRepository.save(existing);
    }

    @Transactional
    public void deletePaymentMode(Integer paymentModeId) {
        PaymentModeMaster paymentModeMaster = paymentModeMasterRepository.findById(paymentModeId)
                .orElseThrow(() -> new BusinessException("Cannot delete. Payment mode not found with id: " + paymentModeId));

        paymentModeMaster.setIsActive(false); // Deactivate instead of deleting
        paymentModeMasterRepository.save(paymentModeMaster);
    }
}
