package com.example.demo.controllers;

import com.example.demo.entities.Document;
import com.example.demo.services.DocumentService;
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
    public Document add(
            @RequestBody Document document) {

        return documentService.add(document);
    }

    @GetMapping("/getAll")
    public List<Document> getAll() {

        return documentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Document getById(
            @PathVariable Long id) {

        return documentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public Document update(
            @PathVariable Long id,
            @RequestBody Document document) {

        return documentService.update(id, document);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        documentService.delete(id);

        return "Document deleted successfully";
    }
}
