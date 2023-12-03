package fr.dawan.training.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.UserDto;
import fr.dawan.training.services.IUserService;

@RestController
@RequestMapping("/api/users")
@PreAuthorize(value = "hasAuthority('ADMIN')")
public class UserController extends GenericController<UserDto, Long> {

	private IUserService service;
	
	@Autowired
	public UserController(IUserService service) {
		super(service);
		this.service = service;
	}


	//@PreAuthorize(value = "hasAuthority('ADMIN')") //on method or on class
	@Override
	public LongDto countBy(Optional<String> searchOpt) throws Exception {
		return super.countBy(searchOpt);
	}

	@Override
	public List<UserDto> getAllBy(@PathVariable("page") int page, @PathVariable("size") int size,
			@PathVariable(value = "search", required = false) Optional<String> searchOpt) throws Exception {
		return super.getAllBy(page, size, searchOpt);
	}

	@Override
	public UserDto getById(@PathVariable("id") Long id) throws Exception {
		return super.getById(id);
	}

	@Override
	public ResponseEntity<UserDto> save(@RequestBody UserDto p) throws Exception {
		return super.save(p);
	}

	@Override
	public UserDto update(@RequestBody UserDto p) throws Exception {
		return super.update(p);
	}
	
	@PreAuthorize(value = "hasAuthority('SUPER_ADMIN')") //@PreAuthorize(value = "hasAuthority('role1') and hasAuthority('role2')")
	@Override
	public ResponseEntity<Void> deleteById(Long id) throws Exception {
		return super.deleteById(id);
	}
}
