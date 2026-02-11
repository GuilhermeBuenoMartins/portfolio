package org.example.visitme.model.repositories;

import org.example.visitme.model.entities.TokenBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklistEntity, Integer> {

    boolean existsByToken(String token);
    
}
