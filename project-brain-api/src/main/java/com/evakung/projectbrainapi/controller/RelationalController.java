package com.evakung.projectbrainapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evakung.projectbrainapi.form.NewIdeaForm;
import com.evakung.projectbrainapi.form.TodoForm;
import com.evakung.projectbrainapi.model.Brain;
import com.evakung.projectbrainapi.model.Idea;
import com.evakung.projectbrainapi.repository.BrainRepository;
import com.evakung.projectbrainapi.repository.IdeaRepository;

@RestController
public class RelationalController {

	private final BrainRepository brainRepository;
	private final IdeaRepository ideaRepository;
	
	@Autowired
	public RelationalController(BrainRepository brainRepository, IdeaRepository ideaRepository) {
		this.brainRepository = brainRepository;
		this.ideaRepository = ideaRepository;
	}
	
	@PostMapping("/assign/idea")
	public Idea assignIdeaToBrain(@RequestBody NewIdeaForm newIdeaForm) {
		
		try {
			Brain brain = brainRepository.findOneByUsername(newIdeaForm.getUsername()).orElseThrow();
			
			Idea idea = new Idea();
			idea.setTitle(newIdeaForm.getTitle());
			idea.setContext(newIdeaForm.getContext());
			idea.setContent(newIdeaForm.getContent());
			idea.setAuthor(brain);
			
			ideaRepository.save(idea);

			brain.getIdeas().add(idea);
			brainRepository.save(brain);
			return idea;
		} catch (Exception e) {
			e.printStackTrace();
			return new Idea();
		}
	}
	
	@PostMapping("/assign/todo")
	public Brain assignTodoToBrain(@RequestBody TodoForm todoForm) {
		try {
			Idea todo = ideaRepository.findById(todoForm.getIdeaId()).orElseThrow();
			Brain brain = brainRepository.findOneByUsername(todoForm.getUsername()).orElseThrow();
			
			brain.getTodos().add(todo);
			brainRepository.save(brain);
			return brainRepository.save(brain);
		} catch (Exception e) {
			e.printStackTrace();
			return new Brain();
		}
	}
}
