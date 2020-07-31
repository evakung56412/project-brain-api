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

import com.evakung.projectbrainapi.form.BrainForm;
import com.evakung.projectbrainapi.form.BrainUpdateForm;
import com.evakung.projectbrainapi.form.LoginForm;
import com.evakung.projectbrainapi.form.BrainFollwersForm;
import com.evakung.projectbrainapi.model.Brain;
import com.evakung.projectbrainapi.model.Idea;
import com.evakung.projectbrainapi.repository.BrainRepository;

@RestController
public class BrainController {

	private final BrainRepository brainRepository;

	@Autowired
	public BrainController(BrainRepository brainRepository) {
		this.brainRepository = brainRepository;
	}
	
	@GetMapping("/brains")
	public Collection<Brain> findAll() {
		return brainRepository.findAll();
	}
	
	@GetMapping("/brain")
	public Brain findOneByUsername(@RequestParam String username) {
		return brainRepository.findOneByUsername(username).orElseThrow();
	}

	@PostMapping("/brain/register")
	public Brain register(@RequestBody BrainForm brainForm) {
		Brain brain = new Brain();
		brain.setEmail(brainForm.getEmail());
		brain.setPassword(brainForm.getPassword());
		brain.setUsername(brainForm.getUsername());
		brain.setFirstName(brainForm.getFirstName());
		brain.setLastName(brainForm.getLastName());
		
		brain.setIdeas(new HashSet<>());
		brain.setFollowers(new HashSet<>());
		brain.setTodos(new HashSet<>());
		
		return brainRepository.save(brain);
		
	}
	
	@PostMapping("/brain/login")
	public Brain login(@RequestBody LoginForm loginForm) {
		return brainRepository.findOneByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword()).orElseThrow();
	}
	
	@GetMapping("/brain/{username}/ideas")
	public Set<Idea> getIdeasForBrain(@PathVariable String username) {
		try {
			return brainRepository.findOneByUsername(username).orElseThrow().getIdeas();
		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<>();
		}
	}
	
	@GetMapping("/brain/{username}/followers")
	public Set<Brain> getFollwersForBrain(@PathVariable String username) {
		try {
			return brainRepository.findOneByUsername(username).orElseThrow().getFollowers();
		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<>();
		}
	}
	
	@GetMapping("/brain/{username}/todos")
	public Set<Idea> getTodosForBrain(@PathVariable String username) {
		try {
			return brainRepository.findOneByUsername(username).orElseThrow().getTodos();
		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<>();
		}
	}
	
	@PostMapping("/brain/follow")
	public Brain follow(@RequestBody BrainFollwersForm brainFollwersForm) {
		Brain followerBrain = brainRepository.findOneByUsername(brainFollwersForm.getFollowerUsername()).orElseThrow();
		Brain followedBrain = brainRepository.findOneByUsername(brainFollwersForm.getFollowedUsername()).orElseThrow();
		
		followerBrain.getFollowers().add(followedBrain);
		
		return brainRepository.save(followerBrain);
	}
	
	@PostMapping("/brain/update")
	public Brain update(@RequestBody BrainUpdateForm brainUpdateForm) {
		Brain brain = brainRepository.findOneByUsername(brainUpdateForm.getUsername()).orElseThrow();
		
		brain.setPassword(brainUpdateForm.getPassword());
		brain.setFirstName(brainUpdateForm.getFirstName());
		brain.setLastName(brainUpdateForm.getLastName());
		
		return brainRepository.save(brain);
	}
	
}
