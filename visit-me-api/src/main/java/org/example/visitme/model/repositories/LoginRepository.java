package org.example.visitme.model.repositories;

import org.example.visitme.model.entities.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Integer> {

    boolean existsByUsername(String username);
    
}
