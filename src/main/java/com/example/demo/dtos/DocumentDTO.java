package com.example.demo.dtos;

import com.example.demo.entities.Document;
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
    private String title;
    private String type;
    private LocalDate uploadDate;

    private Long applicationId;
    private Long projectId;

    public static DocumentDTO convertToDTO(
            Document document) {

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

    public static List<DocumentDTO> convertToDTO(
            List<Document> documents) {

        return documents.stream()
                .map(DocumentDTO::convertToDTO)
                .toList();
    }
}