package com.example.demo.controllers;

import com.example.demo.dtos.PaymentDTO;
import com.example.demo.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
// Exposes payment endpoints for application-related payment records.
public class PaymentController {

    // PaymentService contains payment logic and shields the controller from persistence details.
    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    // The POST route creates a payment from a validated PaymentDTO request body.
    @PostMapping("/add")
    public PaymentDTO add(
            @Valid @RequestBody PaymentDTO dto) {

        return paymentService.add(dto);
    }

    @GetMapping("/getAll")
    public List<PaymentDTO> getAll() {
        return paymentService.getAll();
    }

    // The path variable id tells the service which payment to retrieve.
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

    // The delete endpoint delegates payment removal to the service layer.
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        paymentService.delete(id);

        return "Payment deleted successfully";
    }
}
