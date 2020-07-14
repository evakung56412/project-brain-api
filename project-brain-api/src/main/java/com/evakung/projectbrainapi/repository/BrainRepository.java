package com.evakung.projectbrainapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evakung.projectbrainapi.model.Brain;

@Repository
public interface BrainRepository extends JpaRepository<Brain, Long>{
	
	Optional<Brain> findOneByUsername(String username);
	Optional<Brain> findOneByEmail(String email);
	Optional<Brain> findOneByEmailAndPassword(String email, String password);

}
