package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.ProductDto;
import fr.dawan.training.entities.Category;
import fr.dawan.training.entities.Product;
import fr.dawan.training.entities.Supplier;
import fr.dawan.training.repositories.CategoryRepository;
import fr.dawan.training.repositories.ProductRepository;
import fr.dawan.training.repositories.SupplierRepository;
import fr.dawan.training.tools.DtoTools;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl extends GenericServiceImpl<ProductDto, Product, Long> implements IProductService {

	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		super(productRepository, ProductDto.class, Product.class);
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductDto> getAllBy(int page, int size, String description) throws Exception {
		List<ProductDto> result = new ArrayList<>();

		List<Product> products = productRepository
				.findAllByDescriptionContaining(description, PageRequest.of(page, size)).getContent();

		for (Product p : products) {
			result.add(DtoTools.convert(p, ProductDto.class));
		}
		return result;
	}

	@Override
	public LongDto countBy(String description) throws Exception {
		long nb = productRepository.countByDescriptionContaining(description);
		LongDto result = new LongDto();
		result.setResult(nb);
		return result;
	}
	

	@Override
	public ProductDto saveOrUpdate(ProductDto pDto) throws Exception {
		Product prod = DtoTools.convert(pDto, Product.class);

		// category association because we have only the categoryId
		Optional<Category> optC = categoryRepository.findById(pDto.getCategoryId());
		if (optC.isPresent())
			prod.setCategory(optC.get());
		else
			throw new IllegalArgumentException("Category ID not found !");
		
		//suppliers
		if (pDto.getSuppliersIds() != null) {
			for (long id : pDto.getSuppliersIds() ) {
				Optional<Supplier> suppOpt = supplierRepository.findById(id);
				if (suppOpt.isPresent()) {
					suppOpt.get().getProducts().add(prod);
				}
			}
		}
		
		if(prod.getSuppliers()!=null)
			prod.getSuppliers().remove(null);
		
		prod = productRepository.saveAndFlush(prod);

		ProductDto dto =  DtoTools.convert(prod, ProductDto.class);
		dto.setSuppliersIds(pDto.getSuppliersIds());
		return dto;
	}
	
	@Override
	public void deleteById(Long id) throws Exception {
		Optional<Product> opt = productRepository.findById(id);
		if(opt.isPresent()) {
			Product p = opt.get();
			p.setSuppliers(null);
			productRepository.delete(p);
		}
	}

}
