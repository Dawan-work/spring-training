package fr.dawan.training.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements IFileService {

	@Value("${storage.folder}") // Value permet de récupérer la valeur d'une propriété
	private String storageFolder;
	
	@Override
	public void saveFile(String filePath, byte[] bytes) throws Exception {
		filePath = filePath.replaceAll(" ", "-"); // remplacer les espaces par tiret
		filePath = filePath.replaceAll("[^\\./A-Za-z0-9_]", ""); // remplacer les caractères non alphanumériques par rien
		// regex : \w : a word character => [a-zA-Z_0-9]
		// \W : a non-word character
		File f = new File(storageFolder + filePath);
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f))) {
			bos.write(bytes);
		}
	}
}
