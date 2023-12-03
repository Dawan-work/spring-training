package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.dto.LongDto;

public interface ICategoryService extends IGenericService<CategoryDto, Long> {
	
    List<CategoryDto> getAllBy(int page, int size, String search) throws Exception;

	LongDto countBy(String name) throws Exception;
	
}
