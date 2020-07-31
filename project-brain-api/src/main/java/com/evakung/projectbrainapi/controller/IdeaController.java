package com.evakung.projectbrainapi.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evakung.projectbrainapi.form.IdeaForm;
import com.evakung.projectbrainapi.form.RemoveIdeaForm;
import com.evakung.projectbrainapi.model.Brain;
import com.evakung.projectbrainapi.model.Idea;
import com.evakung.projectbrainapi.repository.BrainRepository;
import com.evakung.projectbrainapi.repository.IdeaRepository;

@RestController
public class IdeaController {

	private final BrainRepository brainRepository;
	private final IdeaRepository ideaRepository;

	@Autowired
	public IdeaController(BrainRepository brainRepository, IdeaRepository ideaRepository) {
		this.brainRepository = brainRepository;
		this.ideaRepository = ideaRepository;
	}
	
	@GetMapping("/ideas")
	public Collection<Idea> findAll() {
		return ideaRepository.findAll();
	}
	
	@PostMapping("/ideas")
	public Idea save(@RequestBody IdeaForm ideaForm) {
		Brain brain = brainRepository.findOneByUsername(ideaForm.getUsername()).orElseThrow();
		
		Idea idea = new Idea();
		idea.setCiteId(ideaForm.getCiteId());
		idea.setTitle(ideaForm.getTitle());
		idea.setContext(ideaForm.getContext());
		idea.setContent(ideaForm.getContent());
		idea.setAuthor(brain);
		return ideaRepository.save(idea);
	}
	
	@PostMapping("/idea/remove")
	public String remove(@RequestBody RemoveIdeaForm ideaForm) {
		Idea idea = ideaRepository.findOneByTitle(ideaForm.getTitle()).orElseThrow();
		idea.getAuthor().getIdeas().remove(idea);
		idea.setAuthor(null);
		
		ideaRepository.save(idea);
		ideaRepository.delete(idea);
		
		return "Delete Successfully!";
	}
	
	@GetMapping("/idea/{citeId}")
	public Idea getOriginalIdeaByCiteId(@PathVariable String citeId) {
		try {
			return ideaRepository.findById(Long.valueOf(citeId)).orElseThrow();
		} catch (Exception e) {
			e.printStackTrace();
			return new Idea();
		}
	}
}
