package com.incede.Repository.payment.paymentmodemaster;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.payment.paymentmodemaster.PaymentModeMaster;


@Repository
public interface PaymentModeMasterRepository extends JpaRepository<PaymentModeMaster, Integer> {

    // Check if payment mode name already exists for the same tenant (case-insensitive)
    boolean existsByTenantIdAndPaymentModeNameIgnoreCase(Integer tenantId, String paymentModeName);

    // Find payment mode by its UUID identity
    Optional<PaymentModeMaster> findByIdentity(UUID identity);

    // Additional custom queries can be added here if needed
}
