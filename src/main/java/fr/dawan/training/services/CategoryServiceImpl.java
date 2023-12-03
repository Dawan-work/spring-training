package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.CategoryDto;
import fr.dawan.training.dto.LongDto;
import fr.dawan.training.entities.Category;
import fr.dawan.training.repositories.CategoryRepository;
import fr.dawan.training.tools.DtoTools;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryServiceImpl extends GenericServiceImpl<CategoryDto, Category, Long> implements ICategoryService {

	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super(categoryRepository, CategoryDto.class, Category.class);
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDto> getAllBy(int page, int size, String name) throws Exception {
		List<CategoryDto> result = new ArrayList<>();

		List<Category> categories = categoryRepository
				.findAllByNameContaining(name, PageRequest.of(page, size)).getContent();

		for (Category p : categories) {
			result.add(DtoTools.convert(p, CategoryDto.class));
		}
		return result;
	}

	@Override
	public LongDto countBy(String name) throws Exception {
		long nb = categoryRepository.countByNameContaining(name);
		LongDto result = new LongDto();
		result.setResult(nb);
		return result;
	}

	

}
