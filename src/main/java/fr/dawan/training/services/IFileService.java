package fr.dawan.training.services;

public interface IFileService {

	void saveFile(String filePath, byte[] bytes) throws Exception;
}
