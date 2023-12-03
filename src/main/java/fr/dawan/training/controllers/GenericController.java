package fr.dawan.training.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.services.IGenericService;

public class GenericController<TDto, ID> {

	private IGenericService<TDto, ID> service;

	public GenericController(IGenericService<TDto, ID> service) {
		this.service = service;
	}
	
	
	// lister avec pagination
	@GetMapping(value = { "/{page}/{size}", "/{page}/{size}/{search}" }, produces = "application/json")
	public List<TDto> getAllBy(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable(value = "search", required = false) Optional<String> searchOpt) throws Exception {
		if (searchOpt.isPresent())
			return service.getAllBy(page - 1, size, searchOpt.get());
		else
			return service.getAllBy(page - 1, size, "");
	}

	// récupérer le nombre
	@GetMapping(value = { "/count", "/count/{search}" }, produces = "application/json")
	public LongDto countBy(@PathVariable(value = "search", required = false) Optional<String> searchOpt)
			throws Exception {
		if (searchOpt.isPresent())
			return service.countBy(searchOpt.get());
		else
			return service.countBy("");
	}

	// récupérer par id
	@GetMapping(value = "/{id}", produces = "application/json")
	public TDto getById(@PathVariable("id") ID id) throws Exception {
		return service.getById(id);
	}

	// ajouter
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<TDto> save(@RequestBody TDto p) throws Exception {
		TDto result = service.saveOrUpdate(p);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	// modifier
	@PutMapping(consumes = "application/json", produces = "application/json")
	public TDto update(@RequestBody TDto p) throws Exception {
		return service.saveOrUpdate(p);
	}

	// supprimer
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteById(@PathVariable("id") ID id) throws Exception {
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// download/upload
}
