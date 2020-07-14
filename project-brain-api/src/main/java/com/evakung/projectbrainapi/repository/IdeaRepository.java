package com.evakung.projectbrainapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evakung.projectbrainapi.model.Idea;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long>{

	Optional<Idea> findOneaByTitle(String title);
}
