package fr.dawan.training.repositories;

import java.util.List;
import java.util.Map;

import fr.dawan.training.entities.Product;

public interface ICustomRepo {

	List<Product> searchBy(Map<String, String> params);
}
