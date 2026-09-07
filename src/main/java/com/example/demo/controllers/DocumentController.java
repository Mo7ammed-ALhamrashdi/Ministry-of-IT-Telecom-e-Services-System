package com.example.demo.controllers;

import com.example.demo.dtos.DocumentDTO;
import com.example.demo.services.DocumentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document")
// Provides endpoints for managing documents attached to ministry processes.
public class DocumentController {

    // DocumentService is injected so document rules stay centralized in the service layer.
    private final DocumentService documentService;

    public DocumentController(
            DocumentService documentService) {

        this.documentService = documentService;
    }

    // The POST endpoint receives document metadata as a DTO and delegates creation.
    @PostMapping("/add")
    public DocumentDTO add(
            @Valid @RequestBody DocumentDTO dto) {

        return documentService.add(dto);
    }

    // @RestController makes the returned list automatically serialize as JSON.
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

        return  documentService.update(id, dto);
    }

    // DELETE maps the HTTP delete request to the service's document removal operation.
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        documentService.delete(id);

        // The text response confirms the endpoint completed the service call.
        return "Document deleted successfully";
    }
}
