package fr.dawan.training.services;

import java.util.List;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.SupplierDto;

public interface ISupplierService extends IGenericService<SupplierDto, Long> {
	
    //other methods
	List<SupplierDto> getAllBy(int page, int size, String name) throws Exception;

	LongDto countBy(String name) throws Exception;
}
