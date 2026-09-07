package com.example.demo.dtos;

import com.example.demo.entities.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

    private Long id;

    @NotBlank(message = "Document title is required")
    @Size(max = 150, message = "Document title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Document type is required")
    @Size(max = 50, message = "Document type must not exceed 50 characters")
    private String type;

    @NotNull(message = "Upload date is required")
    private LocalDate uploadDate;

    private Long applicationId;

    private Long projectId;

    public static DocumentDTO convertToDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .title(document.getTitle())
                .type(document.getType())
                .uploadDate(document.getUploadDate())
                .applicationId(
                        document.getApplication() != null
                                ? document.getApplication().getId()
                                : null
                )
                .projectId(
                        document.getProject() != null
                                ? document.getProject().getId()
                                : null
                )
                .build();
    }

    public static List<DocumentDTO> convertToDTO(List<Document> documents) {
        return documents.stream()
                .map(DocumentDTO::convertToDTO)
                .toList();
    }
}