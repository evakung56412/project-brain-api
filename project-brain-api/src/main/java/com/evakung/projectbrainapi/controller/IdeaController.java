package com.evakung.projectbrainapi.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evakung.projectbrainapi.form.IdeaForm;
import com.evakung.projectbrainapi.model.Idea;
import com.evakung.projectbrainapi.repository.IdeaRepository;

@RestController
public class IdeaController {

	private final IdeaRepository ideaRepository;

	@Autowired
	public IdeaController(IdeaRepository ideaRepository) {
		this.ideaRepository = ideaRepository;
	}
	
	@GetMapping("/ideas")
	public Collection<Idea> findAll() {
		return ideaRepository.findAll();
	}
	
	@GetMapping("idea")
	public Idea findOne(@RequestParam String title) {
		return ideaRepository.findOneaByTitle(title).orElseThrow();
	}
	
	@PostMapping("/ideas")
	public Idea save(@RequestBody IdeaForm ideaForm) {
		Idea idea = new Idea();
		idea.setTitle(ideaForm.getTitle());
		idea.setContext(ideaForm.getContext());
		idea.setContent(ideaForm.getContent());
		return ideaRepository.save(idea);
	}
	
	@PostMapping("/idea/remove")
	public String remove(@RequestBody IdeaForm ideaForm) {
		Idea idea = ideaRepository.findOneaByTitle(ideaForm.getTitle()).orElseThrow();
		idea.getAuthor().getIdeas().remove(idea);
		idea.setAuthor(null);
		
		ideaRepository.save(idea);
		ideaRepository.delete(idea);
		
		return "Delete Successfully!";
	}
}
