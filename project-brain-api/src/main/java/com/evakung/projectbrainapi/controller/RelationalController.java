package com.evakung.projectbrainapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evakung.projectbrainapi.form.IdeaForm;
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
	public Idea assignIdeaToBrain(@RequestBody IdeaForm newIdeaForm) {
		
		try {
			Brain brain = brainRepository.findOneByUsername(newIdeaForm.getUsername()).orElseThrow();
			
			Idea idea = new Idea();
			idea.setCiteId(newIdeaForm.getCiteId());
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
	public Idea assignTodoToBrain(@RequestBody IdeaForm todoForm) {
		try {
			Brain brain = brainRepository.findOneByUsername(todoForm.getUsername()).orElseThrow();
			
			Idea todo = new Idea();
			todo.setCiteId(todoForm.getCiteId());
			todo.setTitle(todoForm.getTitle());
			todo.setContext(todoForm.getContext());
			todo.setContent(todoForm.getContent());
			todo.setAuthor(brain);
			
			ideaRepository.save(todo);
			
			brain.getTodos().add(todo);
			brainRepository.save(brain);
			return todo;
		} catch (Exception e) {
			e.printStackTrace();
			return new Idea();
		}
	}
}
