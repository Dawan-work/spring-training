package fr.dawan.training.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.ProductDto;
import fr.dawan.training.services.IProductService;

@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerOld2 {

	@Autowired 
	private IProductService productService;
	
	//lister avec pagination
	@GetMapping(value = {"/{page}/{size}","/{page}/{size}/{search}"}, produces = "application/json")
	public List<ProductDto> getAllBy(@PathVariable("page") int page, 
									@PathVariable("size") int size, 
									@PathVariable(value="search", required=false) Optional<String> searchOpt) throws Exception {
		if(searchOpt.isPresent())
			return productService.getAllBy(page-1, size, searchOpt.get());
		else
			return productService.getAllBy(page-1, size, "");
	}
	
	//récupérer le nombre
	@GetMapping(value = {"/count","/count/{search}"}, produces = "application/json")
	public LongDto countBy(@PathVariable(value="search", required=false) Optional<String> searchOpt) throws Exception {
		if(searchOpt.isPresent())
			return productService.countBy(searchOpt.get());
		else
			return productService.countBy("");
	}
		
	
	
	//récupérer par id
	@GetMapping(value="/{id}", produces="application/json")
	public ProductDto getById(@PathVariable("id") long id) throws Exception {
		return productService.getById(id);
	}
	
	
	//ajouter
	@PostMapping(consumes="application/json", produces = "application/json")
	public ResponseEntity<ProductDto> save(@RequestBody ProductDto p) throws Exception {
		ProductDto result = productService.saveOrUpdate(p);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	//modifier
	@PutMapping(consumes="application/json", produces = "application/json")
	public ProductDto update(@RequestBody ProductDto p) throws Exception {
		return productService.saveOrUpdate(p);
	}
	
	
	//supprimer
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteById(@PathVariable("id") long id) throws Exception {
		productService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	
	
	//download/upload
}
