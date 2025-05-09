package com.incede.Controller.payment.paymentmodemaster;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.payment.paymentmodemaster.PaymentModeMasterDto;
import com.incede.Model.payment.paymentmodemaster.PaymentModeMaster;
import com.incede.Service.payment.paymentmodemaster.PaymentModeMasterService;

@RestController
@RequestMapping("/v1/masterdata/payments/payment-modes")
public class PaymentModeMasterController {

    private final PaymentModeMasterService paymentModeMasterService;

    public PaymentModeMasterController(PaymentModeMasterService paymentModeMasterService) {
        this.paymentModeMasterService = paymentModeMasterService;
    }

    // Get all payment modes that are not deleted
    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentModeMasterDto>> getAllPaymentModes() {
        List<PaymentModeMasterDto> paymentModes = paymentModeMasterService.getAllPaymentModes();
        return ResponseEntity.ok(paymentModes);
    }

    // Get a payment mode by ID
    @GetMapping("/{paymentModeId}")
    public ResponseEntity<PaymentModeMaster> getPaymentModeById(@PathVariable Integer paymentModeId) {
        return ResponseEntity.ok(paymentModeMasterService.getPaymentMode(paymentModeId));
    }

    // Create a new payment mode
    @PostMapping("/")
    public ResponseEntity<PaymentModeMaster> createPaymentMode(@RequestBody PaymentModeMasterDto dto) {
        return ResponseEntity.status(201).body(paymentModeMasterService.createPaymentMode(dto));
    }

    // Update an existing payment mode
    @PutMapping("/{paymentModeId}")
    public ResponseEntity<PaymentModeMaster> updatePaymentMode(@PathVariable Integer paymentModeId,
            @RequestBody PaymentModeMasterDto dto) {
        return ResponseEntity.ok(paymentModeMasterService.updatePaymentMode(paymentModeId, dto));
    }

    // Soft delete a payment mode
    @DeleteMapping("/{paymentModeId}")
    public ResponseEntity<String> deletePaymentMode(@PathVariable Integer paymentModeId) {
        paymentModeMasterService.deletePaymentMode(paymentModeId);
        return ResponseEntity.ok("Payment mode deleted");
    }
}
