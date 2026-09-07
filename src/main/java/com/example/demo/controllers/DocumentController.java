package com.example.demo.controllers;

import com.example.demo.dtos.DocumentDTO;
import com.example.demo.services.DocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(
            DocumentService documentService) {

        this.documentService = documentService;
    }

    @PostMapping("/add")
    public DocumentDTO add(
            @Valid @RequestBody DocumentDTO dto) {

        return documentService.add(dto);
    }

    @GetMapping("/getAll")
    public List<DocumentDTO> getAll() {
        return documentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public DocumentDTO getById(
            @PathVariable Long id) {

        return documentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public DocumentDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DocumentDTO dto) {

        return documentService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        documentService.delete(id);

        return "Document deleted successfully";
    }
}