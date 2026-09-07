package com.example.demo.controllers;

import com.example.demo.dtos.PaymentDTO;
import com.example.demo.services.PaymentService;
import jakarta.validation.Valid;
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
    public PaymentDTO add(
            @Valid @RequestBody PaymentDTO dto) {

        return paymentService.add(dto);
    }

    @GetMapping("/getAll")
    public List<PaymentDTO> getAll() {
        return paymentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public PaymentDTO getById(
            @PathVariable Long id) {

        return paymentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public PaymentDTO update(
            @PathVariable Long id,
            @Valid @RequestBody PaymentDTO dto) {

         return paymentService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        paymentService.delete(id);

        return "Payment deleted successfully";
    }
}