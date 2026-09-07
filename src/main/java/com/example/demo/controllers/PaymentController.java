package com.example.demo.controllers;

import com.example.demo.entities.Payment;
import com.example.demo.services.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    public Payment add(@RequestBody Payment payment) {

        return paymentService.add(payment);
    }

    @GetMapping("/getAll")
    public List<Payment> getAll() {

        return paymentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Payment getById(@PathVariable Long id) {

        return paymentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Payment update(
            @PathVariable Long id,
            @RequestBody Payment payment) {

        return paymentService.update(id, payment);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        paymentService.delete(id);

        return "Payment deleted successfully";
    }
}