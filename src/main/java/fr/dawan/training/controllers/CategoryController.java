package fr.dawan.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.services.ICategoryService;
import fr.dawan.training.services.IEmailService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController extends GenericController<CategoryDto, Long> {

	private ICategoryService service;
	
	@Autowired
	private IEmailService emailService;
	
	@Autowired
	private CacheManager cacheManager;
	
	
	@Autowired
	public CategoryController(ICategoryService service) {
		super(service);
		this.service = service;
	}

	@Override
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws Exception {
		emailService.sendHtmlEmail("Suppression Category", "attention, xxx a supprimé la catégorie XX", "mderkaoui@dawan.fr");
		return super.deleteById(id);
	}

	@Override
	public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto p) throws Exception {
		
		ResponseEntity<CategoryDto> result = super.save(p);
		
		//supprimer une entrée
		cacheManager.getCache("test").evict("#root.findAllByNameContaining");
		
		//supprime toutes les clés du cache
		//cacheManager.getCache("test").invalidate();
		
		return result;
	}

	//Implement other methods
	

	
	
	
}
