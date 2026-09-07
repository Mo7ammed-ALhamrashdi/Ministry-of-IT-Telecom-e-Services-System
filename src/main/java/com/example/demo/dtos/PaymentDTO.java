package com.example.demo.dtos;

import com.example.demo.entities.Payment;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;

    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount cannot be negative")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    @NotNull(message = "Payment status is required")
    private PaymentStatus status;

    private LocalDate paidDate;

    @NotNull(message = "Application id is required")
    private Long applicationId;

    public static PaymentDTO convertToDTO(Payment payment) {

        return  PaymentDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .paidDate(payment.getPaidDate())
                .applicationId(
                        payment.getApplication() != null
                                ? payment.getApplication().getId()
                                : null
                )
                .build();
    }

    public static List<PaymentDTO> convertToDTO(
            List<Payment> payments) {

        return payments.stream()
                .map(PaymentDTO::convertToDTO)
                .toList();
    }
}