package fr.dawan.training.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.training.dto.LogMsg;

@RestController
@RequestMapping("/api/logs")
public class LogController {

	@Value("${storage.folder}") // Value permet de récupérer la valeur d'une propriété
	private String storageFolder;

	@Autowired // on récupère l'objetMapper de Jackson qui nous permet de convertir json/objet
				// et inversement
	private ObjectMapper objectMapper;

	private static Logger myRootLogger = LoggerFactory.getLogger(TestControllerOld.class);// root
	//private static Logger myLogger2 = LoggerFactory.getLogger("accessLogger");

	// méthode pour récupérer le fichier les logs du serveur
	// requête HTTP /api/logs/app.log ===============> service web / méthode
	// getLogFile
	// <==== Header : CONTENT-DISPOSITION: attachment, filename

	/**
	 * Retreive a log file from backend
	 * 
	 * @param filename file name
	 * @return octets-stream
	 * @throws Exception if file not found or error on read
	 */
	@GetMapping(value = "/{filename}", produces = "application/octet-stream")
	public ResponseEntity<Resource> getLogFile(@PathVariable("filename") String filename) throws Exception {

		// il faut lire le fichier qui se trouve dans le répertoire storageFolder
		File f = new File(storageFolder + "/" + filename);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(f.getAbsolutePath())));

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
		headers.add("Cache-Control", "no-cache, no-store, must-validate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		// retourner le résultat
		return ResponseEntity.ok().headers(headers).contentLength(f.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	// méthode pour insérer un log dans le fichier
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> postLog(@RequestBody LogMsg msg) throws Exception {
		String logStr = objectMapper.writeValueAsString(msg);
		// msg.getType() si on veut jouer avec un logger
		switch (msg.getLevel()) {
		case INFO:
			myRootLogger.info(logStr);
			break;
		case WARN:
			myRootLogger.warn(logStr);
			break;
		case ERROR:
			myRootLogger.error(logStr);
			break;
		default:
			break;

		}

		return ResponseEntity.ok().build();
	}

}
