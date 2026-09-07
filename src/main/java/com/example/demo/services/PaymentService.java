package com.example.demo.services;

import com.example.demo.dtos.PaymentDTO;
import com.example.demo.entities.Application;
import com.example.demo.entities.Payment;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ApplicationRepository;
import com.example.demo.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApplicationRepository applicationRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            ApplicationRepository applicationRepository) {

        this.paymentRepository =
                paymentRepository;

        this.applicationRepository =
                applicationRepository;
    }

    public PaymentDTO add(
            PaymentDTO dto) {

        Application application =
                findApplicationById(
                        dto.getApplicationId()
                );

        Payment payment =
                new Payment();

        payment.setAmount(
                dto.getAmount()
        );

        payment.setMethod(
                dto.getMethod()
        );

        payment.setStatus(
                dto.getStatus()
        );

        payment.setPaidDate(
                dto.getPaidDate()
        );

        payment.setApplication(
                application
        );

        payment.setIsActive(true);

        payment.setCreatedDate(
                LocalDateTime.now()
        );

        payment.setUpdatedDate(
                LocalDateTime.now()
        );

        Payment savedPayment =
                paymentRepository.save(
                        payment
                );

        return PaymentDTO.convertToDTO(
                savedPayment
        );
    }

    public List<PaymentDTO> getAll() {

        List<Payment> payments =
                paymentRepository
                        .findAll()
                        .stream()
                        .filter(payment ->
                                Boolean.TRUE.equals(
                                        payment.getIsActive()
                                )
                        )
                        .toList();

        return PaymentDTO.convertToDTO(
                payments
        );
    }

    public PaymentDTO getById(
            Long id) {

        Payment payment =
                findPaymentById(id);

        return PaymentDTO.convertToDTO(
                payment
        );
    }

    public PaymentDTO update(
            Long id,
            PaymentDTO dto) {

        Payment payment =
                findPaymentById(id);

        Application application =
                findApplicationById(
                        dto.getApplicationId()
                );

        payment.setAmount(
                dto.getAmount()
        );

        payment.setMethod(
                dto.getMethod()
        );

        payment.setStatus(
                dto.getStatus()
        );

        payment.setPaidDate(
                dto.getPaidDate()
        );

        payment.setApplication(
                application
        );

        payment.setUpdatedDate(
                LocalDateTime.now()
        );

        Payment updatedPayment =
                paymentRepository.save(
                        payment
                );

        return PaymentDTO.convertToDTO(
                updatedPayment
        );
    }

    public void delete(Long id) {

        Payment payment =
                findPaymentById(id);

        payment.setIsActive(false);

        payment.setUpdatedDate(
                LocalDateTime.now()
        );

        paymentRepository.save(
                payment
        );
    }

    private Payment findPaymentById(
            Long id) {

        Payment payment =
                paymentRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                payment.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Payment not found with id: "
                            + id
            );
        }

        return payment;
    }

    private Application findApplicationById(
            Long id) {

        Application application =
                applicationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application not found with id: "
                                                + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                application.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Application not found with id: "
                            + id
            );
        }

        return application;
    }
}