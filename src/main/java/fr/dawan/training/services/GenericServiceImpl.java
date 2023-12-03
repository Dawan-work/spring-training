package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.tools.DtoTools;


public class GenericServiceImpl<TDto,T, ID> implements IGenericService<TDto, ID> {

	protected JpaRepository<T, ID> repository;
	protected Class<TDto> dtoClazz;
	protected Class<T> clazz;
	
	public GenericServiceImpl(JpaRepository<T, ID> repository, Class<TDto> dtoClazz, Class<T> clazz) {
		this.repository = repository;
		this.dtoClazz = dtoClazz;
		this.clazz = clazz;
	}
	
	@Override
	public List<TDto> getAllBy(int page, int size) throws Exception {
		List<TDto> result = new ArrayList<>();
		List<T> objects =  repository.findAll(PageRequest.of(page, size)).getContent();
		
		for(T obj : objects) {
			result.add(DtoTools.convert(obj, dtoClazz));
		}
		return result;
	}
	
	@Override
	public List<TDto> getAllBy(int page, int size, String search) throws Exception {
		if(search==null || search.trim().equals(""))
			return getAllBy(page, size);
		else
			throw new IllegalArgumentException("You should implement search method");
	}

	@Override
	public TDto getById(ID id) throws Exception {
		Optional<T> opt = repository.findById(id);
		if(opt.isPresent())
			return DtoTools.convert(opt.get(), dtoClazz);
		
		return null;
	}

	@Override
	public TDto saveOrUpdate(TDto obj) throws Exception {
		T o = DtoTools.convert(obj, clazz);
		o = repository.saveAndFlush(o);
		return DtoTools.convert(o, dtoClazz);
	}

	@Override
	public void deleteById(ID id) throws Exception {
		repository.deleteById(id);
	}

	@Override
	public LongDto countBy() throws Exception {
		LongDto nb = new LongDto();
		nb.setResult(repository.count());
		return nb;
	}
	
	@Override
	public LongDto countBy(String search) throws Exception{
		if(search==null || search.trim().equals(""))
			return countBy();
		else
			throw new IllegalArgumentException("You should implement search method");
	}

	
	
}
