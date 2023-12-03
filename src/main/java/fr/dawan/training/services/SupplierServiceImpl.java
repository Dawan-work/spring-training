package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.training.dto.LongDto;
import fr.dawan.training.dto.SupplierDto;
import fr.dawan.training.entities.Product;
import fr.dawan.training.entities.Supplier;
import fr.dawan.training.repositories.ProductRepository;
import fr.dawan.training.repositories.SupplierRepository;
import fr.dawan.training.tools.DtoTools;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SupplierServiceImpl extends GenericServiceImpl<SupplierDto, Supplier, Long> implements ISupplierService {

	private SupplierRepository supplierRepository;

	@Autowired  
	private ProductRepository productRepository;

	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository) {
		super(supplierRepository, SupplierDto.class, Supplier.class);
		this.supplierRepository = supplierRepository;
	}

	@Override
	public List<SupplierDto> getAllBy(int page, int size, String name) throws Exception {
		List<SupplierDto> result = new ArrayList<>();

		List<Supplier> suppliers = supplierRepository.findAllByNameContaining(name, PageRequest.of(page, size))
				.getContent();

		for (Supplier s : suppliers) {
			result.add(DtoTools.convert(s, SupplierDto.class));
		}
		return result;
	}

	@Override
	public LongDto countBy(String name) throws Exception {
		long nb = supplierRepository.countByNameContaining(name);
		LongDto result = new LongDto();
		result.setResult(nb);
		return result;
	}

	@Override
	public SupplierDto saveOrUpdate(SupplierDto sDto) throws Exception {
		Supplier supplier = DtoTools.convert(sDto, Supplier.class);

		// if update
		if (supplier.getId() > 0) {
			Optional<Supplier> suppInDb = supplierRepository.findById(sDto.getId());
			if (suppInDb.isPresent()) {
				suppInDb.get().setProducts(new HashSet<>());
			}
		}

		// productsIds to products. Populate the List<Product>
		if (sDto.getProductsIds() != null) {
			for (long id : sDto.getProductsIds()) {
				Optional<Product> prodOpt = productRepository.findById(id);
				if (prodOpt.isPresent()) {
					supplier.getProducts().add(prodOpt.get());
				}
			}
		}
		
		if(supplier.getProducts()!=null)
			supplier.getProducts().remove(null);
		
		supplier = supplierRepository.saveAndFlush(supplier);
		return DtoTools.convert(supplier, SupplierDto.class);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		Optional<Supplier> suppOpt = supplierRepository.findById(id);
		if(suppOpt.isPresent()) {
			Supplier supp = suppOpt.get();
			supp.setProducts(null);
			supplierRepository.delete(supp);
		}
	}
			
	
	

}
