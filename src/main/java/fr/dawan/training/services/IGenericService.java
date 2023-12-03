package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.LongDto;

/**
 * Generic interface for a service
 * @author mderk
 *
 * @param <T>	DTO Type
 */
public interface IGenericService<TDto, ID> {
	
    List<TDto> getAllBy(int page, int size) throws Exception;

    List<TDto> getAllBy(int page, int size, String search) throws Exception;
    
    TDto getById(ID id) throws Exception;

    TDto saveOrUpdate(TDto obj) throws Exception;

	void deleteById(ID id) throws Exception;

	LongDto countBy() throws Exception;
	
	LongDto countBy(String search) throws Exception;
	
}
