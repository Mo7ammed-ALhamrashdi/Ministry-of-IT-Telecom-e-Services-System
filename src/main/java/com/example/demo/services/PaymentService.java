package com.example.demo.services;

import com.example.demo.dtos.PaymentDTO;
import com.example.demo.entities.Application;
import com.example.demo.entities.Payment;
import com.example.demo.enums.ApplicationStatus;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.ApplicationRepository;
import com.example.demo.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class  PaymentService {

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

        Optional<Payment> existingPayment =
                paymentRepository
                        .findByApplicationIdAndIsActiveTrue(
                                application.getId()
                        );

        if (existingPayment.isPresent()) {

            if (PaymentStatus.PAID.equals(
                    existingPayment
                            .get()
                            .getStatus())) {

                throw new IllegalArgumentException(
                        "Application has already been paid"
                );
            }

            throw new IllegalArgumentException(
                    "A payment already exists for this application"
            );
        }

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

        if (PaymentStatus.PAID.equals(
                dto.getStatus())) {

            payment.setPaidDate(
                    dto.getPaidDate() != null
                            ? dto.getPaidDate()
                            : LocalDate.now()
            );

            application.setStatus(
                    ApplicationStatus.PROCESSING
            );

            application.setUpdatedDate(
                    LocalDateTime.now()
            );

            applicationRepository.save(
                    application
            );

        } else {

            payment.setPaidDate(
                    dto.getPaidDate()
            );
        }

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

        if (PaymentStatus.PAID.equals(
                payment.getStatus())) {

            throw new IllegalArgumentException(
                    "Paid payment cannot be changed"
            );
        }

        payment.setAmount(
                dto.getAmount()
        );

        payment.setMethod(
                dto.getMethod()
        );

        payment.setStatus(
                dto.getStatus()
        );

        payment.setApplication(
                application
        );

        if (PaymentStatus.PAID.equals(
                dto.getStatus())) {

            payment.setPaidDate(
                    dto.getPaidDate() != null
                            ? dto.getPaidDate()
                            : LocalDate.now()
            );

            application.setStatus(
                    ApplicationStatus.PROCESSING
            );

            application.setUpdatedDate(
                    LocalDateTime.now()
            );

            applicationRepository.save(
                    application
            );

        } else {

            payment.setPaidDate(
                    dto.getPaidDate()
            );
        }

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

    public void delete(
            Long id) {

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