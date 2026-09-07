package com.example.demo.services;

import com.example.demo.dtos.OperatorDTO;
import com.example.demo.entities.Operator;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.OperatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class  OperatorService {

    // Manages telecom operator records used by licenses, inspections, and complaints.
    private final OperatorRepository operatorRepository;

    public OperatorService(
            OperatorRepository operatorRepository) {

        this.operatorRepository = operatorRepository;
    }

    public OperatorDTO add(OperatorDTO dto) {

        // Operator creation maps DTO contact and licensing fields into a new entity.
        Operator operator = new Operator();

        operator.setName(dto.getName());
        operator.setLicenseNumber(dto.getLicenseNumber());
        operator.setContactEmail(dto.getContactEmail());
        operator.setCountry(dto.getCountry());
        operator.setIsActive(true);
        operator.setCreatedDate(LocalDateTime.now());
        operator.setUpdatedDate(LocalDateTime.now());

        return OperatorDTO.convertToDTO(
                operatorRepository.save(operator)
        );
    }

    public List<OperatorDTO> getAll() {

        List<Operator> operators =
                operatorRepository.findAll()
                        .stream()
                        .filter(operator ->
                                Boolean.TRUE.equals(
                                        operator.getIsActive()
                                )
                        )
                        .toList();

        return OperatorDTO.convertToDTO(operators);
    }

    public OperatorDTO getById(Long id) {

        return OperatorDTO.convertToDTO(
                findOperatorById(id)
        );
    }

    public OperatorDTO update(
            Long id,
            OperatorDTO dto) {

        Operator operator =
                findOperatorById(id);

        operator.setName(dto.getName());
        operator.setLicenseNumber(dto.getLicenseNumber());
        operator.setContactEmail(dto.getContactEmail());
        operator.setCountry(dto.getCountry());
        operator.setUpdatedDate(LocalDateTime.now());

        return OperatorDTO.convertToDTO(
                operatorRepository.save(operator)
        );
    }

    public void delete(Long id) {

        Operator operator =
                findOperatorById(id);

        // Soft delete disables the operator without removing related historical records.
        operator.setIsActive(false);
        operator.setUpdatedDate(LocalDateTime.now());

        operatorRepository.save(operator);
    }

    private Operator findOperatorById(Long id) {

        Operator operator =
                operatorRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Operator not found with id: " + id
                                )
                        );

        if (!Boolean.TRUE.equals(
                operator.getIsActive())) {

            throw new ResourceNotFoundException(
                    "Operator not found with id: " + id
            );
        }

        return operator;
    }
}
