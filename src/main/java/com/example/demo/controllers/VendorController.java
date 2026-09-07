package com.example.demo.controllers;

import com.example.demo.entities.Vendor;
import com.example.demo.services.VendorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(
            VendorService vendorService) {

        this.vendorService = vendorService;
    }

    @PostMapping("/add")
    public Vendor add(@RequestBody Vendor vendor) {

        return vendorService.add(vendor);
    }

    @GetMapping("/getAll")
    public List<Vendor> getAll() {

        return vendorService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Vendor getById(@PathVariable Long id) {

        return vendorService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Vendor update(
            @PathVariable Long id,
            @RequestBody Vendor vendor) {

        return vendorService.update(id, vendor);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        vendorService.delete(id);

        return "Vendor deleted successfully";
    }
}