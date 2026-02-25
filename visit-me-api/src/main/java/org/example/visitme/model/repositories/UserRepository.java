package org.example.visitme.model.repositories;

import java.util.Optional;

import org.example.visitme.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByCpf(String cpf);

    Optional<UserEntity> findByCpf(String cpf);
}
