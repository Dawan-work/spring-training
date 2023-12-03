package fr.dawan.training.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.exceptions.AuthenticateException;

/**
 * 
 * @author mderk
 *
 */
@RestController
@RequestMapping("/test")
public class TestControllerOld {

	private static Logger myRootLogger =  LoggerFactory.getLogger(TestControllerOld.class);//root
	//private static Logger myLogger2 =  LoggerFactory.getLogger("accessLogger");

	
	// /test/m1/1  <= Path param (paramètre d'url)
	//url + méthodeHTTP
	//@RequestMapping(method = RequestMethod.GET, value = "/test/m1")
	
//	@GetMapping(value = "/m1/{page}", produces = "text/plain")
//	public String m1(@PathVariable(value = "page") int page) {
//		return "m1"; //par défaut, si OK, on retourne un code 200
//	}
	
	//value peut prendre un tableau de String => plusieurs mappings (urls) vers une méthode
	@GetMapping(value = {"/m1", "/m1/{page}"} , produces = "text/plain")
	public String m1Opt(@PathVariable(value = "page", required = false) Optional<Integer> pageOpt) {
		
		myRootLogger.info("ceci est une information");
		
		if(pageOpt.isPresent()) {
			return "m1 : " + pageOpt.get();
		}else
			return "m1"; //par défaut, si OK, on retourne un code 200
	}
	
	
	
	//Request param : paramètres nommés : ?page=1
	/**
	 * m2
	 * @param page   number of the page requested
	 * @return 			result
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/m2", produces = "text/plain")
	public ResponseEntity<String> m2(@RequestParam("page") int page) {
		return  ResponseEntity
					.status(HttpStatus.CREATED)
					.contentType(MediaType.TEXT_PLAIN)
					.body("m2 créé");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/m3", consumes = "text/plain", produces = "text/plain")
	public @ResponseBody String m3(@RequestBody String message, @RequestHeader("User-Agent") String userAgent) {
		return "m3 : " + message + " : userAgent = "+userAgent;
	}
	
	
	@GetMapping(value="/exc1")
	public String testException() throws Exception {
		throw new AuthenticateException("Identifiants incorrects !");
	}
	
	
	
}
