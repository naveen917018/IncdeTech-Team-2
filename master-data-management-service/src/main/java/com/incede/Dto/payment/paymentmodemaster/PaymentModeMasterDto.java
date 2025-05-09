package com.incede.Dto.payment.paymentmodemaster;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class PaymentModeMasterDto {

    private Integer paymentModeId;
    private Integer tenantId;
    private String paymentModeName;
    private String paymentModeCode;
    private Boolean isOnline;
    private Boolean requiresReference;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;
}
