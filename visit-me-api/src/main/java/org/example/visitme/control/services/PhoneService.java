package org.example.visitme.control.services;

import java.util.List;

import org.example.visitme.control.dto.PhoneDto;
import org.example.visitme.model.entities.PhoneEntity;
import org.example.visitme.model.repositories.PhoneRepository;
import org.example.visitme.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    
    @Autowired
    private PhoneRepository repository;

    public List<PhoneDto> insertAll(List<PhoneDto> dtos) {
        List<PhoneEntity> entities = (List<PhoneEntity>) ConverterUtil.from(dtos, PhoneEntity.class);
        entities = repository.saveAll(entities);
        return (List<PhoneDto>) ConverterUtil.from(entities, PhoneDto.class);
    }
}
