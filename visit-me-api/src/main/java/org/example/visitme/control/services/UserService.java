package org.example.visitme.control.services;

import java.util.List;

import org.example.visitme.control.dto.UserDto;
import org.example.visitme.model.entities.PhoneEntity;
import org.example.visitme.model.entities.UserEntity;
import org.example.visitme.model.repositories.UserRepository;
import org.example.visitme.utils.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public boolean hasCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    public UserDto insert(UserDto dto) {
        UserEntity entity = (UserEntity) ConverterUtil.from(dto, UserEntity.class);
        entity.getLogin().setActived(true);
        List<PhoneEntity> phones = entity.getPhones();
        phones.forEach(phone -> phone.setUser(entity));
        repository.save(entity);
        return (UserDto) ConverterUtil.from(entity, UserDto.class);
    }

}
