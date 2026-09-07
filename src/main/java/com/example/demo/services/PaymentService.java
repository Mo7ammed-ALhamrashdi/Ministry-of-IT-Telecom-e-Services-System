package com.example.demo.services;

import com.example.demo.entities.Payment;
import com.example.demo.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(
            PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;
    }

    public Payment add(Payment payment) {

        payment.setId(null);
        payment.setIsActive(true);
        payment.setCreatedDate(LocalDateTime.now());
        payment.setUpdatedDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public List<Payment> getAll() {

        return paymentRepository.findAll()
                .stream()
                .filter(payment ->
                        Boolean.TRUE.equals(
                                payment.getIsActive()))
                .toList();
    }

    public Payment getById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        if (!Boolean.TRUE.equals(payment.getIsActive())) {
            throw new RuntimeException(
                    "Payment not found");
        }

        return payment;
    }

    public Payment update(Long id, Payment request) {

        Payment payment = getById(id);

        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(request.getStatus());
        payment.setPaidDate(request.getPaidDate());
        payment.setApplication(
                request.getApplication());
        payment.setUpdatedDate(
                LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public void delete(Long id) {

        Payment payment = getById(id);

        payment.setIsActive(false);
        payment.setUpdatedDate(LocalDateTime.now());

        paymentRepository.save(payment);
    }
}