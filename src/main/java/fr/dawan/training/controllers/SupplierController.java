package fr.dawan.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.SupplierDto;
import fr.dawan.training.services.ISupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController extends GenericController<SupplierDto, Long> {

	private ISupplierService service;
	
	@Autowired
	public SupplierController(ISupplierService service) {
		super(service);
		this.service = service;
	}

	//Implement other methods
	

	
	
	
}
