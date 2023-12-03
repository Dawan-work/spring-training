package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.ProductDto;

public interface IProductService extends IGenericService<ProductDto, Long> {
	
    //other methods
	List<ProductDto> getAllBy(int page, int size, String description) throws Exception;

	LongDto countBy(String description) throws Exception;
}
