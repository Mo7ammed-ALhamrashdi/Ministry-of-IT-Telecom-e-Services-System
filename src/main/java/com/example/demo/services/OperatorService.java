package com.example.demo.services;

import com.example.demo.entities.Operator;
import com.example.demo.repositories.OperatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperatorService {

    private final OperatorRepository operatorRepository;

    public OperatorService(
            OperatorRepository operatorRepository) {

        this.operatorRepository = operatorRepository;
    }

    public Operator add(Operator operator) {

        operator.setId(null);
        operator.setIsActive(true);
        operator.setCreatedDate(LocalDateTime.now());
        operator.setUpdatedDate(LocalDateTime.now());

        return operatorRepository.save(operator);
    }

    public List<Operator> getAll() {

        return operatorRepository.findAll()
                .stream()
                .filter(operator ->
                        Boolean.TRUE.equals(
                                operator.getIsActive()))
                .toList();
    }

    public Operator getById(Long id) {

        Operator operator =
                operatorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Operator not found"));

        if (!Boolean.TRUE.equals(operator.getIsActive())) {
            throw new RuntimeException("Operator not found");
        }

        return operator;
    }

    public Operator update(
            Long id,
            Operator request) {

        Operator operator = getById(id);

        operator.setName(request.getName());
        operator.setLicenseNumber(
                request.getLicenseNumber());
        operator.setContactEmail(
                request.getContactEmail());
        operator.setCountry(request.getCountry());
        operator.setUpdatedDate(LocalDateTime.now());

        return operatorRepository.save(operator);
    }

    public void delete(Long id) {

        Operator operator = getById(id);

        operator.setIsActive(false);
        operator.setUpdatedDate(LocalDateTime.now());

        operatorRepository.save(operator);
    }
}