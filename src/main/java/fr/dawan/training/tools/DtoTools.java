package fr.dawan.training.tools;

import org.modelmapper.ModelMapper;

public class DtoTools {

	private DtoTools() {
		
	}
	
	private static ModelMapper myMapper = new ModelMapper();

	/**
	 * Convert an object to an other type using ModelMapper
	 * @param <TSource>			Source Type
	 * @param <TDestination>	Destination Type
	 * @param obj				object to convert
	 * @param clazz				Destination type class
	 * @return					the converted object
	 * @throws Exception		if error in conversion
	 * {@code DtoTools.convert(p, ProductDto.class); }
	 */
	public static <TSource, TDestination> TDestination convert(TSource obj, Class<TDestination> clazz) throws Exception {

		// Ajouter des règles personnalisées
//		myMapper.typeMap(Product.class, ProductDto.class)
//		.addMappings(mapper->{
//			mapper.map(src->src.getDescription(), UserDto::setDesignation);
//			mapper.map(....);
//		});
//		//On définit également les règles inverses
//		myMapper.typeMap(ProductDto.class, Product.class)
//		.addMappings(mapper->{
//			mapper.map(src->src.getDesignation(), Product::setDescription);
//			mapper.map(...);
//		});

		try {
			return myMapper.map(obj, clazz);
		} catch (Exception ex) {
			ex.printStackTrace(); // uniquement pour afficher les exceptions de mapping obj<>Dto
		}
		return null;
	}

}
