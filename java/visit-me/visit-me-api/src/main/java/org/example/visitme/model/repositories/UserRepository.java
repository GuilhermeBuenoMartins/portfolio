package org.example.visitme.model.repositories;

import org.example.visitme.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByCpf(String cpf);

}
